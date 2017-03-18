package com.buahbatu.sabang17;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.buahbatu.sabang17.fragments.BusinessFragment;
import com.buahbatu.sabang17.fragments.CreditFragment;
import com.buahbatu.sabang17.fragments.CriticFragment;
import com.buahbatu.sabang17.fragments.InfoFragment;
import com.buahbatu.sabang17.fragments.TimeFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private BottomNavigationView bottomNavigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_schedule:
                    mViewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_business:
                    mViewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_critics:
                    mViewPager.setCurrentItem(3);
                    return true;
                case R.id.navigation_credits:
                    mViewPager.setCurrentItem(4);
                    return true;
            }
            return false;
        }

    };

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position){
                case 0:
                    bottomNavigationView.setSelectedItemId(R.id.navigation_home);
                    break;
                case 1:
                    bottomNavigationView.setSelectedItemId(R.id.navigation_schedule);
                    break;
                case 2:
                    bottomNavigationView.setSelectedItemId(R.id.navigation_business);
                    break;
                case 3:
                    bottomNavigationView.setSelectedItemId(R.id.navigation_critics);
                    break;
                case 4:
                    bottomNavigationView.setSelectedItemId(R.id.navigation_credits);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void setupViewPager(ViewPager viewPager)
    {
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(mOnPageChangeListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find views
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        // setup pager
        setupViewPager(mViewPager);

        // setup bottom navigation
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private class MainViewPagerAdapter extends FragmentPagerAdapter{
        List<Fragment> fragmentList;
        MainViewPagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentList = new ArrayList<>();
            fragmentList.add(InfoFragment.newInstance());
            fragmentList.add(TimeFragment.newInstance());
            fragmentList.add(BusinessFragment.newInstance());
            fragmentList.add(CriticFragment.newInstance());
            fragmentList.add(CreditFragment.newInstance());
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

}
