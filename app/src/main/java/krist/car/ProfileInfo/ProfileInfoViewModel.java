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

    void getUserProfile() {
        userProfileInfo = repo.getUserInfo();
    }

    void getUserCars() {
        userCars = repo.getUserCars();
    }
}
