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

import org.jetbrains.annotations.NotNull;

import krist.car.R;
import krist.car.history.driver.FragmentHistoryDriver;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHistoryCoDriver extends Fragment implements DriverListener, RatingStartListener {

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
            DriverGeneralInfoBottomsheet dialog = new DriverGeneralInfoBottomsheet(userGeneraInfo, this);
            dialog.show(getParentFragmentManager(), "2");
        });
    }

    @Override
    public void onRatingStartClicked(float rating, String driverId) {
        viewModel.rateDriver(Math.round(rating), driverId);
        viewModel.isDriverRatedSuccesfully().observe(getViewLifecycleOwner(), isSuccess -> {
            Log.d("lol", isSuccess.toString());
        });
    }
}
