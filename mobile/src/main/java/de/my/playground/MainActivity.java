package de.my.playground;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(de.my.playground.R.layout.activity_main);

        initializeNavigationDrawerContent();
        initializeToolbarAndDrawer();
        initializeTabs();
    }

    private void initializeNavigationDrawerContent() {
        RecyclerView recyclerview = (RecyclerView) findViewById(R.id.drawer_sliding);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerview.setAdapter(new NavigationDrawerRecyclerViewAdapter(generateFakeItems()));
    }

    private void initializeToolbarAndDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigationdrawer_open, R.string.navigationdrawer_closed) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // Code here will execute once drawer is opened
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }
        };
        drawer.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    private void initializeTabs() {
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        MyFragmentPagetAdapter mSectionsPagerAdapter = new MyFragmentPagetAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager = (ViewPager) findViewById(R.id.tabContainer);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_main);
        tabLayout.setupWithViewPager(mViewPager);
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

    private List<ExpandableListItem> generateFakeItems() {
        List<ExpandableListItem> data = new ArrayList<>();

        data.add(new ExpandableListItem<>("Max Mustermann-max@mustermail.com-Muster Company", ExpandableListItem.Type.HEADER));

        ExpandableListItem fruits = new ExpandableListItem<>("Fruit", ExpandableListItem.Type.HEADING);
        fruits.children.add(new ExpandableListItem<>("Apple", ExpandableListItem.Type.CHILD));
        fruits.children.add(new ExpandableListItem<>("Orange", ExpandableListItem.Type.CHILD));
        fruits.children.add(new ExpandableListItem<>("Banana", ExpandableListItem.Type.CHILD));
        fruits.children.add(new ExpandableListItem<>("Grape", ExpandableListItem.Type.CHILD));
        fruits.children.add(new ExpandableListItem<>("Apple", ExpandableListItem.Type.CHILD));
        data.add(fruits);

        ExpandableListItem cars = new ExpandableListItem<>("Cars", ExpandableListItem.Type.HEADING);
        cars.children.add(new ExpandableListItem<>("Audi", ExpandableListItem.Type.CHILD));
        cars.children.add(new ExpandableListItem<>("BMW", ExpandableListItem.Type.CHILD));
        cars.children.add(new ExpandableListItem<>("VW", ExpandableListItem.Type.CHILD));
        cars.children.add(new ExpandableListItem<>("Aston Martin", ExpandableListItem.Type.CHILD));
        cars.children.add(new ExpandableListItem<>("Ferrari", ExpandableListItem.Type.CHILD));
        data.add(cars);

        data.add(new ExpandableListItem<>("About", ExpandableListItem.Type.MISC, ExpandableListItem.SubType.ABOUT));

        return data;
    }

}
