package krist.car.auth;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import com.google.firebase.storage.StorageReference;
import java.util.Objects;

import javax.inject.Inject;

import krist.car.api.ApiModule;
import krist.car.api.FirebaseApi;
import krist.car.models.LoginFormModel;
import krist.car.models.UserModel;

public class AuthRepo {
    public ApiModule api;
    public FirebaseApi firebaseApi;

    @Inject
    public AuthRepo(FirebaseApi firebaseApi) {
        this.firebaseApi = firebaseApi;
    }

    public MutableLiveData<Boolean> firebaseSignInWithEmailAndPassword(LoginFormModel loginModel) {
        final MutableLiveData<Boolean> isLoginSuccess = new MutableLiveData<>();

        firebaseApi.signInWithUsernameAndPass(loginModel.getName(), loginModel.getPassword())
                .addOnCompleteListener(task -> isLoginSuccess.setValue(task.isSuccessful()));
        return isLoginSuccess;
    }

    public MutableLiveData<Boolean> createUserWithEmailAndPassword(LoginFormModel loginModel) {
        final MutableLiveData<Boolean> isUserCreatedSuccessfully = new MutableLiveData<>();

        firebaseApi.createUserWithEmailAndPassword(loginModel.getName(), loginModel.getPassword())
                .addOnCompleteListener(task -> {
                    isUserCreatedSuccessfully.setValue(task.isSuccessful());
                });
        return isUserCreatedSuccessfully;
    }

    public MutableLiveData<Boolean> registerUserData(UserModel userModel) {
        final MutableLiveData<Boolean> isUserRegisterSuccessfully = new MutableLiveData<>();

        String userId = api.getUserUId();
        userModel.setId(userId);


        firebaseApi.getDatabaseReferenceToThisEndPoint("users").child(userId).setValue(userModel).addOnCompleteListener(task -> {
            isUserRegisterSuccessfully.setValue(task.isComplete());
        });

        return isUserRegisterSuccessfully;
    }

    public MutableLiveData<String> uploadPhoto(Uri imgFilePath, String fileExtension) {
        final MutableLiveData<String> isSuccess = new MutableLiveData<>();
        StorageReference reference = firebaseApi.getStorageReferenceToThisEndPoint("uploads").child(System.currentTimeMillis() + "." + fileExtension);

        reference.putFile(imgFilePath).continueWithTask( task -> {
            if(!task.isComplete()) {
                throw Objects.requireNonNull(task.getException());
            }else {
                return reference.getDownloadUrl();
            }
        }).addOnCompleteListener( task -> {
            String imageUri = task.getResult().toString();
            //Todo fix this model class
            isSuccess.setValue(imageUri);
        });

        return isSuccess;
    }
}
