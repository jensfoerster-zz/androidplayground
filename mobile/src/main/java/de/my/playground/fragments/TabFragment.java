package de.my.playground.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.my.playground.R;
import de.my.playground.adapter.MyFragmentPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends Fragment {


    public TabFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_tab, container, false);

        MyFragmentPagerAdapter mSectionsPagerAdapter = new MyFragmentPagerAdapter(getActivity().getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager = (ViewPager)(v.findViewById(R.id.tabContainer));
        assert mViewPager != null;
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.tabs_main);
        assert tabLayout != null;
        tabLayout.setupWithViewPager(mViewPager);

        return v;
    }

}
