package krist.car;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ProfiliEdit extends AppCompatActivity {

    private static final String TAG = "Profili Edit" ;
    private EditText mName, mPhone, mGener, mYear, mId, mEmail;
    private TextView mPhotoUser;
    private Database mDatabase;
    private android.support.v7.widget.Toolbar toolbar;
    private ImageView mImageView;


    FirebaseUser idauth = FirebaseAuth.getInstance().getCurrentUser();
    final String id = idauth.getUid();

    private String photoUrlDownlad;

  // Var for image Upload
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 1;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil_edit_layout);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);




        mName = findViewById(R.id.user_info_emri_mbiemri);
        mEmail = findViewById(R.id.user_info_email);
        mPhone = findViewById(R.id.user_info_tel);
        mGener = findViewById(R.id.user_info_gjinia);
        mYear = findViewById(R.id.user_info_mosha);
        mId = findViewById(R.id.user_info_personalNumber);
        mPhotoUser = findViewById(R.id.txt_view_ndrysho_foto_profili);
        mImageView = findViewById(R.id.foto_user);
        mDatabase = new Database();






        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               finish();
            }
        });

        //toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.whiteColor), PorterDuff.Mode.SRC_ATOP);


        getUserInfo();


        mPhotoUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });



        viewHideSoftKeyboard();



    }


    private void getUserInfo( ){

        mDatabase.getmDatabaseRefUsers().child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DetajetModel detajetModel = dataSnapshot.getValue(DetajetModel.class);
                mName.setText(detajetModel.getEmri());
                mPhone.setText(detajetModel.getPhone());
                mYear.setText(detajetModel.getBirthday());
                mGener.setText(detajetModel.getGener());
                mId.setText(detajetModel.getPersonalIdNumber());
                mEmail.setText(idauth.getEmail());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        mDatabase.getDatebaseImage().child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UploadUsersImage imageModel = dataSnapshot.getValue(UploadUsersImage.class);

                photoUrlDownlad = imageModel.getImageUrl();
                Log.d(TAG, "onDataChange: " + photoUrlDownlad);

                Picasso.get().load(imageModel.getImageUrl()).fit().centerCrop().into(mImageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }












    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profilitoolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ok :


                updateInfoFunction();
                uploadFile();





                default:
                    return super.onOptionsItemSelected(item);
        }



    }



    private void updateInfoFunction(){




        String mEmri = mName.getText().toString();
        String mTel = mPhone.getText().toString();
        String mMosha = mYear.getText().toString();
        String mGjinia = mGener.getText().toString();
        String mPersonalNumber = mId.getText().toString();
        String mAddres = mEmail.getText().toString();





        Map<String, Object> emriMap = new HashMap<String, Object>();
        emriMap.put("emri", mEmri);
        emriMap.put("email", mAddres );
        emriMap.put("phone", mTel);
        emriMap.put("birthday", mMosha);
        emriMap.put("gener", mGjinia);
        emriMap.put("personalIdNumber", mPersonalNumber);




        mDatabase.getmDatabaseRefUsers().child(id).updateChildren(emriMap);








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



        }


    }


    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mine = MimeTypeMap.getSingleton();
        return mine.getExtensionFromMimeType(cR.getType(uri));
    }



    private void uploadFile() {




        if (filePath != null) {


            final StorageReference fileRefernce = mDatabase.getStorage().child(System.currentTimeMillis() + "." + getFileExtension(filePath));

            StorageReference photoReference = mDatabase.getStorage().getStorage().getReferenceFromUrl(photoUrlDownlad);
            photoReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    Log.d(TAG, "u fshi");

                }
            });




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



                        Map<String, Object> imageMap = new HashMap<String, Object>();


                        imageMap.put("imageUrl", stringUri);


                        mDatabase.getDatebaseImage().child(id).updateChildren(imageMap);



                    }
                }
            }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {






                }
            });




        }


    }


    private void viewHideSoftKeyboard(){

        mName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!view.hasFocus()){
                    hideKeyboard(view);
                }
            }
        });


        mGener.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!view.hasFocus()){
                    hideKeyboard(view);
                }
            }
        });


        mPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!view.hasFocus()){
                    hideKeyboard(view);
                }
            }
        });


        mYear.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!view.hasFocus()){
                    hideKeyboard(view);
                }
            }
        });

        mId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!view.hasFocus()){
                    hideKeyboard(view);
                }
            }
        });

    }



    private void hideKeyboard(View view){
        InputMethodManager inputMethodManager =(InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }







}
