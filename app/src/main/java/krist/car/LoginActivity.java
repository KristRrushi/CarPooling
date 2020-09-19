package krist.car;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import krist.car.Api.ApiSingleton;
import krist.car.Utils.Constants;
import krist.car.Utils.Helpers;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button logIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_insta_layout);

        editTextEmail = findViewById(R.id.emailLogIn);
        editTextPassword =  findViewById(R.id.passwordLogIn);
        logIn =  findViewById(R.id.buttonLogIn);
        registerButton = findViewById(R.id.register_button);

        logIn.setOnClickListener(this);
        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLogIn:
                attemptLogin();
                break;
            case R.id.register_button:
                goToRegisterActivity();
                break;
        }
    }

    private void attemptLogin() {
        final String email = editTextEmail.getText().toString().trim();
        final String pass = editTextPassword.getText().toString().trim();

        if(!Helpers.validateStringBaseOnRegex(email, Constants.EMAIL_REGEX)) {
            editTextEmail.setError("Plotesoni fushen ne formatin e duhur");
            return;
        }

        if(!Helpers.validateStringBaseOnRegex(pass, Constants.PASSWORD_REGEX)) {
            editTextPassword.setError("Plotesoni fushen ne formatin e duhur");
            return;
        }

        final String emailTest = "kristrrushi@gmail.com";
        final String passTest = "123456789";

        ApiSingleton.getInstance().firebaseAuth.signInWithEmailAndPassword(emailTest, passTest)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {

                            Bundle bundle = new Bundle();
                            bundle.putString("email", email);
                            bundle.putString("password", pass);

                            Helpers.goToActivityAttachBundle(LoginActivity.this, RegisterActivity.class, "auth", bundle);
                            Helpers.showToastMessage(LoginActivity.this, "Regjistrimi i metejshem");

                        } else {
                            Helpers.goToActivity(LoginActivity.this, MainActivity.class);
                            Helpers.showToastMessage(LoginActivity.this, "Hyrje e sukseshme");
                        }
                    }
                });
    }

    private void goToRegisterActivity() {
        Helpers.goToActivity(this, RegisterActivity.class);
    }
}
