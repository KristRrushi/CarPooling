package krist.car;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentTransaction;

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

import krist.car.Models.TripsModel;
import krist.car.Models.Upload;

public class PostFragment extends Fragment {


    Button btnPost;
    Calendar myCalendar = Calendar.getInstance();
    String uRL;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dataPost = database.getReference("trips");
    DatabaseReference imageUp = database.getReference("imageUploads");

    private AutoCompleteTextView mNisja, mMberritja;
    private TextView mData, mOra;
    private EditText mCmimi, mVendet;
    private Button posto;

    public PostFragment(){ }

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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        return inflater.inflate(R.layout.posto_new_layout, container, false);
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

        hideAllViewsKeyBoard();
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

        final String search = nisja.toLowerCase() + " " + mberritja.toLowerCase();

        if(!ora.isEmpty() && !data.isEmpty() && !nisja.isEmpty() && !mberritja.isEmpty() && !vendet.isEmpty() && !price.isEmpty()){

            String pushKey = dataPost.push().getKey();

            TripsModel userspost = new TripsModel(id,nisja,mberritja, data, ora, vendet,uRL,pushKey, price, search);

            dataPost.child(pushKey).setValue(userspost);

            clearFields();

            FragmentTransaction t = this.getFragmentManager().beginTransaction();
            Fragment fragment1 = new SearchFragment();
            t.replace(R.id.main_frame, fragment1);
            t.commit();

        }else {
            Toast.makeText(getActivity(), "Plotesoni te gjitha te dhenat", Toast.LENGTH_LONG).show();
        }
    }

    public void clearFields(){
       mNisja.setText(null);
       mMberritja.setText(null);
       mCmimi.setText(null);
       mVendet.setText(null);
    }




    private void hideAllViewsKeyBoard(){
        mNisja.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!view.hasFocus()){
                    hideKeyboard(view);
                }
            }
        });

        mMberritja.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!view.hasFocus()){
                    hideKeyboard(view);
                }
            }
        });

        mCmimi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!view.hasFocus()){
                    hideKeyboard(view);
                }
            }
        });

        mVendet.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!view.hasFocus()){
                    hideKeyboard(view);
                }
            }
        });
    }



    private void hideKeyboard(View view){
        InputMethodManager inputMethodManager =(InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }
}
