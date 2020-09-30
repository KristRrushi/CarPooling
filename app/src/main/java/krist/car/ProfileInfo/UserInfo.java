package krist.car.ProfileInfo;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import krist.car.CarCheckAsyncTask;
import krist.car.CarRegister.DetajeActivity;
import krist.car.MakinaEdit;
import krist.car.Models.DetajetModel;
import krist.car.Models.UploadUsersImage;
import krist.car.ProfileInfo.Models.CarModel;
import krist.car.ProfileInfo.Models.ProfileInfoModel;
import krist.car.ProfileInfo.adapter.CarSelectedListener;
import krist.car.ProfileInfo.adapter.CarsAdapter;
import krist.car.ProfiliEdit;
import krist.car.R;
import krist.car.Utils.Helpers;

public class UserInfo extends Fragment implements CarSelectedListener {
    private ImageView userImg;
    private TextView nameTV, phoneTV, ageTV, generTV;
    private RecyclerView userCarList;
    private ProfileInfoViewModel viewModel;
    private ImageView addCarButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_cars_info_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userImg = view.findViewById(R.id.user_info_img);
        nameTV = view.findViewById(R.id.user_info_name);
        phoneTV = view.findViewById(R.id.user_info_phone);
        ageTV = view.findViewById(R.id.user_info_age);
        generTV = view.findViewById(R.id.user_info_gener);
        userCarList = view.findViewById(R.id.user_info_car_list);
        addCarButton = view.findViewById(R.id.user_info_add_car);

        addCarButton.setOnClickListener(v -> {
            Helpers.goToActivity(getContext(), DetajeActivity.class);
        });

        setupCarRecyclerView();
        setupUserProfileViewModel();
        getUserProfileDetails();
        getUserCars();
    }

    private void setupUserProfileViewModel() {
        viewModel = new ViewModelProvider(this).get(ProfileInfoViewModel.class);
    }

    private void getUserProfileDetails() {
        viewModel.getUserProfile();
        viewModel.userProfileDetails().observe(getViewLifecycleOwner(), userData -> {
            if(userData != null) {
                populateUserInfoField(userData);
            }
        });
    }

    private void getUserCars() {
        viewModel.getUserCars();
        viewModel.userCars().observe(getViewLifecycleOwner(), carModels -> {
            CarsAdapter adapter = new CarsAdapter(carModels, this);
            userCarList.setAdapter(adapter);
        });
    }

    private void populateUserInfoField(ProfileInfoModel userInfo) {
        nameTV.setText(userInfo.getEmri());
        phoneTV.setText(userInfo.getPhone());
        ageTV.setText(userInfo.getBirthday());
        generTV.setText(userInfo.getGener());
        Picasso.get().load(userInfo.getUserImgRef()).fit().centerCrop().into(userImg);
    }

    private void setupCarRecyclerView() {
        userCarList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
    }

    @Override
    public void onCarSelected(String carRef) {
        viewModel.registerCarSelected(carRef);
        viewModel.isCarRegisterSuccessfully().observe(getViewLifecycleOwner(), isSuccess -> {
            if(isSuccess) {
                Helpers.showToastMessage(getContext(), "Makina u ruajt me sukses");
            }
        });
    }
}
