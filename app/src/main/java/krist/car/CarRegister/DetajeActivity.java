package krist.car.CarRegister;




import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import krist.car.MainActivity;
import krist.car.Models.RegisterCarModel;
import krist.car.R;
import krist.car.Utils.Helpers;

public class DetajeActivity extends AppCompatActivity  implements View.OnFocusChangeListener{


    private AutoCompleteTextView acModeli, acMarka, acNgjyra;
    private TextView zgjidhFoto;
    private EditText  txttarga;
    private Button btnNgarko;
    private ProgressBar mProgresBar;
    private Uri filePath;
    private Toolbar toolbar;
    private final int PICK_IMAGE_REQUEST = 1;
    private CarRegisterViewModel viewModel;
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

        acMarka = findViewById(R.id.emailRegister);
        acModeli =  findViewById(R.id.passRegister);
        acNgjyra = findViewById(R.id.cardIdNumberRegister);
        zgjidhFoto = findViewById(R.id.btn_foto_personale_register);
        txttarga = findViewById(R.id.phoneRegister);
        btnNgarko = findViewById(R.id.buttonRegister);
        mProgresBar = findViewById(R.id.progres_detajet_activity);
        mProgresBar.setVisibility(View.INVISIBLE);
        toolbar = findViewById(R.id.toolbarRegjMakine);


        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("imageUploads");


        viewHideSoftKeyBoard();
        setupAdaptersForFields();
        setupListeners();
        setupRegisterCarViewModel();
    }

    private void setupRegisterCarViewModel() {
        viewModel = new ViewModelProvider(this).get(CarRegisterViewModel.class);
    }

    private void setupListeners() {
        toolbar.setNavigationOnClickListener(view -> startMainActivity());
        zgjidhFoto.setOnClickListener(view -> chooseImage());
        btnNgarko.setOnClickListener(view -> attemptCarRegister());
    }

    private void setupAdaptersForFields() {
        String[] modeliArray = getResources().getStringArray(R.array.modelet_makina);
        String[] markaArray = getResources().getStringArray(R.array.marka_makina);
        String[] ngjyraArray = getResources().getStringArray(R.array.ngjyrat_makina);

        ArrayAdapter<String> adapterMarka = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, markaArray);
        ArrayAdapter<String> adapterModeli = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, modeliArray);
        ArrayAdapter<String> adapterNgjyra = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ngjyraArray);

        acMarka.setAdapter(adapterMarka);
        acModeli.setAdapter(adapterModeli);
        acNgjyra.setAdapter(adapterNgjyra);
    }

   private void uploadDetails2(){
        final String marka = acMarka.getText().toString().trim();
       final String modeli = acModeli.getText().toString().trim();
       final String targa = txttarga.getText().toString().trim().toUpperCase();
       final String ngjyra = acNgjyra.getText().toString().trim();

       String id;





       id = mAuth.getCurrentUser().getUid();

       if(!marka.isEmpty() && !modeli.isEmpty() && !targa.isEmpty() && !ngjyra.isEmpty() && filePath != null) {


           Map<String, Object> mapMak = new HashMap<String, Object>();

           mapMak.put("markaMak", marka);
           mapMak.put("modeliMak", modeli);
           mapMak.put("targaMak", targa);
           mapMak.put("ngjyraMak", ngjyra);


           databaseUsers.child(id).updateChildren(mapMak);

           uploadFile();





       }else {

       Toast.makeText(DetajeActivity.this, "Plotesoni te gjitha te dhenat" , Toast.LENGTH_LONG).show();
       }

   }

   private void attemptCarRegister() {
       String make = acMarka.getText().toString().trim();
       String carModel = acModeli.getText().toString().trim();
       String plate = txttarga.getText().toString().trim().toUpperCase();
       String color = acNgjyra.getText().toString().trim();

       RegisterCarModel model = new RegisterCarModel(make, carModel, plate, color, "");

       if(!validateFields(model)){
           return;
       }

       uploadCarImg(model);
   }

   private void uploadCarImg(RegisterCarModel model) {
        viewModel.uploadImg(filePath, Helpers.getFileExtension(this, filePath));
        viewModel.isImgUploadedSucessfully().observe(this, imgRef -> {
            if(!imgRef.isEmpty()) {
                model.setCarImgRef(imgRef);
                registerCarDetails(model);
            }
        });
   }

   private void registerCarDetails(RegisterCarModel model) {
        viewModel.registerCar(model);
        viewModel.isCarRegisterSuccessfully().observe(this, isSuccess -> {
            if(isSuccess) {
                Helpers.goToActivity(this, MainActivity.class);
                Helpers.showToastMessage(this, "Regjistrim i suksesshem");
            }else {
                Helpers.showToastMessage(this, "Pati nje problem ne regjistrim, provo perseri");
            }
        });
   }

   private Boolean validateFields(RegisterCarModel model) {
        if(model.getCarMarks().isEmpty()) {
            acMarka.setError("Zgjidhni marken");
            return false;
        }else if(model.getCarModel().isEmpty()) {
            acModeli.setError("Zgjidhni modelin");
            return false;
        }else if(model.getCarColor().isEmpty()){
            acNgjyra.setError("Zgjidhni ngjyren");
            return false;
        }else if(model.getCarPlate().isEmpty()) {
            txttarga.setError("Plotesoni targen");
            return false;
        }else if(filePath == null) {
            Helpers.showToastMessage(this, "Mungon foto e makines");
            return false;
        }else {
            return true;
        }
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

        mProgresBar.setVisibility(View.VISIBLE);

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


                        FirebaseUser idauth = FirebaseAuth.getInstance().getCurrentUser();
                        String id = idauth.getUid();

                        Map<String, Object> mapUri = new HashMap<String, Object>();
                        mapUri.put("imageCarUrl", stringUri);
                        mDatabaseRef.child(id).updateChildren(mapUri);

                        Toast.makeText(DetajeActivity.this, "downloadUri.toString()",Toast.LENGTH_LONG);
                    }
                }
            }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    mProgresBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(DetajeActivity.this, "Image Done", Toast.LENGTH_LONG).show();
                    startMainActivity();
                }
            });
        }
    }

    private void startMainActivity(){
        Intent intent = new Intent(DetajeActivity.this, MainActivity.class);
        startActivity(intent);
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

    private void viewHideSoftKeyBoard(){
        acMarka.setOnFocusChangeListener(this);
        acModeli.setOnFocusChangeListener(this);
        acNgjyra.setOnFocusChangeListener(this);
        txttarga.setOnFocusChangeListener(this);
    }
}