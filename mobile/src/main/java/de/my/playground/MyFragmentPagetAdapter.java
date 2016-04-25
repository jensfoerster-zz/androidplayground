package de.my.playground;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import de.my.playground.keystore.KeyStoreUsageFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class MyFragmentPagetAdapter extends FragmentPagerAdapter {

    public MyFragmentPagetAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new BroadcastFragment();
            case 1:
                return new DialogExampleFragment();
            case 2:
                return new KeyStoreUsageFragment();
            default:
                return PlaceholderFragment.newInstance(position + 1);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Broadcast";
            case 1:
                return "Dialog";
            case 2:
                return "KeyStore";
        }
        return null;
    }
}
