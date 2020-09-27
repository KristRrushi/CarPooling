package krist.car;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.SearchView;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

import krist.car.Models.TripsModel;

public class SearchFragment extends Fragment {


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TripsAdapter adapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private List<TripsModel> tripsModelList;
    private TripsAdapter tripsAdapter;
    private DatabaseReference databaseReferenceQuery;
    private List<TripsModel> dataSet;


    private EditText editText;
    androidx.appcompat.widget.Toolbar toolbar;

    private DatabaseReference databaseProve;


    Context mContext;

    private View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();


        setHasOptionsMenu(true);




        databaseReferenceQuery = firebaseDatabase.getReference("trips");


        databaseProve = firebaseDatabase.getReference("users");





        return inflater.inflate(R.layout.kerko_final, container, false);





    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {





        recyclerView = view.findViewById(R.id.list_trips);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        tripsModelList = new ArrayList<>();
        adapter = new TripsAdapter(tripsModelList);
        recyclerView.setAdapter(adapter);

        tripsAdapter = new TripsAdapter();

        toolbar = (androidx.appcompat.widget.Toolbar) view.findViewById(R.id.toolbar_kerko);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);


        setHasOptionsMenu(true);

        //editText = view.findViewById(R.id.txt_edit_search);


        //sen Query to FireBaseDatabase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceQuery = firebaseDatabase.getReference("trips");


        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


        ((AppCompatActivity) getActivity()).getSupportActionBar();






    }


    @Override
    public void onStart() {
        super.onStart();

        getAllTrips();




    }


    private void getAllTrips() {


        tripsModelList.clear();

        databaseReference.child("trips").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                System.out.println("There are " + dataSnapshot.getChildrenCount() + " posted trips");


                TripsModel trip = dataSnapshot.getValue(TripsModel.class);
                trip.setTripID(dataSnapshot.getKey());
                tripsModelList.add(trip);
                adapter.notifyDataSetChanged();

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
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_toolbar, menu);

        MenuItem mSearch = menu.findItem(R.id.action_search);

        SearchView mSearchView = (SearchView) mSearch.getActionView();

        mSearchView.setQueryHint("Kerko");

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {



                /*
                 if (newText.length() > 0) {

                     newText = newText.substring(0,1).toUpperCase() + newText.substring(1);

                }
                    */



                    
                Query firebaseQuery = databaseReferenceQuery.orderByChild("search").startAt(newText.toString()).endAt(newText.toString() + "\uf8ff");

                
                firebaseQuery.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        TripsModel trip = dataSnapshot.getValue(TripsModel.class);
                        //trip.setTripID(dataSnapshot.getKey());
                        tripsModelList.add(trip);
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

                adapter.getFilter().filter(newText.toString());

                return true;
            }
        });


        changeSearchTextColor(mSearchView);


    }

    private void changeSearchTextColor(View view){

        if(view != null){
            if(view instanceof TextView){
                ((TextView) view).setTextColor(Color.WHITE);
                return;
            }else if(view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for(int i=0; i < viewGroup.getChildCount(); i++){
                    changeSearchTextColor(viewGroup.getChildAt(i));
                }
            }
        }


    }
}
