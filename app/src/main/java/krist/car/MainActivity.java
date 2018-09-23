package krist.car;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.function.ToLongBiFunction;

public class MainActivity extends AppCompatActivity{




    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;


    private KerkoFragment kerkoFragment;
    private PostFragment postFragment;
    private HistoryFragment historyFragment;
    private UserInfo userInfoFragment;

    DatabaseReference databaseReference;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        //----- nav menu 3 items


        kerkoFragment = new KerkoFragment();
        postFragment = new PostFragment();
        historyFragment = new HistoryFragment();
        userInfoFragment = new UserInfo();

        //Database
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid();

        databaseReference = firebaseDatabase.getReference("users").child(uid);


        mMainFrame = findViewById(R.id.main_frame);
        mMainNav = findViewById(R.id.main_nav);

        setFragment(kerkoFragment);


        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.nav_home:
                        setFragment(kerkoFragment);
                        return true;


                    case R.id.nav_posto:

                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                if(dataSnapshot.hasChild("targaMak")){

                                    Toast.makeText(MainActivity.this,"Beht postim direkt pa kaluar te regj i deatjeve" , Toast.LENGTH_LONG).show();

                                    setFragment(postFragment);


                                }else{
                                    Intent intent = new Intent(MainActivity.this, DetajeActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(MainActivity.this, "duhet te regjistorsh makinen ", Toast.LENGTH_LONG).show();
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        //bej fragment tj
                       /* setFragment(postFragment);
                        return true;*/

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




























