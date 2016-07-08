package de.my.playground;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import de.my.playground.adapter.FragmentInfo;
import de.my.playground.adapter.NavigationDrawerAdapter;
import de.my.playground.fragments.BroadcastFragment;
import de.my.playground.fragments.TabFragment;

public class MainActivity extends AppCompatActivity implements NavigationDrawerAdapter.NavigationDrawerListener {

    DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(de.my.playground.R.layout.activity_main);

        initializeToolbarAndDrawer();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, new TabFragment());
        ft.commit();
    }

    private void initializeToolbarAndDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.navigationdrawer_open, R.string.navigationdrawer_closed) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // Code here will execute once mDrawer is opened
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once mDrawer is closed
            }
        };
        assert mDrawer != null;
        mDrawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        RecyclerView recyclerview = (RecyclerView) findViewById(R.id.drawer_sliding);
        assert recyclerview != null;
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerview.setAdapter(new NavigationDrawerAdapter(this, createFragmentList(), this));
    }

    private ArrayList<FragmentInfo> createFragmentList() {
        ArrayList<FragmentInfo> infos = new ArrayList<>();
        infos.add(new FragmentInfo("Tabs", null, new TabFragment()));
        infos.add(new FragmentInfo("Broadcast", null, new BroadcastFragment()));
        return infos;
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == de.my.playground.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNavigationDrawerItemSelected(Fragment f) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, f);
        ft.commit();
        mDrawer.closeDrawers();
    }
}
