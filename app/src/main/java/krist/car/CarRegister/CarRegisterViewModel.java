package krist.car.CarRegister;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import krist.car.ProfileInfo.Models.CarModel;

public class CarRegisterViewModel extends ViewModel {
    private CarRegisterRepo repo;

    private MutableLiveData<Boolean> isCarRegisterSuccessfully;
    private MutableLiveData<String> imgPathRef;

    public CarRegisterViewModel() { repo = new CarRegisterRepo();}

    LiveData<Boolean> isCarRegisterSuccessfully() {
        if(isCarRegisterSuccessfully == null) {
            isCarRegisterSuccessfully = new MutableLiveData<>();
        }
        return isCarRegisterSuccessfully;
    }

    LiveData<String> isImgUploadedSuccessfully() {
        if(imgPathRef == null) {
            imgPathRef = new MutableLiveData<>();
        }
        return imgPathRef;
    }

    void uploadImg(Uri imgUri, String imgExt) {
        imgPathRef = repo.uploadPhoto(imgUri, imgExt);
    }

    void registerCarNewWay(CarModel model) { isCarRegisterSuccessfully = repo.addCar(model);}
}
