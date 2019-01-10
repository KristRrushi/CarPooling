package krist.car;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class CarCheckAsyncTask extends AsyncTask<Void , Void, Boolean> {

    public Boolean check = false;
    private Database db = new Database();

    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private String uID = firebaseUser.getUid();




    @Override
    protected Boolean doInBackground(Void... voids) {


        db.getmDatabaseRefUsers().child(uID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild("targaMak")){

                    check = true;

                }else{
                    check = false;
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return check;
    }



}
