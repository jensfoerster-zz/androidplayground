package de.my.playground;

import android.os.Bundle;
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

import de.my.playground.NavigationDrawerRecyclerViewAdapter.ExpandableListItem;
import de.my.playground.NavigationDrawerRecyclerViewAdapter.ExpandableListItemType;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(de.my.playground.R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerview = (RecyclerView) findViewById(R.id.drawer_sliding);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerview.setAdapter(new NavigationDrawerRecyclerViewAdapter(generateFakeItems()));

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

        data.add(new ExpandableListItem(ExpandableListItemType.HEADER, "Max Mustermann"));

        ExpandableListItem header1 = new ExpandableListItem(ExpandableListItemType.HEADING, "Header 1");
        header1.invisibleChildren = new ArrayList<>();
        header1.invisibleChildren.add(new ExpandableListItem(ExpandableListItemType.CHILD, "Apple"));
        header1.invisibleChildren.add(new ExpandableListItem(ExpandableListItemType.CHILD, "Orange"));
        header1.invisibleChildren.add(new ExpandableListItem(ExpandableListItemType.CHILD, "Banana"));
        header1.invisibleChildren.add(new ExpandableListItem(ExpandableListItemType.CHILD, "Audi"));
        header1.invisibleChildren.add(new ExpandableListItem(ExpandableListItemType.CHILD, "Apple"));
        header1.invisibleChildren.add(new ExpandableListItem(ExpandableListItemType.CHILD, "Orange"));
        header1.invisibleChildren.add(new ExpandableListItem(ExpandableListItemType.CHILD, "Banana"));
        header1.invisibleChildren.add(new ExpandableListItem(ExpandableListItemType.CHILD, "Aston Martin"));
        header1.invisibleChildren.add(new ExpandableListItem(ExpandableListItemType.CHILD, "BMW"));
        header1.invisibleChildren.add(new ExpandableListItem(ExpandableListItemType.CHILD, "Apple"));
        header1.invisibleChildren.add(new ExpandableListItem(ExpandableListItemType.CHILD, "Orange"));
        header1.invisibleChildren.add(new ExpandableListItem(ExpandableListItemType.CHILD, "Banana"));
        header1.invisibleChildren.add(new ExpandableListItem(ExpandableListItemType.CHILD, "Cadillac"));
        data.add(header1);

        data.add(new ExpandableListItem(ExpandableListItemType.HEADING, "Header 2"));
        data.add(new ExpandableListItem(ExpandableListItemType.CHILD, "Audi"));
        data.add(new ExpandableListItem(ExpandableListItemType.CHILD, "Apple"));
        data.add(new ExpandableListItem(ExpandableListItemType.CHILD, "Orange"));
        data.add(new ExpandableListItem(ExpandableListItemType.CHILD, "Banana"));
        data.add(new ExpandableListItem(ExpandableListItemType.CHILD, "Audi"));
        data.add(new ExpandableListItem(ExpandableListItemType.CHILD, "Apple"));
        data.add(new ExpandableListItem(ExpandableListItemType.CHILD, "Orange"));
        data.add(new ExpandableListItem(ExpandableListItemType.CHILD, "Banana"));
        data.add(new ExpandableListItem(ExpandableListItemType.CHILD, "Aston Martin"));
        data.add(new ExpandableListItem(ExpandableListItemType.CHILD, "BMW"));
        data.add(new ExpandableListItem(ExpandableListItemType.CHILD, "Apple"));
        data.add(new ExpandableListItem(ExpandableListItemType.CHILD, "Orange"));
        data.add(new ExpandableListItem(ExpandableListItemType.CHILD, "Banana"));
        data.add(new ExpandableListItem(ExpandableListItemType.CHILD, "Cadillac"));
        data.add(new ExpandableListItem(ExpandableListItemType.CHILD, "Aston Martin"));
        data.add(new ExpandableListItem(ExpandableListItemType.CHILD, "BMW"));
        data.add(new ExpandableListItem(ExpandableListItemType.CHILD, "Apple"));
        data.add(new ExpandableListItem(ExpandableListItemType.CHILD, "Orange"));
        data.add(new ExpandableListItem(ExpandableListItemType.CHILD, "Banana"));
        data.add(new ExpandableListItem(ExpandableListItemType.CHILD, "Cadillac"));
        data.add(new ExpandableListItem(ExpandableListItemType.MISC, "Settings"));
        data.add(new ExpandableListItem(ExpandableListItemType.MISC, "About"));
        return data;
    }

}
