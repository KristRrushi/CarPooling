package krist.car.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import krist.car.Models.LoginFormModel;

public class LoginViewModel extends ViewModel {
    private AuthRepo authRepo;

    private MutableLiveData<Boolean> loginStatus;
    public LiveData<Boolean> isLoginSuccess() {
        if(loginStatus == null) {
            loginStatus = new MutableLiveData<>();
        }
        return loginStatus;
    }

    public LoginViewModel() {
        authRepo = new AuthRepo();
    }

    public void signInWithEmailAndPassword(LoginFormModel loginModel) {
        loginStatus = authRepo.firebaseSignInWithEmailAndPassword(loginModel);
    }
}
