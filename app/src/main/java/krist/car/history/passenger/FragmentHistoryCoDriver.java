package krist.car.history.passenger;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import krist.car.R;
import krist.car.history.adapter.CoDriverAdapter;
import krist.car.history.driver.FragmentHistoryDriver;
import krist.car.models.TripsModel;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHistoryCoDriver extends Fragment implements DriverListener {

    private FragmentHistoryDriver.OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView ;
    private RecyclerView.LayoutManager layoutManager;
    private UserBookTripsAdapter adapter;

    private PassengerViewModel viewModel;


    public FragmentHistoryCoDriver() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.co_driver_recycle, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.co_driver_recycle_list);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new UserBookTripsAdapter(this);
        recyclerView.setAdapter(adapter);

        initViewModel();
        getAllBookedTrips();

    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(PassengerViewModel.class);
    }

    private void getAllBookedTrips() {
        viewModel.getUserBookedTrips();
        viewModel.bookedTrips().observe(getViewLifecycleOwner(), bookedTrips -> {
            if(!bookedTrips.isEmpty()) {
                adapter.setTrips(bookedTrips);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void getDriverId(@NotNull UserAndCarRefLink data) {
        viewModel.getUserInfo(data.getUserId(), data.getCarId());
        viewModel.userGeneralInfo().observe(getViewLifecycleOwner(), userGeneraInfo -> {
            DriverGeneralInfoBottomsheet dialog = new DriverGeneralInfoBottomsheet(userGeneraInfo);
            dialog.show(getParentFragmentManager(), "2");
        });
    }
}
