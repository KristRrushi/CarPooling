package krist.car.search_trips.driver_info;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import krist.car.R;
import krist.car.models.DialogModel;
import krist.car.models.PassToTripsModel;
import krist.car.models.TripsModel;
import krist.car.models.Upload;
import krist.car.models.UploadUsersImage;
import krist.car.models.UserModel;
import krist.car.search_trips.driver_info.TripBookingInfoRepo;
import krist.car.search_trips.driver_info.TripBookingViewModel;
import krist.car.utils.Helpers;

public class TripBookingInfoActivity extends AppCompatActivity {
    private TextView mEmri, mMosha, mGjinia, mTel;
    private TextView mMarka, mModeli, mTarga, mNgjyra;
    private ImageView mUserImage , mMakImage;
    private TextView cmimi;
    private RatingBar ratingBar;
    private TextView txtRating;
    private String shoferId;
    private String tripId;
    private Button btnKonfirmo;
    private Toolbar actionToolBar;
    private TripBookingViewModel viewModel;
    private String tripPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_main_final);

        btnKonfirmo = findViewById(R.id.konfirmo_pop_main);
        mEmri = findViewById(R.id.emri_pop_main);
        mMosha = findViewById(R.id.mosha_pop_main);
        mGjinia = findViewById(R.id.gjinia_pop_main);
        mTel = findViewById(R.id.tel_pop_main);
        mMarka = findViewById(R.id.marka_pop_main);
        mModeli = findViewById(R.id.modeli_pop_main);
        mTarga = findViewById(R.id.targa_pop_main);
        mNgjyra = findViewById(R.id.ngjyra_pop_main);
        mUserImage = findViewById(R.id.shofer_photo);
        mMakImage = findViewById(R.id.makina_photo);
        cmimi = findViewById(R.id.cmimi_pop_main);
        ratingBar = findViewById(R.id.ratingbar);
        txtRating = findViewById(R.id.txtRating);

        actionToolBar = findViewById(R.id.toolbar_main_pop_up);
        setSupportActionBar(actionToolBar);
        actionToolBar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.whiteColor), PorterDuff.Mode.SRC_ATOP);

        Bundle bundle = getIntent().getBundleExtra("bS");
        shoferId = bundle.getString("shoferID");
        tripId = bundle.getString("tripID");
        tripPrice = bundle.getString("tripPrice");

        setupListeners();
        initViewModel();
        getDriverInfo(shoferId);
        getDriverCar(shoferId);
    }

    private void setupListeners() {
        btnKonfirmo.setOnClickListener(v -> { bookTrip(tripId); });
        actionToolBar.setNavigationOnClickListener(v -> finish());
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(TripBookingViewModel.class);
    }

    @SuppressLint("SetTextI18n")
    private void getDriverInfo(String driverID) {
        viewModel.getDriverInfoBaseOnId(driverID);
        viewModel.driverInfo().observe(this, driverInfo -> {
            mEmri.setText(driverInfo.getEmri());
            mMosha.setText(driverInfo.getBirthday());
            mGjinia.setText(driverInfo.getGener());
            mTel.setText(driverInfo.getPhone());
            ratingBar.setRating(driverInfo.getRating());
            txtRating.setText(driverInfo.getRating().toString());
            Picasso.get().load(driverInfo.getUserImgRef()).fit().centerCrop().into(mUserImage);
        });
    }

    @SuppressLint("SetTextI18n")
    private void getDriverCar(String driverId) {
        viewModel.getDriverSelectedCar(driverId);
        viewModel.driverCar().observe(this, car -> {
            mMarka.setText(car.getCarMarks());
            mModeli.setText(car.getCarModel());
            mTarga.setText(car.getCarPlate());
            mNgjyra.setText(car.getCarColor());
            cmimi.setText("* Cmimi i udhetimi per person eshte : " + tripPrice+" Leke. \n" +
                    "* Cmimi eshte i vendosur nga vete shoferi.");
            Picasso.get().load(car.getCarImgRef()).fit().centerCrop().into(mMakImage);
        });
    }

    private void bookTrip(String tripId) {
        viewModel.bookTrip(tripId);
        viewModel.isBookingFlowSuccess().observe(this, isSuccess -> {
            if(isSuccess) {
                Helpers.showToastMessage(this, "Rezervimi i sukseshem");
                finish();
            }
        });
    }

}
