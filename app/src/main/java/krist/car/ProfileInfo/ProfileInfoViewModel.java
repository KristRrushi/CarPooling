package krist.car.ProfileInfo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import krist.car.ProfileInfo.Models.CarModel;
import krist.car.ProfileInfo.Models.ProfileInfoModel;

public class ProfileInfoViewModel extends ViewModel {
    private ProfileInfoRepo repo;
    private MutableLiveData<ProfileInfoModel> userProfileInfo;
    private MutableLiveData<ArrayList<CarModel>> userCars;
    private MutableLiveData<Boolean> isCarSelectedFinishSuccessfully;

    public ProfileInfoViewModel() {repo = new ProfileInfoRepo();}

    LiveData<ProfileInfoModel> userProfileDetails() {
        if(userProfileInfo == null) {
            userProfileInfo = new MutableLiveData<>();
        }
        return userProfileInfo;
    }

    LiveData<ArrayList<CarModel>> userCars() {
        if(userCars == null) {
            userCars = new MutableLiveData<>();
        }
        return userCars;
    }

    LiveData<Boolean> isCarRegisterSuccessfully() {
        if(isCarSelectedFinishSuccessfully == null) {
            isCarSelectedFinishSuccessfully = new MutableLiveData<>();
        }
        return isCarSelectedFinishSuccessfully;
    }

    void getUserProfile() {
        userProfileInfo = repo.getUserInfo();
    }

    void getUserCars() {
        userCars = repo.getUserCars();
    }

    void registerCarSelected(String carRef) {
        isCarSelectedFinishSuccessfully = repo.addSelectedCar(carRef);
    }
}
