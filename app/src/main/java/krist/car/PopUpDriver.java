package krist.car;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import krist.car.Models.PopUpDriverListModel;

public class PopUpDriver extends AppCompatActivity {

    private ListView listView;
    private ArrayList<PopUpDriverListModel> listPassToTrips;

    private PopUpDriverAdapter adapter;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private DatabaseReference tripsReference;
    private TextView mtxt;
    //private String tripId;
    private Database db;
    private Button mBtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Pasagjeret");


        setContentView(R.layout.pop_up_driver_layout);


        Bundle bundle = getIntent().getBundleExtra("bundleDriver");
        String tripId = bundle.getString("tripID");

        System.out.println(tripId);

        Log.v("driver ", tripId);

        mBtn = findViewById(R.id.btn_pop_co_mbyll);

        mtxt = findViewById(R.id.pop_up_driver_txt);
        mtxt.setVisibility(View.VISIBLE);




        db = new Database();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("trips").child(tripId).child("passengers");




        listView = findViewById(R.id.pop_up_driver_list);
        listPassToTrips = new ArrayList<>();
        adapter = new PopUpDriverAdapter(this, listPassToTrips);

        listView.setAdapter(adapter);


        getAllPassengers();

        checkIfHavePass(tripId);

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mbyll();
            }
        });



    }


    public void mbyll(){

        finish();


    }



    public void checkIfHavePass(String tripId){

        db.getDatabaserefTrips().child(tripId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("passengers")){
                    mtxt.setVisibility(View.INVISIBLE);
                }else {

                mtxt.setVisibility(View.VISIBLE);
                mtxt.setText("Nuk keni asnje pasagjer per momentin");
                mtxt.setTextColor(Color.RED);
                mtxt.setTextSize(20);

            }}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }




    public void getAllPassengers() {


        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {



                PopUpDriverListModel model1 = dataSnapshot.getValue(PopUpDriverListModel.class);

                listPassToTrips.add(model1);
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}
