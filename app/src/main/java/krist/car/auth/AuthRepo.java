package krist.car.auth;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import krist.car.Api.ApiSingleton;
import krist.car.Models.LoginFormModel;

public class AuthRepo {
    private ApiSingleton api;

    public AuthRepo() {
        api = ApiSingleton.getInstance();
    }

    public MutableLiveData<Boolean> firebaseSignInWithEmailAndPassword(LoginFormModel loginModel) {
        final MutableLiveData<Boolean> isLoginSuccess = new MutableLiveData<>();
        api.firebaseAuth.signInWithEmailAndPassword(loginModel.getName(), loginModel.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                isLoginSuccess.setValue(task.isSuccessful());
            }
        });
        return isLoginSuccess;
    }
}
