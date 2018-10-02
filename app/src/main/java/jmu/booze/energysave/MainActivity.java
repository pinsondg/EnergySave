package jmu.booze.energysave;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import jmu.booze.energysave.fragments.DeviceListFragment;
import jmu.booze.energysave.fragments.Home;
import jmu.booze.energysave.fragments.Money;

public class MainActivity extends AppCompatActivity {

    private FrameLayout frameLayout;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setCustomAnimations(R.animator.fade_in, R.animator.fade_out);
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new Home();
                    transaction.replace(R.id.fragment_holder, fragment);
                    transaction.commitNow();
                    return true;
                case R.id.navigation_money:
                    fragment = Money.newInstance();
                    transaction.replace(R.id.fragment_holder, fragment);
                    transaction.commitNow();
                    return true;
                case R.id.navigation_device_list:
                    fragment = new DeviceListFragment();
                    transaction.replace(R.id.fragment_holder, fragment);
                    transaction.commitNow();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide(); // hide the title bar

        BottomNavigationView nav = (BottomNavigationView) findViewById(R.id.navigation);
        nav.setSelectedItemId(R.id.navigation_home);
        FragmentManager fragManager = getSupportFragmentManager();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Set default fragment
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.fragment_holder, Home.newInstance("", "")).commitNow();
    }

}
