package krist.car;

import android.content.ContentResolver;
import android.content.Intent;
import android.drm.DrmStore;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pampers on 1/12/2018.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText  txtName, txtPhone, txtBirthday, txtPersonalIdNumber,txtEmail, txtPass;
   private  TextView txtVFoto;
   private AutoCompleteTextView txtGener;

    private Button btnRegister, btnPersonalPhoto;
    private FirebaseAuth mAuth;

    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 1;


    private StorageReference mStorageRef;

    private DatabaseReference mDatabaseRef;





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
       // btnPersonalPhoto = findViewById(R.id.btn_foto_personale_register);
        txtVFoto = findViewById(R.id.btn_foto_personale_register);

        btnRegister =  findViewById(R.id.buttonRegister);
        mAuth = FirebaseAuth.getInstance();


        String[] modeliArray = getResources().getStringArray(R.array.gener);

        ArrayAdapter<String> adapterMarka = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, modeliArray);

        txtGener.setAdapter(adapterMarka);



        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("imageUploads");


        Bundle bundle = getIntent().getBundleExtra("auth");
        String email = bundle.getString("email");
        String pass = bundle.getString("password");


        txtEmail.setText(email);
        txtPass.setText(pass);





        btnRegister.setOnClickListener(this);

    /*    btnPersonalPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });*/


        txtVFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

       // btnRegister.setVisibility(View.GONE);


    }


    @Override
    public void onClick(View v) {
        if (v == btnRegister) {
            uploadFile();
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

        if (!name.isEmpty() && !phone.isEmpty() && !birthday.isEmpty() &&  !gener.isEmpty() && !personalIdNumber.isEmpty()) {


            mAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {


                                Toast.makeText(RegisterActivity.this, "suskese", Toast.LENGTH_SHORT).show();

                                FirebaseUser idauth = FirebaseAuth.getInstance().getCurrentUser();
                                idauth.getUid();
                                String id = idauth.getUid();


                                    users users = new users(id, name, phone, birthday, gener, personalIdNumber);

                                    databaseUsers.child(id).setValue(users);






                            }
                            else Toast.makeText(RegisterActivity.this,task.getException().getMessage() , Toast.LENGTH_LONG).show();
                        }
                    });




        }else {
            Toast.makeText(this, "Plotesoni te gjitha te dhenat", Toast.LENGTH_SHORT).show();
        }
        /*if (name.isEmpty() || phone.isEmpty() || birthday.isEmpty() ||  gener.isEmpty() || !personalIdNumber.isEmpty()) {
            //pass bosh

        }*/




    }


   //---------------------------------------- choose Image

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


   //------------------------------------------------------------- choose Image



    private void uploadFile() {

        Log.v("DEta", "shjion");

        if (filePath != null) {


            final StorageReference fileRefernce = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(filePath));


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

                        Log.v("klick", stringUri);
                        Log.v("String","benibeniebenibeiniebni");




                        FirebaseUser idauth = FirebaseAuth.getInstance().getCurrentUser();
                        String id = idauth.getUid();




                        UploadUsersImage uploadUsersImage = new UploadUsersImage(stringUri);




                        mDatabaseRef.child(id).setValue(uploadUsersImage);












                    }
                }
            }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {






                    Toast.makeText(RegisterActivity.this, "Foto u ngarkua me sukses", Toast.LENGTH_LONG).show();
                    Intent myIntent = new Intent(RegisterActivity.this,
                            MainActivity.class);

                    startActivity(myIntent);


                }
            });







        }


    }












}
