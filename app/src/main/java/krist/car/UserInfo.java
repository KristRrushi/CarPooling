package krist.car;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static com.firebase.ui.auth.ui.email.CheckEmailFragment.TAG;

public class UserInfo extends Fragment{





    FirebaseDatabase mDatabase;
    DatabaseReference mDatabaseReference;
    DatabaseReference mDatabaseReferenceImage;

    private EditText emri,tel,mosha,gjinia,numriPersona,marka,modeli,targa,ngjyra, email;

     private View myView;

     private ImageView imgUser, imgMakina;

     private boolean flag;

     private CarCheckAsyncTask carCheck;










    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.scrollview_profili_layout, container, false);






    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        email = view.findViewById(R.id.user_info_email);
        //password = view.findViewById(R.id.user_info_password);
        emri = view.findViewById(R.id.user_info_emri_mbiemri);
        tel = view.findViewById(R.id.user_info_tel);
        marka = view.findViewById(R.id.user_info_marka);
        modeli = view.findViewById(R.id.user_info_modeli);
        targa = view.findViewById(R.id.user_info_targa);
        mosha = view.findViewById(R.id.user_info_mosha);
        gjinia = view.findViewById(R.id.user_info_gjinia);
        numriPersona = view.findViewById(R.id.user_info_personalNumber);
        ngjyra = view.findViewById(R.id.user_info_ngjyra);

        imgUser = view.findViewById(R.id.foto_user);
        imgMakina = view.findViewById(R.id.foto_makine);

        carCheck = new CarCheckAsyncTask();
        carCheck.execute();




        flag = false;






        mDatabase = FirebaseDatabase.getInstance();
        FirebaseUser idauth = FirebaseAuth.getInstance().getCurrentUser();
        final String id = idauth.getUid();


        mDatabaseReference = mDatabase.getReference("users").child(id);
        mDatabaseReferenceImage =mDatabase.getReference("imageUploads").child(id);






        email.setText(idauth.getEmail());

        myView = (View) view.findViewById(R.id.include_relative_makina);

        myView.setVisibility(View.GONE);









        Toolbar actionToolBar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(actionToolBar);

        setHasOptionsMenu(true);








    }

    @Override
    public void onStart() {
        super.onStart();

        getUserInfo();



    }








    private void getUserInfo(){


        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    DetajetModel detajetModel = dataSnapshot.getValue(DetajetModel.class);
                    emri.setText(detajetModel.getEmri());
                    tel.setText(detajetModel.getPhone());
                    mosha.setText(detajetModel.getBirthday());
                    gjinia.setText(detajetModel.getGener());
                    numriPersona.setText(detajetModel.getPersonalIdNumber());


                    if (detajetModel.getTargaMak() == null){



                       myView.setVisibility(View.GONE);
                       flag = false;




                    }else{

                        flag = true;
                        myView.setVisibility(View.VISIBLE);
                        marka.setText(detajetModel.getMarkaMak());
                        modeli.setText(detajetModel.getModeliMak());
                        targa.setText(detajetModel.getTargaMak());
                        ngjyra.setText(detajetModel.getNgjyraMak());
                    }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDatabaseReferenceImage.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                UploadUsersImage imageModel = dataSnapshot.getValue(UploadUsersImage.class);

                Picasso.get().load(imageModel.getImageUrl()).fit().centerCrop().into(imgUser);

                if (flag = true) {

                    Picasso.get().load(imageModel.getImageCarUrl()).fit().centerCrop().into(imgMakina);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }






    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbarmenu, menu);

        super.onCreateOptionsMenu(menu,inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.profil_menu :
                Intent intent = new Intent(getActivity(),ProfiliEdit.class);
                startActivity(intent);
                break;

            case R.id.makina_menu :

                checkCar();




                break;

            case R.id.dil :
                Intent intent2 = new Intent(getActivity(), DetajeActivity.class);
                startActivity(intent2);


                break;



        }



        return super.onOptionsItemSelected(item);
    }


    private void checkCar(){




        if(carCheck.check = true){

            Intent intent1 = new Intent(getActivity(), MakinaEdit.class);
            startActivity(intent1);

        }else {

            Toast.makeText(getContext(), "Regjistro makine", Toast.LENGTH_LONG).show();
        }




    }



}
