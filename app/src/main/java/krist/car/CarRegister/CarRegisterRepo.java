package krist.car.CarRegister;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.storage.StorageReference;

import java.util.Objects;

import krist.car.Api.ApiSingleton;
import krist.car.Models.RegisterCarModel;
import krist.car.Models.UploadUsersImage;

public class CarRegisterRepo {
    private ApiSingleton api;

    public CarRegisterRepo() {
        api = ApiSingleton.getInstance();
    }

    public MutableLiveData<Boolean> registerCarDetails(RegisterCarModel model) {
        final MutableLiveData<Boolean> isCarRegisterSuccessfully = new MutableLiveData<>();

        String userId = api.getUserUId();

        api.getDatebaseReferenceToThisEndPoint("user_cars").child(userId).setValue(model)
                .addOnCompleteListener(task -> {
                    isCarRegisterSuccessfully.setValue(task.isComplete());
                });
        return isCarRegisterSuccessfully;
    }

    public MutableLiveData<String> uploadPhoto(Uri imgFilePath, String fileExtension) {
        final MutableLiveData<String> imgUri = new MutableLiveData<>();
        StorageReference reference = api.getFirebaseStorageToThisEndPoint("uploads").child(System.currentTimeMillis() + "." + fileExtension);

        reference.putFile(imgFilePath).continueWithTask( task -> {
            if(!task.isComplete()) {
                throw Objects.requireNonNull(task.getException());
            }else {
                return reference.getDownloadUrl();
            }
        }).addOnCompleteListener( task -> {
            String imageUri = task.getResult().toString();
            imgUri.setValue(imageUri);
        });
        return imgUri;
    }
}