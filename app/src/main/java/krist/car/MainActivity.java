package krist.car;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import krist.car.CarRegister.DetajeActivity;
import krist.car.PostTrips.PostFragment;
import krist.car.ProfileInfo.UserInfo;

public class MainActivity extends AppCompatActivity{
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    private SearchFragment searchFragment;
    private PostFragment postFragment;
    private HistoryFragment historyFragment;
    private UserInfo userInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //----- nav menu 4 items
        searchFragment = new SearchFragment();
        postFragment = new PostFragment();
        historyFragment = new HistoryFragment();
        userInfoFragment = new UserInfo();

        mMainFrame = findViewById(R.id.main_frame);
        mMainNav = findViewById(R.id.main_nav);

        setFragment(searchFragment);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        setFragment(searchFragment);
                        return true;
                    case R.id.nav_posto:
                        setFragment(postFragment);
                    return true;
                    case R.id.nav_profili:
                        setFragment(historyFragment);
                        return true;
                    case R.id.nav_profili2:
                        setFragment(userInfoFragment);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }
}




























