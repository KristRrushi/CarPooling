package krist.car.history.driver;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import krist.car.R;
import krist.car.history.driver.adapter.HistoryDriverAdapter;
import krist.car.models.PassToTripsModel;
import krist.car.models.TripsModel;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentHistoryDriver.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class FragmentHistoryDriver extends Fragment  implements OnPostTripClickedListener {

    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView ;
    private RecyclerView.LayoutManager layoutManager;
    private HistoryDriverAdapter adapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private List<TripsModel> tripsModelList;
    FirebaseUser idauth;
    private DriverHistoryViewModel viewModel;


    public FragmentHistoryDriver() {
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
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        idauth = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView = view.findViewById(R.id.co_driver_recycle_list);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        tripsModelList = new ArrayList<>();
        adapter = new HistoryDriverAdapter(this);
        recyclerView.setAdapter(adapter);


        RecyclerView.ItemDecoration divider = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divider);


       /* ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.removeItem(viewHolder.getAdapterPosition());
            }


            @Override
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                final View foregroundView = ((HistoryDriverAdapter.ViewHolder) viewHolder).viewForeground;
                getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
            }



            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                final View foregroundView = ((HistoryDriverAdapter.ViewHolder) viewHolder).viewForeground;
                getDefaultUIUtil().clearView(foregroundView);
            }


            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                final View foregroundView = ((HistoryDriverAdapter.ViewHolder) viewHolder).viewForeground;

                getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
            }



        });*/



        //helper.attachToRecyclerView(recyclerView);


        initViewModel();

    }


    @Override
    public void onStart() {
        super.onStart();
        getAllPostedTrips();
    }


    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(DriverHistoryViewModel.class);
    }

    private void getAllPostedTrips() {
        viewModel.getTrips();
        viewModel.getPostedTrips().observe(getViewLifecycleOwner(), postedTrips -> {
            adapter.setTrips(postedTrips);
        });
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onTripClicked(@NotNull ArrayList<PassToTripsModel> passengers) {
        if(!passengers.isEmpty()) {
            PassengerListBottomSheetDialog dialog = new PassengerListBottomSheetDialog(passengers);
            dialog.show(getParentFragmentManager(), "1");
        }
    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
