package krist.car;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by pampers on 12/19/2017.
 */

public class PostFragment extends Fragment {


    Button btnPost;
    Calendar myCalendar = Calendar.getInstance();
    //TextView txtTime;
    //TextView txtDate;
    Spinner sStartCity;
    Spinner sEndCity;
    Spinner sSeats;
    String uRL;
    private EditText cmimi;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dataPost = database.getReference("trips");
    DatabaseReference imageUp = database.getReference("imageUploads");

    private Button txtTime,txtDate;



   //------------------ new layout


    private AutoCompleteTextView mNisja, mMberritja;
    private TextView mData, mOra;
    private EditText mCmimi, mVendet;
    private Button posto;
















    public PostFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //if user has car


        FirebaseUser idauth = FirebaseAuth.getInstance().getCurrentUser();
        final String id = idauth.getUid();



        imageUp.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              Upload upload = dataSnapshot.getValue(Upload.class);

                String uID = upload.getImageCarUrl().toString();



                uRL = uID;

                Log.v("Listener ", uID);





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }





        });





        return inflater.inflate(R.layout.posto_new_layout, container, false);
        //else
       // return inflater.inflate(R.layout.activity_detaje, container, false);

    }





    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        String[] qytetet = getResources().getStringArray(R.array.qytetet_shqiperis);


        mNisja = view.findViewById(R.id.emailRegister);
        mMberritja = view.findViewById(R.id.passRegister);
        mData = view.findViewById(R.id.cardIdNumberRegister);
        mOra = view.findViewById(R.id.btn_foto_personale_register);
        mVendet = view.findViewById(R.id.post_new_vendet);
        mCmimi = view.findViewById(R.id.post_new_cmimi);
        posto = view.findViewById(R.id.buttonRegister);






        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, qytetet);
        mNisja.setAdapter(adapter);
        mMberritja.setAdapter(adapter);



        mData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataPicker();
            }
        });


        mOra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePicker();
            }
        });







        posto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseUser idauth = FirebaseAuth.getInstance().getCurrentUser();
                final String id = idauth.getUid();





                postUserInfo(id);


            }
        });





















    }




    public void dataPicker(){

        new DatePickerDialog(getActivity(), R.style.Theme_AppCompat_DayNight_Dialog, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }



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


    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mData.setText(sdf.format(myCalendar.getTime()));
    }





    public void timePicker(){

        new TimePickerDialog(getActivity(), R.style.Theme_AppCompat_DayNight_Dialog, time, myCalendar
                .get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), false).show();

    }


   final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            myCalendar.set(Calendar.MINUTE, minute);
            updateLabelTime();
        }


    };









    private void updateLabelTime() {
        String myFormat = "hh:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        mOra.setText(sdf.format(myCalendar.getTime()));
    }

















    private void postUserInfo(String id) {











        final String ora = mOra.getText().toString().trim();
        final String data = mData.getText().toString().trim();
        final String nisja = mNisja.getText().toString();
        final String mberritja = mMberritja.getText().toString();
        final String vendet = mVendet.getText().toString();
        final String uri = uRL;
        final String price = mCmimi.getText().toString().trim();


        Log.v("pospot", mberritja);







        Toast.makeText(getActivity(),uRL,Toast.LENGTH_LONG).show();

        String pushKey = dataPost.push().getKey();

        TripsModel userspost = new TripsModel(id,nisja,mberritja, data, ora, vendet,uRL,pushKey, price);







        dataPost.child(pushKey).setValue(userspost);





    }









}
