package krist.car.ProfileInfo;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import krist.car.Api.ApiSingleton;
import krist.car.ProfileInfo.Models.CarModel;
import krist.car.ProfileInfo.Models.ProfileInfoModel;

public class ProfileInfoRepo {
    private ApiSingleton api;

    ProfileInfoRepo() {api = ApiSingleton.getInstance();}

    public MutableLiveData<ProfileInfoModel> getUserInfo() {
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
    public MutableLiveData<ArrayList<CarModel>> getUserCars() {
        final MutableLiveData<ArrayList<CarModel>> userCars = new MutableLiveData<>();
        ArrayList<CarModel> cars = new ArrayList<>();

        String userId = api.getUserUId();

        api.getDatebaseReferenceToThisEndPoint("user_cars").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot carSnapshot: dataSnapshot.getChildren()) {
                    CarModel car = carSnapshot.getValue(CarModel.class);
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
}
