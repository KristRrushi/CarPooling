package krist.car;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by pampers on 1/12/2018.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText  txtName, txtPhone;
    private TextView txtEmail, txtPass;

    private Button btnRegister;
    private FirebaseAuth mAuth;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference databaseUsers = database.getReference("users");
    private String userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeractivity);

        txtEmail =  findViewById(R.id.emailRegister);
        txtPass =  findViewById(R.id.passRegister);
        txtName =  findViewById(R.id.nameRegister);
        txtPhone =  findViewById(R.id.phoneRegister);
        btnRegister =  findViewById(R.id.buttonRegister);
        mAuth = FirebaseAuth.getInstance();
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");


        Bundle bundle = getIntent().getBundleExtra("auth");
        String email = bundle.getString("email");
        String pass = bundle.getString("password");


        txtEmail.setText(email);
        txtPass.setText(pass);


        btnRegister.setOnClickListener(this);


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

        if (!TextUtils.isEmpty(name)) {


            mAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent myIntent = new Intent(RegisterActivity.this,
                                        MainActivity.class);

                                Toast.makeText(RegisterActivity.this, "suskese", Toast.LENGTH_SHORT).show();

                                FirebaseUser idauth = FirebaseAuth.getInstance().getCurrentUser();
                                idauth.getUid();
                                String id = idauth.getUid();
                                users users = new users(id, name, phone);

                                databaseUsers.child(id).setValue(users);

                                startActivity(myIntent);
                            }
                            else Toast.makeText(RegisterActivity.this,task.getException().getMessage() , Toast.LENGTH_LONG).show();
                        }
                    });




        }
        if (TextUtils.isEmpty(name)) {
            //pass bosh
            Toast.makeText(this, "Fusni emrin dhe mbiemrin", Toast.LENGTH_SHORT).show();
        }




    }
}
