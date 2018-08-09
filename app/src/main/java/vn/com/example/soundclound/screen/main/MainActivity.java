package vn.com.example.soundclound.screen.main;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import vn.com.example.soundclound.R;

import vn.com.example.soundclound.screen.base.activity.BaseActivity;
import vn.com.example.soundclound.screen.base.presenter.BasePresenter;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private String[] mPermision = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    public static final int PERMISSIONS_REQUEST_CODE = 100;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initViews();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
        checkPermission();
    }

    private void initViews() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToolbar = findViewById(R.id.toolbar);
        mNavigationView = findViewById(R.id.nav_view);
        mViewPager = findViewById(R.id.view_pager);
        mTabLayout = findViewById(R.id.tab_layout);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initViewPager() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentAdapter adapter = new FragmentAdapter(this, manager);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setTabsFromPagerAdapter(adapter);
    }

    public void checkPermission() {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermit() == PackageManager.PERMISSION_GRANTED) {
                initViewPager();
            } else {
                requestPermissions(mPermision, PERMISSIONS_REQUEST_CODE);
            }
        }
    }

    public int checkPermit() {
        int checkPoint = PackageManager.PERMISSION_GRANTED;
        for (String check : mPermision) {
            checkPoint += ContextCompat.checkSelfPermission(this, check);
        }
        return checkPoint;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initViewPager();
                } else {
                    finish();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }
}
