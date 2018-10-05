package krist.car;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class UserInfo extends Fragment{


    //private TextView email,password, editMakinaHead;
    private Button updata, updateAll;

    FirebaseDatabase mDatabase;
    DatabaseReference mDatabaseReference;
    DatabaseReference mDatabaseReferenceImage;

    private EditText emri,tel,mosha,gjinia,numriPersona,marka,modeli,targa,ngjyra, email;

     private View myView;

     private ImageView imgUser, imgMakina;

     private boolean flag;






    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
       // editMakinaHead = view.findViewById(R.id.txt_user_info_profili_mak);
        updateAll = view.findViewById(R.id.btn_change_info_all);

        imgUser = view.findViewById(R.id.foto_user);
        imgMakina = view.findViewById(R.id.foto_makine);

        flag = false;



        updata = view.findViewById(R.id.btn_change_info);

        mDatabase = FirebaseDatabase.getInstance();
        FirebaseUser idauth = FirebaseAuth.getInstance().getCurrentUser();
        final String id = idauth.getUid();


        mDatabaseReference = mDatabase.getReference("users").child(id);
        mDatabaseReferenceImage =mDatabase.getReference("imageUploads").child(id);






        email.setText(idauth.getEmail());

        myView = (View) view.findViewById(R.id.include_relative_makina);

        myView.setVisibility(View.GONE);

        updateAll.setVisibility(View.GONE);










    }

    @Override
    public void onStart() {
        super.onStart();
        getUserInfo();

       updata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(),"krist rrushu", Toast.LENGTH_LONG).show();
                updateInfoFunction();
            }
        });



       updateAll.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              updateInfoAllFunction();
           }
       });
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



                      /*  targa.setText("N/A");
                        modeli.setText("N/A");
                        marka.setText("N/A");
                        ngjyra.setText("N/A");
                        targa.setEnabled(false);
                        modeli.setEnabled(false);
                        marka.setEnabled(false);
                        ngjyra.setEnabled(false);*/
                       // editMakinaHead.setText("NUK KENI ASNJE MAKINE EKSIZTUESE");
                        //editMakinaHead.setTextColor(Color.RED);


                    }else{

                        flag = true;
                        myView.setVisibility(View.VISIBLE);
                        updata.setVisibility(View.GONE);
                        updateAll.setVisibility(View.VISIBLE);


                       /* targa.setEnabled(true);
                        modeli.setEnabled(true);
                        marka.setEnabled(true);
                        ngjyra.setEnabled(true);*/
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


    private void updateInfoFunction(){




        String mEmri = emri.getText().toString();
        String mTel = tel.getText().toString();

        String mMosha = mosha.getText().toString();
        String mGjinia = gjinia.getText().toString();
        String mPersonalNumber = numriPersona.getText().toString();





            Map<String, Object> emriMap = new HashMap<String, Object>();
            emriMap.put("emri", mEmri);
            emriMap.put("phone", mTel);
            emriMap.put("birthday", mMosha);
            emriMap.put("gener", mGjinia);
            emriMap.put("personalIdNumber", mPersonalNumber);



            mDatabaseReference.updateChildren(emriMap);







    }

    private void updateInfoAllFunction(){

        String mEmri = emri.getText().toString();
        String mTel = tel.getText().toString();
        String mMarka = marka.getText().toString();
        String mModeli = modeli.getText().toString();
        String mTarga = targa.getText().toString();
        String mMosha = mosha.getText().toString();
        String mGjinia = gjinia.getText().toString();
        String mPersonalNumber = numriPersona.getText().toString();
        String mNgjyra = ngjyra.getText().toString();


        Map<String, Object> emriMap = new HashMap<String, Object>();
        emriMap.put("emri", mEmri);
        emriMap.put("phone", mTel);
        emriMap.put("birthday", mMosha);
        emriMap.put("gener", mGjinia);
        emriMap.put("personalIdNumber", mPersonalNumber);


        emriMap.put("markaMak", mMarka);
        emriMap.put("modeliMak", mModeli);
        emriMap.put("targaMak", mTarga);
        emriMap.put("ngjyraMak", mNgjyra);

        mDatabaseReference.updateChildren(emriMap);

    }


















}
