package krist.car;


import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHistoryCoDriver extends Fragment {

    private FragmentHistoryDriver.OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView ;
    private RecyclerView.LayoutManager layoutManager;
    private CoDriverAdapter adapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private List<TripsModel> tripsModelList;
    FirebaseUser idauth;


    public FragmentHistoryCoDriver() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.co_driver_recycle, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        idauth = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView = view.findViewById(R.id.co_driver_recycle_list);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        tripsModelList = new ArrayList<>();
        adapter = new CoDriverAdapter(tripsModelList);
        recyclerView.setAdapter(adapter);

        Log.v(TAG, "Set ADapter");
    }

    @Override
    public void onStart() {
        super.onStart();
        getAllTrips();

    }

    private void getAllTrips(){


        final String id = idauth.getUid();

        databaseReference.child("users").child(id).child("usersTrips").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {





                tripsModelList.clear();
                TripsModel trip = dataSnapshot.getValue(TripsModel.class);

                    tripsModelList.add(trip);

                adapter.notifyDataSetChanged();
                Log.v(TAG, "notifyDAtaSetCHanged");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(),databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });


    }
}
