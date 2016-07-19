package de.my.playground;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import de.my.playground.fragments.BroadcastFragment;
import de.my.playground.fragments.PlaceholderFragment;
import de.my.playground.fragments.SoundFragment;
import de.my.playground.fragments.TabFragment;

public class MainActivity extends AppCompatActivity {

    DrawerLayout mDrawer;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(de.my.playground.R.layout.activity_main);

        initializeToolbarAndDrawer();

        if (savedInstanceState == null)
            nvDrawer.getMenu().performIdentifierAction(R.id.nav_frag_tabs, 0);
    }

    private void initializeToolbarAndDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawer(nvDrawer);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = setupDrawerToggle(mDrawer, toolbar);
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    private void setupDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    public MenuItem mPrevMenuItem = null;

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem, mPrevMenuItem);
                        mPrevMenuItem = menuItem;
                        return true;
                    }
                });


        Menu menu = navigationView.getMenu();
//        menu.add(1, R.id.frag_tabs, Menu.NONE, "Tabs");
//        menu.add(1, R.id.frag_bc, Menu.NONE, "Broadcast");
//        menu.add(1, R.id.frag_sound, Menu.NONE, "Sound");

        //already inflated in xml
        //View hv = navigationView.inflateHeaderView(R.layout.nav_header);
        TextView tv = (TextView) navigationView.findViewById(R.id.nav_name);
    }

    private ActionBarDrawerToggle setupDrawerToggle(DrawerLayout drawer, Toolbar toolbar) {
        return new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(de.my.playground.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
            case de.my.playground.R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void selectDrawerItem(MenuItem menuItem, MenuItem previousItem) {

        //only replace the fragment if it is new.
        if (previousItem == null || !previousItem.equals(menuItem)) {
            Fragment fragment = null;
            Class fragmentClass;
            switch (menuItem.getItemId()) {
                case R.id.nav_frag_tabs:
                    fragmentClass = TabFragment.class;
                    break;
                case R.id.nav_frag_bc:
                    fragmentClass = BroadcastFragment.class;
                    break;
                case R.id.nav_frag_sound:
                    fragmentClass = SoundFragment.class;
                    break;
                default:
                    fragmentClass = PlaceholderFragment.class;
            }

            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        }

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

}
