package krist.car.auth.register;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import krist.car.Models.LoginFormModel;
import krist.car.Models.UserModel;
import krist.car.auth.AuthRepo;

public class RegisterViewModel extends ViewModel {
    private AuthRepo authRepo;

    private MutableLiveData<Boolean> createUserStatus;
    private MutableLiveData<Boolean> registerUserStatus;
    private MutableLiveData<Boolean> imgUploadStatus;

    LiveData<Boolean> isUserCreatedSuccessfully() {
        if(createUserStatus == null) {
            createUserStatus = new MutableLiveData<>();
        }
        return createUserStatus;
    }

    LiveData<Boolean> isUserRegisterSuccessfully() {
        if(registerUserStatus == null) {
            registerUserStatus = new MutableLiveData<>();
        }
        return registerUserStatus;
    }

    LiveData<Boolean> isImgUploadedSuccessfully() {
        if(imgUploadStatus == null) {
            imgUploadStatus = new MutableLiveData<>();
        }
        return imgUploadStatus;
    }

    void createUserWithEmailAndPassword(LoginFormModel loginModel) {
        createUserStatus = authRepo.createUserWithEmailAndPassword(loginModel);
    }

    void registerUserDate(UserModel user) {
        registerUserStatus = authRepo.registerUserData(user);
    }

    void uploadImage(Uri imgUri, String fileExtension) {
        imgUploadStatus = authRepo.uploadPhoto(imgUri, fileExtension);
    }
}
