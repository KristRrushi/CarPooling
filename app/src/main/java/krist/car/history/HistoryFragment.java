package krist.car.history;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import krist.car.history.driver.FragmentHistoryDriver;
import krist.car.history.driver.FragmentHistoryDriver.OnFragmentInteractionListener;
import krist.car.R;
import krist.car.history.adapter.HistoryPagerAdapter;
import krist.car.history.passenger.FragmentHistoryCoDriver;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HistoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class HistoryFragment extends Fragment implements OnFragmentInteractionListener {

    private OnFragmentInteractionListener mListener;
    HistoryPagerAdapter historyPagerAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;


    public HistoryFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history,container,false);
        viewPager = view.findViewById(R.id.history_view_pager);
        historyPagerAdapter = new HistoryPagerAdapter(getChildFragmentManager());

        historyPagerAdapter.addFragment(new FragmentHistoryDriver(),"Shofer");
        historyPagerAdapter.addFragment(new FragmentHistoryCoDriver(),"Pasagjer");

        viewPager.setAdapter(historyPagerAdapter);
        tabLayout = view.findViewById(R.id.result_tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        return view;
    }


    private void setupTabIcons(){
        tabLayout.getTabAt(0).setIcon(R.drawable.steering_wheel);
        tabLayout.getTabAt(1).setIcon(R.drawable.car_seat_with_seatbelt_white);
    }

    @Override
    public void onFragmentInteraction(Uri uri) { }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
