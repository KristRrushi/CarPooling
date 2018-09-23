package krist.car;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class PopUpDriver extends AppCompatActivity  {

    private ListView listView;
    private ArrayList<PassToTrips> listPassToTrips;

    private PopUpDriverAdapter adapter;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    //private String tripId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_driver_layout);
        setTitle("Pasagjeret");

        Bundle bundle = getIntent().getBundleExtra("bundleDriver");
       String tripId = bundle.getString("tripID");

        System.out.println(tripId);

      Log.v("driver ", tripId);



        database = FirebaseDatabase.getInstance();
        reference = database.getReference("trips").child(tripId).child("passengers");



        listView = findViewById(R.id.pop_up_driver_list);
        listPassToTrips = new ArrayList<>();
        adapter = new PopUpDriverAdapter(this,listPassToTrips);

        listView.setAdapter(adapter);





        getAllPassengers();









    }

   public void getAllPassengers(){


        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                PassToTrips model = dataSnapshot.getValue(PassToTrips.class);
                String emri = model.getEmri();
                Log.v("E<RI", emri);
                System.out.println(emri);
                System.out.println("krist");

                listPassToTrips.add(model);
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
