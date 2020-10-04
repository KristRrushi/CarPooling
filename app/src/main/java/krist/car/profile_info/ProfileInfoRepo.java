package krist.car.profile_info;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import krist.car.api.ApiSingleton;
import krist.car.profile_info.Models.CarModel;
import krist.car.profile_info.Models.ProfileInfoModel;

class ProfileInfoRepo {
    private ApiSingleton api;

    ProfileInfoRepo() {api = ApiSingleton.getInstance();}

    MutableLiveData<ProfileInfoModel> getUserInfo() {
        final MutableLiveData<ProfileInfoModel> userInfo = new MutableLiveData<>();

        String userId = api.getUserUId();

        api.getDatebaseReferenceToThisEndPoint("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProfileInfoModel model = dataSnapshot.getValue(ProfileInfoModel.class);
                userInfo.setValue(model);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //TODO find a way to handle error
            }
        });
        return userInfo;
    }

    //return list of cars
    MutableLiveData<ArrayList<CarModel>> getUserCars() {
        final MutableLiveData<ArrayList<CarModel>> userCars = new MutableLiveData<>();
        ArrayList<CarModel> cars = new ArrayList<>();

        String userId = api.getUserUId();

        api.getDatebaseReferenceToThisEndPoint("user_cars").child(userId).child("cars").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot carSnapshot: dataSnapshot.getChildren()) {
                    CarModel car = carSnapshot.getValue(CarModel.class);
                    assert car != null;
                    car.setCarKey(carSnapshot.getKey());
                    cars.add(car);
                }
                userCars.setValue(cars);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return userCars;
    }

    MutableLiveData<Boolean> addSelectedCar(String carRef) {
        final MutableLiveData<Boolean> isSuccess = new MutableLiveData<>();

        Map<String, Object> selectedCarUpdate = new HashMap<>();
        selectedCarUpdate.put("selected_car", carRef);

        String userId = api.getUserUId();
        api.getDatebaseReferenceToThisEndPoint("user_cars").child(userId).updateChildren(selectedCarUpdate).addOnCompleteListener(task -> {
            isSuccess.setValue(task.isSuccessful());
        });

        return isSuccess;
    }
}
