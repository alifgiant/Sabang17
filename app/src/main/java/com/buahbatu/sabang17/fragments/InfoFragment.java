package com.buahbatu.sabang17.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.buahbatu.sabang17.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link InfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class InfoFragment extends Fragment {

    public static InfoFragment newInstance() {
        return new InfoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // find Views
        View root = inflater.inflate(R.layout.fragment_info, container, false);
        TabLayout tabLayout= (TabLayout)root.findViewById(R.id.info_tabs);
        ViewPager viewPager = (ViewPager) root.findViewById(R.id.info_nested_pager);

        // setup pager
        viewPager.setAdapter(new InfoPagerAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        return root;
    }

    private class InfoPagerAdapter extends FragmentPagerAdapter{
        List<Fragment> infoFragmentList;

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0: return "Kegiatan";
                case 1: return "Kaderisasi";
                case 2: return "Berita";
                default: return "";
            }
        }

        InfoPagerAdapter(FragmentManager fm) {
            super(fm);
            infoFragmentList = new ArrayList<>();
            infoFragmentList.add(InfoChildFragment.newInstance("event"));
            infoFragmentList.add(InfoChildFragment.newInstance("cadre"));
            infoFragmentList.add(InfoChildFragment.newInstance("opinion"));
        }

        @Override
        public Fragment getItem(int position) {
            return infoFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return infoFragmentList.size();
        }
    }
}
