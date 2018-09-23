package krist.car;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import krist.car.FragmentHistoryDriver.OnFragmentInteractionListener;


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
        TabLayout tabLayout = view.findViewById(R.id.result_tabs);
        tabLayout.setupWithViewPager(viewPager);

        return view;

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
