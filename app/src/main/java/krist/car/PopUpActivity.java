package krist.car;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class PopUpActivity extends AppCompatActivity {

    private TextView etEmri;
    private TextView etTel;
    private TextView etTarga;
    private TextView etMarka;
    private TextView etModeli;

    // new layout

    private TextView mEmri, mMosha, mGjinia, mTel;

    private TextView mMarka, mModeli, mTarga, mNgjyra;

    private ImageView mUserImage , mMakImage;

    private TextView cmimi;

    private Button btnMbyll;

    //-------------


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
        setContentView(R.layout.popupactivitymain_prove);
        setTitle("Detajet e metejshme. Konfirmo");


        etEmri = findViewById(R.id.pop_co_emri);
        etTel = findViewById(R.id.pop_co_tel);
        etMarka = findViewById(R.id.pop_co_marka);
        etModeli = findViewById(R.id.pop_co_modeli);
        etTarga = findViewById(R.id.pop_co_targa);
        btnKonfirmo = findViewById(R.id.konfirmo_pop_main);


        //------- new layout


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

        btnMbyll = findViewById(R.id.mbyll_pop_main);

        //---------------------









        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference databaseUsers = database.getReference("users");
        final DatabaseReference databaseTrips = database.getReference("trips");
        final DatabaseReference databaseImage = database.getReference("imageUploads");



        Bundle bundle = getIntent().getBundleExtra("bS");

        shoferId = bundle.getString("shoferID");
        System.out.println(shoferId);
        tripId = bundle.getString("tripID");


        getUri();


        fillPopUp(shoferId);





        final Query query = databaseUsers.orderByKey().equalTo(shoferId);

        Log.v(TAG,"Query exe");




      btnMbyll.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              mbyllfunction();
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
                final String uri = model.getImageCarUrl();
                System.out.println(uri);
                Log.v("POP ACTIVITY : ", uri);


                btnKonfirmo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {





                        FirebaseUser idauth = FirebaseAuth.getInstance().getCurrentUser();
                        final String id = idauth.getUid();


                        final DatabaseReference databaseUsers = database.getReference("users");
                        final DatabaseReference databaseTrips = database.getReference("trips");

                        Log.v(TAG, "onDataChange: 0");


                       // prove e re

                        final DatabaseReference databaseTripsProve = database.getReference("trips").child(tripId);

                        databaseTripsProve.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                TripsModel tripsModel2 = dataSnapshot.getValue(TripsModel.class);
                                String nisja = tripsModel2.getvNisja();
                                String mberritja = tripsModel2.getvMberritja();
                                String ora = tripsModel2.getOra();
                                String data = tripsModel2.getData();
                                String vendet = tripsModel2.getVendet();


                                int vendetInt = Integer.parseInt(vendet);

                                if(vendetInt >=1) {


                                    TripsModel tripsModel1 = new TripsModel(shoferId, nisja, mberritja, ora, data, vendet, uri);

                                    databaseUsers.child(id).child("usersTrips").push().setValue(tripsModel1);

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });





                        //end prove e re






                        /*Query query1 = databaseTrips.orderByKey().equalTo(tripId);

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




                                    int vendetInt = Integer.parseInt(vendet);

                                    if(vendetInt >=1) {


                                        TripsModel tripsModel1 = new TripsModel(shoferId, nisja, mberritja, ora, data, vendet, uri);

                                        databaseUsers.child(id).child("usersTrips").push().setValue(tripsModel1);

                                    }




                                }











                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

*/




                        vendetfunction();



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

    private void vendetfunction() {

        final DatabaseReference databaseTripsVendet = database.getReference("trips").child(tripId);




        databaseTripsVendet.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                TripsModel model = dataSnapshot.getValue(TripsModel.class);
                String vendet = model.getVendet();



                int vendetInt = Integer.parseInt(vendet);

                if(vendetInt >= 1){

                vendetInt--;

                    passToTrips();




                String vendetUpdate = Integer.toString(vendetInt);

                Map<String , Object> vendetMap = new HashMap<String, Object>();

                vendetMap.put("vendet", vendetUpdate);
                databaseTripsVendet.updateChildren(vendetMap);
                }else {
                    Toast.makeText(PopUpActivity.this, "Udhetimi i plostesuar", Toast.LENGTH_LONG).show();
                }






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
                   String name = dialogModel.getEmri();
                   String tel = dialogModel.getPhone();
                   String mosha = dialogModel.getBirthday();
                   String gjinia = dialogModel.getGener();


                   Log.v("EMRI ", name);


                   PassToTrips passToTrips = new PassToTrips(name, tel, mosha, gjinia);



                   databaseTrips.child(tripId).child("passengers").child(id).setValue(passToTrips);




               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });




           databaseImage.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   UploadUsersImage model = dataSnapshot.getValue(UploadUsersImage.class);
                   String imageUrlPass = model.getImageUrl();

                   System.out.println(imageUrlPass);


                   Map<String, Object> mapUri = new HashMap<String, Object>();
                   mapUri.put("imageUrl", imageUrlPass);

                   databaseTrips.child(tripId).child("passengers").child(id).updateChildren(mapUri);






               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });








    }


    private void fillPopUp(String shoferId){





        databaseUsers.child(shoferId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DialogModel model = dataSnapshot.getValue(DialogModel.class);
                mEmri.setText(model.getEmri());
                mMosha.setText(model.getBirthday() + " vjec");
                mGjinia.setText(model.getGener());
                mTel.setText(model.getPhone());

                mMarka.setText(model.getMarkaMak());
                mModeli.setText(model.getModeliMak());
                mTarga.setText(model.getTargaMak());
                mNgjyra.setText(model.getNgjyraMak());




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        databaseTrips.child(tripId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DialogModel model = dataSnapshot.getValue(DialogModel.class);
                cmimi.setText( "Cmimi i udhetimi per person eshte : " + model.getCmimi()+" Leke. \n" +
                        "Cmim eshte i vendosur nga vete shoferi."
                );
                cmimi.setTextColor(Color.RED);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        databaseImage.child(shoferId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                Upload modelImageCar = dataSnapshot.getValue(Upload.class);
                UploadUsersImage modelImageUser = dataSnapshot.getValue(UploadUsersImage.class);

                Picasso.get().load(modelImageCar.getImageCarUrl()).fit().centerCrop().into(mMakImage);
                Picasso.get().load(modelImageUser.getImageUrl()).fit().centerCrop().into(mUserImage);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }


    private void mbyllfunction(){

       Intent intent = new Intent(PopUpActivity.this, MainActivity.class);




       startActivity(intent);


    }




}
