package krist.car;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import krist.car.Models.DialogModel;
import krist.car.Models.Upload;
import krist.car.Models.UploadUsersImage;
import krist.car.Models.UserModel;

public class PopUpCoDriver extends AppCompatActivity {

    private static final String TAG = "pop up co :";
    private TextView metEmri;
    private TextView metTel;
    private TextView metTarga;
    private TextView metMarka;
    private TextView metModeli;
    private TextView metMosha;
    private TextView metGjinia;
    private TextView metNgjyra;
    private ImageView imgShofer, imgMak;
    private RatingBar ratingBar;




    private String shoferId;
    private String tripId;

    private Button btn_mbyll;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_co_driver);
        setTitle("Shoferi detaje");




        metEmri = findViewById(R.id.pop_co_emri);
        metTel = findViewById(R.id.pop_co_tel);
        metMarka = findViewById(R.id.pop_co_marka);
        metModeli = findViewById(R.id.pop_co_modeli);
        metTarga = findViewById(R.id.pop_co_targa);
        btn_mbyll = findViewById(R.id.btn_pop_co_mbyll);
        metMosha = findViewById(R.id.pop_co_mosha);
        metGjinia = findViewById(R.id.pop_co_gjinia);
        metNgjyra = findViewById(R.id.pop_co_ngjyra);

        imgShofer = findViewById(R.id.pop_co_shofer_photo);
        imgMak = findViewById(R.id.pop_co_makina_photo);
        ratingBar = findViewById(R.id.ratingbar1);



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
                metGjinia.setText(dialogModel.getGener());
                metMosha.setText(dialogModel.getBirthday());
                metNgjyra.setText(dialogModel.getNgjyraMak());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        btn_mbyll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mbyll();
            }
        });





        imageDownLoad();

    }






    private void mbyll(){






        if(ratingBar.getRating() == 0.0){
            Toast.makeText(PopUpCoDriver.this, "Shoferi nuk u vleresua", Toast.LENGTH_LONG).show();
        }else {

            database.getReference("users").child(shoferId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    UserModel users = dataSnapshot.getValue(UserModel.class);

                    if (users.getRating() == 0) {
                        Map<String, Object> mapUri = new HashMap<String, Object>();
                        mapUri.put("rating", ratingBar.getRating());


                        database.getReference("users").child(shoferId).updateChildren(mapUri);
                    } else {



                        Float totalRating = (users.getRating() + ratingBar.getRating()) / 2;

                        System.out.println(totalRating);

                        Map<String, Object> mapUri = new HashMap<String, Object>();
                        mapUri.put("rating", totalRating);


                        database.getReference("users").child(shoferId).updateChildren(mapUri);

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


           Toast.makeText(PopUpCoDriver.this, "Shoferi u vleresua me " + ratingBar.getRating() + " yje." , Toast.LENGTH_LONG ).show();

        }

        finish();

    }





    private void imageDownLoad(){

        database.getReference("imageUploads").child(shoferId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Upload modelImageCar = dataSnapshot.getValue(Upload.class);
                UploadUsersImage modelImageUser = dataSnapshot.getValue(UploadUsersImage.class);

                Picasso.get().load(modelImageCar.getImageCarUrl()).fit().centerCrop().into(imgMak);
                Picasso.get().load(modelImageUser.getImageUrl()).fit().centerCrop().into(imgShofer);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }






}
