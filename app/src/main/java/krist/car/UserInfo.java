package krist.car;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserInfo extends Fragment{


    private TextView email,password,emri,tel,marka,modeli,targa;
    private Button updata;

    FirebaseDatabase mDatabase;
    DatabaseReference mDatabaseReference;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_info_profil, container, false);





    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        email = view.findViewById(R.id.user_info_email);
        password = view.findViewById(R.id.user_info_password);
        emri = view.findViewById(R.id.user_info_emri_mbiemri);
        tel = view.findViewById(R.id.user_info_tel);
        marka = view.findViewById(R.id.user_info_marka);
        modeli = view.findViewById(R.id.user_info_modeli);
        targa = view.findViewById(R.id.user_info_targa);

        updata = view.findViewById(R.id.btn_update);

        mDatabase = FirebaseDatabase.getInstance();
        FirebaseUser idauth = FirebaseAuth.getInstance().getCurrentUser();
        final String id = idauth.getUid();


        mDatabaseReference = mDatabase.getReference("users").child(id);






        email.setText(idauth.getEmail());


        updata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference db = mDatabase.getReference("users").child("JTfyZom6WFgzz55q2tI803ogBMS2");
                db.removeValue();
            }
        });




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
                    marka.setText(detajetModel.getMarkaMak());
                    modeli.setText(detajetModel.getModeliMak());
                    targa.setText(detajetModel.getTargaMak());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }


















}
