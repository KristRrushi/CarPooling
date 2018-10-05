package krist.car;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.SearchView;
import android.widget.Toolbar;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static android.content.ContentValues.TAG;

//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by pampers on 12/19/2017.
 */

public class KerkoFragment extends Fragment {


    private RecyclerView recyclerView ;
    private RecyclerView.LayoutManager layoutManager;
    private TripsAdapter adapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private List<TripsModel> tripsModelList;
    private TripsAdapter tripsAdapter;
    private DatabaseReference databaseReferenceQuery;
    private List<TripsModel> dataSet;


    private EditText editText;
    android.support.v7.widget.Toolbar toolbar;

    private DatabaseReference databaseProve;




    Context mContext;

    private View view;





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();












        setHasOptionsMenu(true);

      //c  mContext.getApplicationContext();


        databaseReferenceQuery = firebaseDatabase.getReference("trips");



        databaseProve = firebaseDatabase.getReference("users");


       /* editText = (EditText) inflater.inflate(R.layout.kerko_fragment, container, false).findViewById(R.id.search_text34);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                filter(editable.toString());
            }
        });*/





        return inflater.inflate(R.layout.kerko_final, container, false);















    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {

        //getActivity().setTitle("KerkoFragment1");





        //EditText editText = view.findViewById(R.id.search_text);

        recyclerView = view.findViewById(R.id.list_trips);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        tripsModelList = new ArrayList<>();
        adapter = new TripsAdapter(tripsModelList);
        recyclerView.setAdapter(adapter);

        tripsAdapter = new TripsAdapter();

       toolbar = (android.support.v7.widget.Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        setHasOptionsMenu(true);

        editText = view.findViewById(R.id.txt_edit_search);







        //sen Query to FireBaseDatabase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceQuery = firebaseDatabase.getReference("trips");










        /*editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                filter(editable.toString());
            }
        });*/



        

        super.onViewCreated(view, savedInstanceState);










    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


        ((AppCompatActivity) getActivity()).getSupportActionBar();




       ;




    }


/*
    private void filter(final String text){
        Query firebaseQuery =  databaseReferenceQuery.orderByChild("vNisja").startAt(text).endAt(text + "\uf8ff");

        firebaseQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                ArrayList<TripsModel> filteredList = new ArrayList<>();

                for(TripsModel model : tripsModelList ){
                    if(model.getvNisja().toLowerCase().contains(text.toLowerCase())){
                        filteredList.add(model);
                    }
                }

                tripsAdapter.filterList(filteredList);


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


    }*/



    @Override
    public void onStart() {
        super.onStart();
       getAllTrips();



       // searchDatabaseFilter("Tirana");


        /*FirebaseRecyclerOptions<TripsModel> options =
                new FirebaseRecyclerOptions.Builder<TripsModel>().setQuery(databaseReferenceQuery, TripsModel.class).build();


        FirebaseRecyclerAdapter<TripsModel, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<TripsModel, ViewHolder>(options) {


                    @Override
                    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull TripsModel model) {

                      //  holder.setDetails(getContext(), model.getvNisja(), model.getvMberritja(), model.getOra(), model.getData() );


                        holder.mNisja.setText(dataSet.get(position).getvNisja());
                        holder.mMberritja.setText(dataSet.get(position).getvMberritja());
                        holder.mOra.setText(dataSet.get(position).getOra());
                        holder.mData.setText(dataSet.get(position).getData());


                        Log.v(TAG,"ek");
                    }

                    @NonNull
                    @Override
                    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View trips_view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.trips_list_item,parent,false);



                        return new ViewHolder(trips_view);
                    }
                };




        recyclerView.setAdapter(firebaseRecyclerAdapter);

*/

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                Query firebaseQuery =  databaseReferenceQuery.orderByChild("vNisja").startAt(charSequence.toString()).endAt(charSequence.toString() + "\uf8ff");

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

                adapter.getFilter().filter(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }



    private void getAllTrips(){



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
                Toast.makeText(getActivity(),databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });



    }

    /*public void searchDatabase(String searchText){

       Query firebaseQuery =  databaseReferenceQuery.orderByChild("vNisja").startAt(searchText).endAt(searchText + "\uf8ff");

       firebaseQuery.addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
               TripsModel trip = dataSnapshot.getValue(TripsModel.class);

               tripsModelList.clear();
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


    }*/


   /* private void searchDatabaseFilter(String searchText){
        Log.v(TAG,"vjen");

        Query firebaseQuery =  databaseReferenceQuery.orderByChild("vNisja").startAt(searchText).endAt(searchText + "\uf8ff");

        FirebaseRecyclerOptions<TripsModel> options =
                new FirebaseRecyclerOptions.Builder<TripsModel>().setQuery(firebaseQuery, TripsModel.class).build();
        Log.v(TAG,"vjen3");

        FirebaseRecyclerAdapter adapter1 = new FirebaseRecyclerAdapter<TripsModel, TripsAdapter.ViewHolder>(options) {



            @Override


            protected void onBindViewHolder(@NonNull TripsAdapter.ViewHolder holder, int position, @NonNull TripsModel model) {


                holder.vMberritja.setText(dataSet.get(position).getvMberritja());
                holder.vNisja.setText(dataSet.get(position).getvNisja());
                holder.data.setText(dataSet.get(position).getData());
                holder.ora.setText(dataSet.get(position).getOra());
            }




            @NonNull
            @Override
            public TripsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View trips_view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.trips_list_item,parent,false);



                return new TripsAdapter.ViewHolder(trips_view);

            }

        };

        Log.v(TAG,"vjen4");
        recyclerView.setAdapter(adapter1);


    }
*/





    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

       inflater.inflate(R.menu.toolbarmenu, menu);
        MenuItem item = menu.findItem(R.id.action_search1);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {




                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                //String filterPatternn = s.toString().toLowerCase().trim();

                Query firebaseQuery =  databaseReferenceQuery.orderByChild("vNisja").startAt(s).endAt(s + "\uf8ff");

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

                adapter.getFilter().filter(s);


                return false;

            }
        });





        super.onCreateOptionsMenu(menu, inflater);

    }

  /*  @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem searchMenuItem = menu.findItem(R.id.action_search1);
       SearchView searchView = (SearchView) searchMenuItem.getActionView();

       searchView.setQueryHint("kerki");
       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               return false;
           }

           @Override
           public boolean onQueryTextChange(String newText) {
               adapter.getFilter().filter(newText);
               return false;
           }
       });

    }*/




}
