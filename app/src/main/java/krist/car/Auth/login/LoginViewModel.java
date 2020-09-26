package krist.car.Auth.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import krist.car.Models.LoginFormModel;
import krist.car.Auth.AuthRepo;

public class LoginViewModel extends ViewModel {
    private AuthRepo authRepo;

    private MutableLiveData<Boolean> loginStatus;

    public LoginViewModel() {
        authRepo = new AuthRepo();
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
