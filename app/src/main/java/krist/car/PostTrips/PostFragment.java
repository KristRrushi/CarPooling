package krist.car.PostTrips;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import krist.car.CarRegister.DetajeActivity;
import krist.car.Models.TripsModel;
import krist.car.R;
import krist.car.Utils.Helpers;
import krist.car.common.CustomAlertDialog;
import krist.car.common.CustomDialogListener;

public class PostFragment extends Fragment implements CustomDialogListener {

    private Calendar myCalendar = Calendar.getInstance();
    private AutoCompleteTextView startPlace, endPlace;
    private TextView dateTV, timeTV;
    private EditText priceTV, seatsTV;
    private Button postTripButton;
    private PostTripsViewModel viewModel;
    private Boolean doesUserHaveCar = false;

    public PostFragment(){ }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.posto_new_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        startPlace = view.findViewById(R.id.emailRegister);
        endPlace = view.findViewById(R.id.passRegister);
        dateTV = view.findViewById(R.id.cardIdNumberRegister);
        timeTV = view.findViewById(R.id.btn_foto_personale_register);
        seatsTV = view.findViewById(R.id.post_new_vendet);
        priceTV = view.findViewById(R.id.post_new_cmimi);
        postTripButton = view.findViewById(R.id.buttonRegister);

        dateTV.setOnClickListener(view1 -> dataPicker());
        timeTV.setOnClickListener(view12 -> timePicker());
        postTripButton.setOnClickListener(v ->  postUserInfo());

        setupAdapterForCities();
        hideAllViewsKeyBoard();
        setupViewModel();
        checkIfCarsExists();
    }

    private void setupViewModel() {
        viewModel =  new ViewModelProvider(this).get(PostTripsViewModel.class);
    }

    private void checkIfCarsExists() {
        viewModel.checkIfUserHaveCar();
        viewModel.doesUserHaveCar().observe(getViewLifecycleOwner(), haveRegisterCar -> {
            if(!haveRegisterCar) {
                doesUserHaveCar = false;
                openShowDialogIfUserDoesntHaveCar();
            }
        });
    }

    private void postUserInfo() {

        if(!isFormValid()) {
            return;
        }

        if(!doesUserHaveCar) {
            Helpers.showToastMessage(getContext(), "Nuk mund te postoni udhetim nqs nuk keni nje makine te regjistrua");
            openShowDialogIfUserDoesntHaveCar();
            return;
        }

        String ora = timeTV.getText().toString().trim();
        String data = dateTV.getText().toString().trim();
        String nisja = startPlace.getText().toString();
        String mberritja = endPlace.getText().toString();
        String vendet = seatsTV.getText().toString();
        String price = priceTV.getText().toString().trim();

        final String search = nisja.toLowerCase() + " " + mberritja.toLowerCase();

        TripsModel userspost = new TripsModel("",nisja,mberritja, data, ora, vendet,"", price, search);

        viewModel.postTrip(userspost);
        viewModel.isTripPosted().observe(getViewLifecycleOwner(), isSuccess -> {
            if(isSuccess) {
                Helpers.showToastMessage(getContext(), "Postim i sukseshem");
            }else {
                Helpers.showToastMessage(getContext(), "Ndodhi nje problem me sistemin, postimi nuk u postua");
            }
        });
    }

    private Boolean isFormValid() {
        if(timeTV.getText().equals("")) {
            return false;
        }
        if (dateTV.getText().equals("")) {
            return false;
        }
        if(startPlace.getText().toString().equals("")) {
            return false;
        }
        if(endPlace.getText().toString().equals("")){
            return false;
        }
        if(seatsTV.getText().toString().equals("")){
            return false;
        }
        if(priceTV.getText().toString().equals("")){
            return false;
        }

        return true;
    }

    private void setupAdapterForCities() {
        String[] cities = getResources().getStringArray(R.array.qytetet_shqiperis);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Objects.requireNonNull(getContext()), android.R.layout.simple_list_item_1, cities);
        startPlace.setAdapter(adapter);
        endPlace.setAdapter(adapter);
    }

    private void openShowDialogIfUserDoesntHaveCar() {
        CustomAlertDialog dialog = new CustomAlertDialog("Nuk u gjet makine!", "Deshironi te regjistroni makine?", this);
        dialog.show(this.getParentFragmentManager(), "");
    }

    public void clearFields(){
       startPlace.setText(null);
       endPlace.setText(null);
       priceTV.setText(null);
       seatsTV.setText(null);
    }

    private void hideAllViewsKeyBoard(){
        startPlace.setOnFocusChangeListener((view, b) -> {
            if(!view.hasFocus()){
                hideKeyboard(view);
            }
        });

        endPlace.setOnFocusChangeListener((view, b) -> {
            if(!view.hasFocus()){
                hideKeyboard(view);
            }
        });

        priceTV.setOnFocusChangeListener((view, b) -> {
            if(!view.hasFocus()){
                hideKeyboard(view);
            }
        });

        seatsTV.setOnFocusChangeListener((view, b) -> {
            if(!view.hasFocus()){
                hideKeyboard(view);
            }
        });
    }

    private void hideKeyboard(View view){
        InputMethodManager inputMethodManager =(InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onConfirm() {
        Helpers.goToActivity(getContext(), DetajeActivity.class);
    }

    @Override
    public void onDismiss() {
        Helpers.showToastMessage(getContext(), "Duhet te regjistroni makine per te postuar udhetim");
    }

    private void dataPicker(){
        new DatePickerDialog(Objects.requireNonNull(getActivity()), R.style.Theme_AppCompat_DayNight_Dialog, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private final DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateLabel();
    };


    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateTV.setText(sdf.format(myCalendar.getTime()));
    }

    private void timePicker(){
        new TimePickerDialog(getActivity(), R.style.Theme_AppCompat_DayNight_Dialog, time, myCalendar
                .get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), false).show();
    }


    private final TimePickerDialog.OnTimeSetListener time = (view, hourOfDay, minute) -> {
        myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        myCalendar.set(Calendar.MINUTE, minute);
        updateLabelTime();
    };

    private void updateLabelTime() {
        String myFormat = "hh:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        timeTV.setText(sdf.format(myCalendar.getTime()));
    }
}
