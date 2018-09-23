package krist.car;




import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class DetajeActivity extends AppCompatActivity {


    private EditText txtmodeli, txtmarka, txttarga;
    private Button btnZgjidh, btnNgarko;

    private ProgressBar mProgresBar;

    private ImageView imageView;

    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 1;


    private StorageReference mStorageRef;

    private FirebaseAuth mAuth;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference databaseUsers = database.getReference("users");


    private DatabaseReference mDatabaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detaje);

        mAuth = FirebaseAuth.getInstance();
        btnZgjidh = findViewById(R.id.fotoMakine);
        btnNgarko = findViewById(R.id.btnNgarko);
        txtmodeli = findViewById(R.id.modeliMakina);
        txtmarka = findViewById(R.id.markaMakina);
        txttarga = findViewById(R.id.targaMakina);
        mProgresBar = findViewById(R.id.progress_bar);


        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("imageUploads");


        btnZgjidh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });


        btnNgarko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getDetail();
                uploadFile();


            }
        });


    }


    private void getDetail() {

        String id;

        id = mAuth.getCurrentUser().getUid();

        Query query = databaseUsers.orderByKey().equalTo(id);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child : children) {
                    users users = child.getValue(krist.car.users.class);
                    String emri = users.getEmri();
                    String phone = users.getPhone();

                    uploadDetails(emri, phone);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    private void uploadDetails(String emri, String phone) {


        final String marka = txtmarka.getText().toString().trim();
        final String modeli = txtmodeli.getText().toString().trim();
        final String targa = txttarga.getText().toString().trim();

        String id;

        id = mAuth.getCurrentUser().getUid();


        DetajetModel detajetModel = new DetajetModel(id, emri, phone, marka, modeli, targa);


        databaseUsers.child(id).setValue(detajetModel);



      /*  final Intent intent = new Intent(DetajeActivity.this , MainActivity.class);
        startActivity(intent);*/


      /*PostFragment postFragment = new PostFragment();
       FragmentTransaction transaction = getFragmentManager().beginTransaction();
       transaction.replace(R.id.main_frame, postFragment);
        transaction.addToBackStack(null);
        transaction.commit();*/











      /*  Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent1 = new Intent(DetajeActivity.this, PostFragment.class);
                startActivity(intent1);
            }
        }, 3000);


*/

    }


    private void chooseImage() {
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

           /* String uid = mAuth.getCurrentUser().getUid();

            Uri uri = data.getData();
            StorageReference filepath = mStorageRef.child(uid);




            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(DetajeActivity.this, "Ngarkim i suksesshem.", Toast.LENGTH_LONG).show();




                }
            });*/


        }


    }


    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mine = MimeTypeMap.getSingleton();
        return mine.getExtensionFromMimeType(cR.getType(uri));
    }


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



                        Upload upload = new Upload(stringUri);

                        mDatabaseRef.child(id).setValue(upload);

                        Toast.makeText(DetajeActivity.this, "downloadUri.toString()",Toast.LENGTH_LONG);


                       /* Bundle bundle = new Bundle();
                        bundle.putString("URi", stringUri);

                        bundle.putExtra

                        FragmentManager m = getSupportFragmentManager();
                        FragmentTransaction t = m.beginTransaction();

                        PostFragment fragment = new PostFragment();
                        fragment.setArguments(bundle);
                        t.commit();*/







                    }
                }
            });







        }


    }
}