package krist.car;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PopUpCoDriver extends AppCompatActivity {

    private static final String TAG = "pop up co :";
    private TextView metEmri;
    private TextView metTel;
    private TextView metTarga;
    private TextView metMarka;
    private TextView metModeli;


    private String shoferId;
    private String tripId;

    private Button btn_mbyll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_co_driver);
        setTitle("Shofer detaje");

        metEmri = findViewById(R.id.pop_co_emri);
        metTel = findViewById(R.id.pop_co_tel);
        metMarka = findViewById(R.id.pop_co_marka);
        metModeli = findViewById(R.id.pop_co_modeli);
        metTarga = findViewById(R.id.pop_co_targa);
        //btn_mbyll = findViewById(R.id.btn_pop_co_mbyll);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();



        Bundle bundle = getIntent().getBundleExtra("codriver");

        shoferId = bundle.getString("shoferID2");
        System.out.println("PAra sout te id");
        System.out.println(shoferId);


        tripId = bundle.getString("tripID2");

        String prove = "KenqVGRnScZocW7HHwQlvoh7SuD2";

        Log.v(TAG, shoferId);


        metTel.setText(shoferId);








        DatabaseReference databaseUsers = database.getReference("users").child(shoferId);


        databaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DialogModel dialogModel = dataSnapshot.getValue(DialogModel.class);

                metEmri.setText(dialogModel.getEmri());
                metTel.setText(dialogModel.getPhone());
                metMarka.setText(dialogModel.getMarkaMak());
                metModeli.setText(dialogModel.getModeliMak());
                metTarga.setText(dialogModel.getTargaMak());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



/*
        btn_mbyll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mbyll();
            }
        });*/



    }






    private void mbyll(){
        Intent intent = new Intent(PopUpCoDriver.this ,MainActivity.class);
        startActivity(intent);

    }











}
