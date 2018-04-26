package me.jiahuan.androidlearn;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;

import me.jiahuan.androidlearn.base.BaseActivity;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private NavigationView mNavigationView;

    private NavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.id_menu_item_example) {
                Router.build("module_example/example_activity").go(MainActivity.this);
            }
            mDrawerLayout.closeDrawer(Gravity.LEFT, false);
            return true;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate, savedInstanceState = " + savedInstanceState);
        initializeActivityWithToolbar(R.layout.layout_main_activity, false);
        initialize();
    }

    private void initialize() {
        mDrawerLayout = findViewById(R.id.id_layout_main_activity_drawer_layout);
        mNavigationView = findViewById(R.id.id_layout_main_activity_navigation_view);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, getToolbar(), R.string.main_string_main_activity_navigation_drawer_open, R.string.main_string_main_activity_navigation_drawer_close);
        mActionBarDrawerToggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setHomeFragment();
        new Thread() {
            @Override
            public void run() {
                super.run();
//                Looper.prepare();
//                Handler handler = new Handler(Looper.getMainLooper()){
//                    @Override
//                    public void handleMessage(Message msg) {
//                        super.handleMessage(msg);
//
//                    }
//                };
            }
        }.start();
    }

    private void setHomeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = (Fragment) Router.build("module_home/home_fragment").getFragment(this);
        fragmentTransaction.add(R.id.id_layout_main_activity_fragment_container_view_group, fragment, "HOME_FRAGMENT");
        fragmentTransaction.commit();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
    }
}

