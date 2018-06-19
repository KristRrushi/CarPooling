package krist.car;



import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class DetajeActivity extends AppCompatActivity {



    private EditText txtmodeli,txtmarka,txttarga;
    private Button btnZgjidh , btnNgarko;
   private ImageView imageView;

   private Uri filePath;

   private final int PICK_IMAGE_REQUEST = 71;

   private FirebaseStorage storage;
   private StorageReference mStorageRef;

    private FirebaseAuth mAuth;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference databaseUsers = database.getReference("usersdetails");





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detaje);


        btnZgjidh = findViewById(R.id.fotoMakine);
        btnNgarko = findViewById(R.id.btnNgarko);
        txtmodeli = findViewById(R.id.modeliMakina);
        txtmarka = findViewById(R.id.markaMakina);
        txttarga = findViewById(R.id.targaMakina);



        mStorageRef = FirebaseStorage.getInstance().getReference();


        btnZgjidh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });


        btnNgarko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadDetails();

            }
        });


        }

    private void uploadDetails() {


        final String marka = txtmarka.getText().toString().trim();
        final String modeli = txtmodeli.getText().toString().trim();
        final String targa = txttarga.getText().toString().trim();

        String id;

        FirebaseUser iaduth = FirebaseAuth.getInstance().getCurrentUser();

        id = iaduth.getUid();






        DetajetModel detajetModel = new DetajetModel(id,marka,modeli,targa);



        databaseUsers.child(id).setValue(detajetModel);


//       PostFragment postFragment = new PostFragment();
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.replace(R.id.main_frame, postFragment);
//        transaction.addToBackStack(null);
//        transaction.commit();







    }





    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK){



            Uri uri = data.getData();
            StorageReference filepath = mStorageRef.child("Photos").child(uri.getLastPathSegment());

            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(DetajeActivity.this, "Ngarkim i suksesshem.", Toast.LENGTH_LONG).show();



                }
            });

    }


    }


}
