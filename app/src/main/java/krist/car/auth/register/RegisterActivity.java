package krist.car.auth.register;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import krist.car.Api.ApiSingleton;
import krist.car.MainActivity;
import krist.car.Models.UploadUsersImage;
import krist.car.Models.users;
import krist.car.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText  txtName, txtPhone, txtBirthday, txtPersonalIdNumber,txtEmail, txtPass;
    private TextView txtVFoto;
    private AutoCompleteTextView txtGener;
    private Button btnRegister;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 1;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private ProgressBar progressBar;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseUsers = database.getReference("users");

    private String userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prove_prove);

        txtEmail =  findViewById(R.id.emailRegister);
        txtPass =  findViewById(R.id.passRegister);
        txtName =  findViewById(R.id.nameRegister);
        txtPhone =  findViewById(R.id.phoneRegister);
        txtBirthday = findViewById(R.id.birthdayRegister);
        txtGener = findViewById(R.id.generRegister);
        txtPersonalIdNumber = findViewById(R.id.cardIdNumberRegister);
        txtVFoto = findViewById(R.id.btn_foto_personale_register);
        btnRegister =  findViewById(R.id.buttonRegister);
        progressBar = findViewById(R.id.progres_register_activity);
        progressBar.setVisibility(View.INVISIBLE);


        String[] modeliArray = getResources().getStringArray(R.array.gener);

        ArrayAdapter<String> adapterMarka = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, modeliArray);

        txtGener.setAdapter(adapterMarka);

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("imageUploads");

        //Check if user is comming from unsuccesfull login
        Bundle extra = getIntent().getExtras();
        if(extra != null) {
            Bundle bundle = extra.getBundle("auth");
            assert bundle != null;
            String email = bundle.getString("email");
            String pass = bundle.getString("password");
            txtEmail.setText(email);
            txtPass.setText(pass);
        }

        btnRegister.setOnClickListener(this);
        txtVFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == btnRegister) {
            registerUser();
        }
    }

    private void registerUser() {

        final String email = txtEmail.getText().toString().trim();
        final String pass = txtPass.getText().toString().trim();
        final String name = txtName.getText().toString().trim();
        final String phone = txtPhone.getText().toString().trim();
        final String birthday = txtBirthday.getText().toString().trim();
        final String gener = txtGener.getText().toString().trim();
        final String personalIdNumber = txtPersonalIdNumber.getText().toString().trim();

        if (!name.isEmpty() && !phone.isEmpty() && !birthday.isEmpty() &&  !gener.isEmpty() && !personalIdNumber.isEmpty() && filePath != null) {

            ApiSingleton.getInstance().firebaseAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String id  = ApiSingleton.getInstance().firebaseAuth.getCurrentUser().getUid();
                                users users = new users(id, name, phone, birthday, gener, personalIdNumber);

                                ApiSingleton.getInstance().getDatebaseReferenceToThisEndPoint("users").setValue(users);
                                uploadFile();

                                Toast.makeText(RegisterActivity.this, "suskese", Toast.LENGTH_SHORT).show();
                            }
                            else Toast.makeText(RegisterActivity.this,task.getException().getMessage() , Toast.LENGTH_LONG).show();
                        }
                    });
        }else {
            Toast.makeText(this, "Plotesoni te gjitha te dhenat", Toast.LENGTH_SHORT).show();
        }
    }

    private void chooseImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            filePath = data.getData();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mine = MimeTypeMap.getSingleton();
        return mine.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        progressBar.setVisibility(View.VISIBLE);
        if (filePath != null) {
            final StorageReference fileRefernce =
                    ApiSingleton.getInstance().getFirebaseStorageToThisEndPoint("uploads").child(System.currentTimeMillis() + "." + getFileExtension(filePath));

            fileRefernce.putFile(filePath).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isComplete()) {
                        throw task.getException();
                    }
                    return fileRefernce.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();

                        String stringUri = downloadUri.toString();

                        FirebaseUser idauth = FirebaseAuth.getInstance().getCurrentUser();
                        String id = idauth.getUid();

                        UploadUsersImage uploadUsersImage = new UploadUsersImage(stringUri);
                        mDatabaseRef.child(id).setValue(uploadUsersImage);
                    }
                }
            }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(RegisterActivity.this, "Foto u ngarkua me sukses", Toast.LENGTH_LONG).show();
                    Intent myIntent = new Intent(RegisterActivity.this,
                            MainActivity.class);

                    startActivity(myIntent);

                }
            });
        }
    }
}
