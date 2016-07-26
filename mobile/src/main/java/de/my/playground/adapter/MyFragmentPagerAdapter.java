package de.my.playground.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.Pair;

import java.util.ArrayList;

import de.my.playground.fragments.DialogExampleFragment;
import de.my.playground.fragments.ExpandableListFragment;
import de.my.playground.fragments.Scrollable.TwoWayScrollFragment;
import de.my.playground.fragments.keystore.KeyStoreUsageFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Pair<String, Fragment>> mFragments;

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);

        mFragments = new ArrayList<>();
        mFragments.add(new Pair<>("Dialog", new DialogExampleFragment()));
        mFragments.add(new Pair<>("KeyStore", new KeyStoreUsageFragment()));
        mFragments.add(new Pair<>("List", new ExpandableListFragment()));
        mFragments.add(new Pair<>("Scrollable", new TwoWayScrollFragment()));
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position).second;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragments.get(position).first;
    }
}
