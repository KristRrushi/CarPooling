package krist.car;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import krist.car.Models.DetajetModel;
import krist.car.Models.UploadUsersImage;

public class MakinaEdit extends AppCompatActivity implements View.OnFocusChangeListener{


    private EditText  mMake , mModel , mPlate , mColor;
    private TextView mSelectImage;
    private ImageView mCarImage;
    private Toolbar toolbar;
    private Database mDatabase;



    FirebaseUser idauth = FirebaseAuth.getInstance().getCurrentUser();
    final String id = idauth.getUid();


    // Var for image Upload
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 1;









    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.makina_edit_layout);

        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar_makina);
        setSupportActionBar(toolbar);


        mMake = findViewById(R.id.user_info_marka_edit);
        mModel = findViewById(R.id.user_info_modeli_edit);
        mPlate = findViewById(R.id.user_info_targa_edit);
        mColor = findViewById(R.id.user_info_ngjyra_edit);
        mSelectImage = findViewById(R.id.txt_view_ndrysho_foto_makine_edit);
        mCarImage = findViewById(R.id.foto_makine_edit);
        mDatabase = new Database();


        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.whiteColor), PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });


        getCarInfo();


        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        viewHideSoftKeyboard();


    }










    private void getCarInfo(){


        mDatabase.getmDatabaseRefUsers().child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DetajetModel detajetModel = dataSnapshot.getValue(DetajetModel.class);

                mMake.setText(detajetModel.getMarkaMak());
                mModel.setText(detajetModel.getModeliMak());
                mPlate.setText(detajetModel.getTargaMak());
                mColor.setText(detajetModel.getNgjyraMak());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        mDatabase.getDatebaseImage().child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                UploadUsersImage imageModel = dataSnapshot.getValue(UploadUsersImage.class);
                Picasso.get().load(imageModel.getImageCarUrl()).fit().centerCrop().into(mCarImage);
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




        String marka = mMake.getText().toString();
        String modeli = mModel.getText().toString();
        String targa = mPlate.getText().toString();
        String ngjyra = mColor.getText().toString();






        Map<String, Object> makinaMap = new HashMap<String, Object>();
        makinaMap.put("markaMak", marka);
        makinaMap.put("modeliMak", modeli);
        makinaMap.put("targaMak", targa);
        makinaMap.put("ngjyraMak", ngjyra);




        mDatabase.getmDatabaseRefUsers().child(id).updateChildren(makinaMap);







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


                        imageMap.put("imageCarUrl", stringUri);


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

        mMake.setOnFocusChangeListener(this);
        mModel.setOnFocusChangeListener(this);
        mPlate.setOnFocusChangeListener(this);
        mColor.setOnFocusChangeListener(this);

    }







    private void hideKeyboard(View view){
        InputMethodManager inputMethodManager =(InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }


    @Override
    public void onFocusChange(View view, boolean b) {
        if(!view.hasFocus()){
            hideKeyboard(view);
        }
    }
}
