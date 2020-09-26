package krist.car.Auth.register;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import krist.car.MainActivity;
import krist.car.Models.RegisterUserModelValidation;
import krist.car.Models.UserModel;
import krist.car.R;
import krist.car.Utils.Constants;
import krist.car.Utils.Helpers;

public class RegisterActivity extends AppCompatActivity {

    private EditText  txtName, txtPhone, txtBirthday, txtPersonalIdNumber,txtEmail, txtPass;
    private TextView txtVFoto;
    private AutoCompleteTextView txtGener;
    private Button btnRegister;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 1;
    private ProgressBar progressBar;
    private RegisterViewModel viewModel;


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

        populateAutoCompleteView();
        setupViewListeners();
        initRegisterViewModel();
    }

    private void initRegisterViewModel() {
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
    }

    private void setupViewListeners() {
        btnRegister.setOnClickListener(view -> registerUser());
        txtVFoto.setOnClickListener(view -> chooseImage());
    }

    private void populateAutoCompleteView() {
        String[] generList = getResources().getStringArray(R.array.gener);
        ArrayAdapter<String> adapterMarka = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, generList);
        txtGener.setAdapter(adapterMarka);
    }

    private void registerUser() {
        String email = txtEmail.getText().toString().trim();
        String pass = txtPass.getText().toString().trim();
        String name = txtName.getText().toString().trim();
        String phone = txtPhone.getText().toString().trim();
        String birthday = txtBirthday.getText().toString().trim();
        String gener = txtGener.getText().toString().trim();
        String personalIdNumber = txtPersonalIdNumber.getText().toString().trim();

        RegisterUserModelValidation model = new RegisterUserModelValidation(email, pass, name, phone,
                birthday, gener, personalIdNumber, filePath);


        if(!validateRegisterModel(model)){
            return;
        }

        viewModel.createUserWithEmailAndPassword(model.getEmail(), model.getPassword());
        viewModel.isUserCreatedSuccessfully().observe(this, isSuccess -> {
            if(isSuccess) {
                uploadIdImg(model);
            }
        });
    }

    private void registerUserData(UserModel model) {
        viewModel.registerUserData(model);
        viewModel.isUserRegisterSuccessfully().observe(RegisterActivity.this, isRegisterSuccesfully -> {
            if(isRegisterSuccesfully) {
                Helpers.showToastMessage(this, "Regjistrim i sukseshem");
                Helpers.goToActivity(this, MainActivity.class);
            }
        });
    }

    private void uploadIdImg(RegisterUserModelValidation model) {
        String fileExtension = Helpers.getFileExtension(this, filePath);

        viewModel.uploadImage(filePath,fileExtension);
        viewModel.isImgUploadedSuccessfully().observe(this, imgRef -> {
            if(!imgRef.isEmpty()) {

                UserModel userModel = new UserModel("", model.getName(), model.getPhone(), model.getBirthday(), model.getGener(), model.getPersonalIdNumber(), imgRef);
                registerUserData(userModel);
            }
        });
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

    private Boolean validateRegisterModel(RegisterUserModelValidation model) {
        if(model.getEmail().isEmpty() || !Helpers.validateStringBaseOnRegex(model.getEmail(), Constants.EMAIL_REGEX)) {
            txtEmail.setError("Plotesoni fushen ne formatin e duhur");
            return false;
        }else if(model.getPassword().isEmpty() || !Helpers.validateStringBaseOnRegex(model.getPassword(), Constants.PASSWORD_REGEX)){
            //todo to string
            txtPass.setError("Plotesoni fushen ne formatin e duhur");
            return false;
        }else if(model.getName().isEmpty()) {
            txtName.setError("Plotesoni emrin");
            return false;
        }else if(model.getPhone().isEmpty()) {
            txtPhone.setError("Plotesoni telefonin");
            return false;
        }else if(model.getBirthday().isEmpty()) {
            txtBirthday.setError("Plotesoni ditelindjen");
            return false;
        }else if(model.getPersonalIdNumber().isEmpty()) {
            txtPersonalIdNumber.setError("Plotesoni id");
            return false;
        }else if(filePath == null) {
            Helpers.showToastMessage(this, "Ngarkoni nje foto");
            return false;
        }
        return true;
    }
}
