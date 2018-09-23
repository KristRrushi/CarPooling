package krist.car;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static krist.car.R.layout.loginactivity;

/**
 * Created by pampers on 1/12/2018.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{


    private Button logIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);

        firebaseAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.emailLogIn);
        editTextPassword =  findViewById(R.id.passwordLogIn);

        logIn =  findViewById(R.id.buttonLogIn);

        progressDialog = new ProgressDialog(this);


        logIn.setOnClickListener(this);






    }

    private void registerUser() {

        final String email = editTextEmail.getText().toString().trim();
        final String pass = editTextPassword.getText().toString().trim();



       /* if (TextUtils.isEmpty(email)) {
            //email bosh
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();

            return;
        }
        if (TextUtils.isEmpty(pass)) {
            //pass bosh
            Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();

            return;
        }*/


       /* progressDialog.setMessage("");
        progressDialog.show();*/

        //firebaseAuth.signInWithEmailAndPassword("krist2@gmail.com", "123456789")
        firebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("D", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Intent myIntent = new Intent(LoginActivity.this,
                                    RegisterActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("email", email);
                            bundle.putString("password", pass);
                            myIntent.putExtra("auth", bundle);

                            Toast toast = Toast.makeText(LoginActivity.this, "Regjistrim i  metejshem", Toast.LENGTH_SHORT);
                            toast.show();

                            startActivity(myIntent);


                        } else {
                            Intent myIntent = new Intent(LoginActivity.this,
                                    MainActivity.class);

                            Toast toast = Toast.makeText(LoginActivity.this, "Hyrje e suksesshme", Toast.LENGTH_LONG);
                            toast.show();

                            startActivity(myIntent);
                        }


                    }
                });



    }

    @Override
    public void onClick(View v) {
        if (v == logIn) {
            registerUser();
        }
    }




}
