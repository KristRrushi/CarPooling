package krist.car;

import android.content.Intent;
import android.graphics.drawable.PaintDrawable;
import android.nfc.Tag;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import static android.content.ContentValues.TAG;

public class PopUpActivity extends AppCompatActivity {

    private TextView etEmri;
    private TextView etTel;
    private TextView etTarga;
    private TextView etMarka;
    private TextView etModeli;


    private String shoferId;
    private String tripId;

    private Button btnKonfirmo;

    private static final String TAG = "popActivity";


    private String imageUri;


    final FirebaseDatabase database = FirebaseDatabase.getInstance();


    final DatabaseReference databaseImage = database.getReference("imageUploads");

    final DatabaseReference databaseUsers = database.getReference("users");

    final DatabaseReference databaseTrips = database.getReference("trips");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_co_driver);
        setTitle("Detajet e metejshme. Konfirmo");


        etEmri = findViewById(R.id.pop_co_emri);
        etTel = findViewById(R.id.pop_co_tel);
        etMarka = findViewById(R.id.pop_co_marka);
        etModeli = findViewById(R.id.pop_co_modeli);
        etTarga = findViewById(R.id.pop_co_targa);
        btnKonfirmo = findViewById(R.id.btn_pop_co_mbyll);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference databaseUsers = database.getReference("users");
        final DatabaseReference databaseTrips = database.getReference("trips");
        final DatabaseReference databaseImage = database.getReference("imageUploads");

        Bundle bundle = getIntent().getBundleExtra("bS");

        shoferId = bundle.getString("shoferID");
        tripId = bundle.getString("tripID");


        getUri();






        final Query query = databaseUsers.orderByKey().equalTo(shoferId);

        Log.v(TAG,"Query exe");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                Log.v(TAG,"mbushja e popup");

                for (DataSnapshot child: children) {
                    DialogModel dialogModel = child.getValue(DialogModel.class);

                    Log.v(TAG,"mbushja e popup2");

                    etEmri.setText(dialogModel.getEmri());
                    etTel.setText(dialogModel.getPhone());
                    etTarga.setText(dialogModel.getTargaMak());
                    etModeli.setText(dialogModel.getModeliMak());
                    etMarka.setText(dialogModel.getMarkaMak());

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }



    private void getUri(){




        FirebaseUser idauth = FirebaseAuth.getInstance().getCurrentUser();
        final String id = idauth.getUid();


        databaseImage.child(shoferId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final Upload model = dataSnapshot.getValue(Upload.class);
                final String uri = model.getmImageUrl();
                System.out.println(uri);
                Log.v("POP ACTIVITY : ", uri);


                btnKonfirmo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        passToTrips();


                        FirebaseUser idauth = FirebaseAuth.getInstance().getCurrentUser();
                        final String id = idauth.getUid();


                        final DatabaseReference databaseUsers = database.getReference("users");
                        final DatabaseReference databaseTrips = database.getReference("trips");

                        Log.v(TAG, "onDataChange: 0");



                        Query query1 = databaseTrips.orderByKey().equalTo(tripId);

                        query1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                                for (DataSnapshot child: children){
                                    TripsModel tripsModel2 = child.getValue(TripsModel.class);
                                    String nisja = tripsModel2.getvNisja();
                                    String mberritja = tripsModel2.getvMberritja();
                                    String ora = tripsModel2.getOra();
                                    String data = tripsModel2.getData();
                                    String vendet = tripsModel2.getVendet();
                                    String idFalse = "1";
                                    String uriFALSE = "1";


                                    TripsModel tripsModel1 = new TripsModel(shoferId,nisja,mberritja,ora,data,vendet,uri);

                                    databaseUsers.child(id).child("usersTrips").push().setValue(tripsModel1);



                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });









                        Intent intent = new Intent(PopUpActivity.this ,MainActivity.class);
                        startActivity(intent);









                    }
                });






            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });







    }





    private void passToTrips(){



        FirebaseUser idauth = FirebaseAuth.getInstance().getCurrentUser();
        final String id = idauth.getUid();



        databaseUsers.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DialogModel dialogModel = dataSnapshot.getValue(DialogModel.class);
                String name  = dialogModel.getEmri();
                String tel = dialogModel.getPhone();


                Log.v("EMRI ", name);



                PassToTrips passToTrips = new PassToTrips(name, tel);

                databaseTrips.child(tripId).child("passengers").push().setValue(passToTrips);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });







    }





}
