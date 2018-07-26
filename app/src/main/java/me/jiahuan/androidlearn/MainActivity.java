package me.jiahuan.androidlearn;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import java.util.List;

import butterknife.BindView;
import me.jiahuan.androidlearn.base.BaseActivity;
import me.jiahuan.androidlearn.function.FunctionFragment;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.id_layout_main_activity_bottom_navigation_view)
    BottomNavigationView mBottomNavigationView;

    private FunctionFragment mFunctionFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity(R.layout.layout_main_activity);
        initialize();
    }

    private void initialize() {
        mFunctionFragment = new FunctionFragment();
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return true;
            }
        });
        addOrShowFragment(R.id.id_menu_main_activity_bottom_navigation_view_function);
    }


    private void hideAllFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for (Fragment fragment : fragments) {
            fragmentTransaction.hide(fragment);
        }
        fragmentTransaction.commit();
    }

    private void addOrShowFragment(int itemId) {
        hideAllFragments();
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (itemId == R.id.id_menu_main_activity_bottom_navigation_view_function) {
            if (fragments.contains(mFunctionFragment)) {
                fragmentTransaction.show(mFunctionFragment);
            } else {
                fragmentTransaction.add(R.id.id_layout_main_activity_fragment_container_view_group, mFunctionFragment, FunctionFragment.TAG);
            }
            getSupportActionBar().setTitle(R.string.string_menu_main_activity_bottom_navigation_view_function);
        }
        fragmentTransaction.commit();
    }
}

