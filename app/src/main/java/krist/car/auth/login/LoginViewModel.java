package krist.car.auth.login;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import krist.car.models.LoginFormModel;
import krist.car.auth.AuthRepo;

public class LoginViewModel extends ViewModel {
    private AuthRepo authRepo;

    private MutableLiveData<Boolean> loginStatus;

    @ViewModelInject
    LoginViewModel(AuthRepo repo) {
        this.authRepo = repo;
        Log.d("lol", repo.toString());
    }


    LiveData<Boolean> isLoginSuccess() {
        if(loginStatus == null) {
            loginStatus = new MutableLiveData<>();
        }
        return loginStatus;
    }

    void signInWithEmailAndPassword(LoginFormModel loginModel) {
        loginStatus = authRepo.firebaseSignInWithEmailAndPassword(loginModel);
    }
}
