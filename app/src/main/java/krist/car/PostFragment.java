package krist.car;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by pampers on 12/19/2017.
 */

public class PostFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    Button btnPost;
    Calendar myCalendar = Calendar.getInstance();
    TextView txtTime;
    TextView txtDate;
    Spinner sStartCity;
    Spinner sEndCity;
    Spinner sSeats;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dataPost = database.getReference("trips");

    public PostFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.posto_fragment, container, false);


    }





    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        txtTime = (TextView) getView().findViewById(R.id.txtTime);
        txtDate = (TextView) getView().findViewById(R.id.txtDate);
        sStartCity = (Spinner) getView().findViewById(R.id.listStart);
        sEndCity = (Spinner) getView().findViewById(R.id.listEnd);
        sSeats = (Spinner) getView().findViewById(R.id.listSeats);
        btnPost = (Button) getView().findViewById(R.id.btnPostPost);

        sStartCity.setOnItemSelectedListener(this);
        sEndCity.setOnItemSelectedListener(this);
        sSeats.setOnItemSelectedListener(this);
        btnPost.setOnClickListener(this);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.qytetet_shqiperis,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sStartCity.setAdapter(adapter);
        sEndCity.setAdapter(adapter);


        ArrayAdapter<CharSequence> adapterSeats = ArrayAdapter.createFromResource(getActivity(), R.array.nr_vendeve,
                android.R.layout.simple_spinner_item);

        adapterSeats.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sSeats.setAdapter(adapterSeats);


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        txtTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), R.style.Theme_AppCompat_DayNight_Dialog, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);
                updateLabelTime();
            }


        };

        txtDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new TimePickerDialog(getActivity(), R.style.Theme_AppCompat_DayNight_Dialog, time, myCalendar
                        .get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), false).show();
            }
        });



    }

    public void showTimePickerDialog(View v) {


    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        txtTime.setText(sdf.format(myCalendar.getTime()));
    }


    private void updateLabelTime() {
        String myFormat = "hh:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        txtDate.setText(sdf.format(myCalendar.getTime()));
    }



    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner spinner = (Spinner) parent;
        if (spinner.getId() == R.id.listStart) {

            String qytetinisjes = (String) parent.getItemAtPosition(position);
            Toast.makeText(parent.getContext(), qytetinisjes, Toast.LENGTH_SHORT).show();

        } else if (spinner.getId() == R.id.listEnd) {

            String qytetimerritjes = (String) parent.getItemAtPosition(position);
            Toast.makeText(parent.getContext(), qytetimerritjes, Toast.LENGTH_SHORT).show();
            Log.d("krist", qytetimerritjes);

        } else if (spinner.getId() == R.id.listSeats) {

            String vendet = (String) parent.getItemAtPosition(position);
            Toast.makeText(parent.getContext(), vendet, Toast.LENGTH_SHORT).show();

        }

    }



    public void onNothingSelected(AdapterView<?> parent) {

    }



    public void onClick(View v) {

        if (v == btnPost) {

            postUserInfo();
        }


    }

    private void postUserInfo() {

        final String ora = txtTime.getText().toString().trim();
        final String data = txtDate.getText().toString().trim();
        final String nisja = sStartCity.getSelectedItem().toString();
        final String mberritja = sEndCity.getSelectedItem().toString();
        final String vendet = sSeats.getSelectedItem().toString();

        FirebaseUser idauth = FirebaseAuth.getInstance().getCurrentUser();
        idauth.getUid();

        String id = idauth.getUid();

        TripsModel userspost = new TripsModel(id,nisja,mberritja, data, ora, vendet);


        dataPost.push().setValue(userspost);


    }




}
