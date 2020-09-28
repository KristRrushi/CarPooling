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
import krist.car.ProfileInfo.adapter.CarsAdapter;
import krist.car.ProfiliEdit;
import krist.car.R;

public class UserInfo extends Fragment {
    private EditText emri, tel, mosha, gjinia, numriPersona, marka, modeli, targa, ngjyra, email;
    private View myView;
    private RecyclerView carList;
    private ImageView imgUser;
    private ProfileInfoViewModel viewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.scrollview_profili_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar actionToolBar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(actionToolBar);
        setHasOptionsMenu(true);

        email = view.findViewById(R.id.user_info_email);
        emri = view.findViewById(R.id.user_info_emri_mbiemri);
        tel = view.findViewById(R.id.user_info_tel);
        marka = view.findViewById(R.id.user_info_marka);
        modeli = view.findViewById(R.id.user_info_modeli);
        targa = view.findViewById(R.id.user_info_targa);
        mosha = view.findViewById(R.id.user_info_mosha);
        gjinia = view.findViewById(R.id.user_info_gjinia);
        numriPersona = view.findViewById(R.id.user_info_personalNumber);
        ngjyra = view.findViewById(R.id.user_info_ngjyra);
        imgUser = view.findViewById(R.id.foto_user);
        carList = view.findViewById(R.id.cars_recyclerView);

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
            CarsAdapter adapter = new CarsAdapter(carModels);
            carList.setAdapter(adapter);
        });
    }

    private void populateUserInfoField(ProfileInfoModel userInfo) {
        emri.setText(userInfo.getEmri());
        tel.setText(userInfo.getPhone());
        mosha.setText(userInfo.getBirthday());
        gjinia.setText(userInfo.getGener());
        numriPersona.setText(userInfo.getPersonalIdNumber());
        Picasso.get().load(userInfo.getUserImgRef()).fit().centerCrop().into(imgUser);
    }

    private void setupCarRecyclerView() {
        carList.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbarmenu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.profil_menu:
                Intent intent = new Intent(getActivity(), ProfiliEdit.class);
                startActivity(intent);
                break;
            case R.id.makina_menu:
                break;
            case R.id.dil:
                Intent intent2 = new Intent(getActivity(), DetajeActivity.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
