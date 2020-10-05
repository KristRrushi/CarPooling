package krist.car.search_trips;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import krist.car.PopUpActivity;
import krist.car.R;
import krist.car.search_trips.adapters.OnTripClickedListener;
import krist.car.search_trips.adapters.TripsAdapter;
import krist.car.models.TripsModel;
import krist.car.search_trips.adapters.SearchSuggestionAdapter;
import krist.car.search_trips.adapters.SuggestionSelectionListener;
import krist.car.search_trips.adapters.TripsAdapterKt;
import krist.car.utils.Animations;
import krist.car.utils.Helpers;

public class SearchFragment extends Fragment implements SuggestionSelectionListener, OnTripClickedListener {


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TripsAdapterKt adapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private List<TripsModel> tripsModelList;
    private TripsAdapter tripsAdapter;
    private DatabaseReference databaseReferenceQuery;
    private List<TripsModel> dataSet;
    private LinearLayout searchContainer;
    private EditText searchEditText;
    private RecyclerView searchRecycler;
    private SearchTripsViewModel viewModel;
    private SearchSuggestionAdapter searchSuggestionAdapter;
    private TextView showAllTrips;
    androidx.appcompat.widget.Toolbar toolbar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.kerko_final, container, false);
    }


    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchContainer = view.findViewById(R.id.search_container);
        searchEditText = view.findViewById(R.id.search_edit_text);
        recyclerView = view.findViewById(R.id.list_trips);
        searchRecycler = view.findViewById(R.id.search_view_recycler_view);
        showAllTrips = view.findViewById(R.id.show_all_trips);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        tripsModelList = new ArrayList<>();
        adapter = new TripsAdapterKt(this);
        recyclerView.setAdapter(adapter);


        toolbar = view.findViewById(R.id.toolbar_kerko);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.search_icon);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupSearchViewSuggestionAdapter();
        setupListeners();
        intViewModel();
        getAllTrips();
        getSearchSuggestion();

    }

    private void intViewModel() {
        viewModel = new ViewModelProvider(this).get(SearchTripsViewModel.class);
        viewModel.getCitesSuggestions(getContext());
    }

    private void getAllTrips() {
        viewModel.getTrips();
        viewModel.getAllTrips().observe(getViewLifecycleOwner(), trips -> {
            if(trips.size() > 0) {
                adapter.setTrips(trips);
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupListeners() {
        searchContainer.setOnClickListener(null);
        showAllTrips.setOnClickListener(view -> {
            getAllTrips();
            hideSearchView();
        });

        searchEditText.setOnTouchListener((v, event) -> {
            final int DRAWABLE_LEFT = 0;
            final int DRAWABLE_RIGHT = 2;

            if(event.getAction() == MotionEvent.ACTION_UP) {
                if(event.getRawX() <= (searchEditText.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width() + 12)) {
                    hideSearchView();
                    return true;
                }
                if(event.getRawX() >= searchEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()) {
                    onSuggestionClicked(searchEditText.getText().toString());
                }
            }
            return false;
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.getSuggestion(s.toString());
            }
        });
    }

    private void getSearchSuggestion() {
        viewModel.getSuggestionBaseOnUserInput().observe(getViewLifecycleOwner(), suggestionList -> {
            searchSuggestionAdapter.setSuggestionList(suggestionList);
        });
    }

    private void setupSearchViewSuggestionAdapter() {
        searchRecycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        searchSuggestionAdapter = new SearchSuggestionAdapter(this);
        searchRecycler.setAdapter(searchSuggestionAdapter);
    }

    private void showSearchView() {
        searchContainer.setVisibility(View.VISIBLE);
        Animations.animateViewHHeightTopToBottom(searchContainer, Helpers.getScreenHeight(getActivity()));
        searchEditText.requestFocus();
    }

    private void hideSearchView() {
        Animations.animateViewHHeightBottomToTop(searchContainer, 0);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
       /* inflater.inflate(R.menu.search_toolbar, menu);

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



                *//*
                 if (newText.length() > 0) {

                     newText = newText.substring(0,1).toUpperCase() + newText.substring(1);

                }
                    *//*



                    
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
*/

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                showSearchView();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSuggestionClicked(@NotNull String query) {
        viewModel.getTripsForQuery(query);
        viewModel.getQueryTrips().observe(getViewLifecycleOwner(), queryTrips -> {
            if(queryTrips.size() != 0) {
                adapter.setTrips(queryTrips);
            }else {
                Helpers.showToastMessage(getContext(), "Nuk u gjet asnje udhetim me kete te dhena");
            }

        });
        hideSearchView();
    }

    @Override
    public void onTripsClicked(@NotNull TripsModel model) {
        String shoferId = model.getIdShofer();
        String tripId = model.getTripID();

        Bundle bundle = new Bundle();
        bundle.putString("shoferID", shoferId);
        bundle.putString("tripID", tripId);

        Helpers.goToActivityAttachBundle(getContext(), PopUpActivity.class, "bS", bundle);
    }
}
