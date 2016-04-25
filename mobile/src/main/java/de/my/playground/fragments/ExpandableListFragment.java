package de.my.playground.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import de.my.playground.ExpandableListItem;
import de.my.playground.NavigationDrawerRecyclerViewAdapter;
import de.my.playground.R;

/**
 * Created by dep01181 on 4/25/2016.
 */
public class ExpandableListFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_expandable_list, container, false);
        initializeContent(v);
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void initializeContent(View v) {
        RecyclerView recyclerview = (RecyclerView) v.findViewById(R.id.exp_list_recycler);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerview.setAdapter(new NavigationDrawerRecyclerViewAdapter(generateFakeItems()));
    }

    private List<ExpandableListItem> generateFakeItems() {
        List<ExpandableListItem> data = new ArrayList<>();

        //Big header
        //data.add(new ExpandableListItem<>("Max Mustermann-max@mustermail.com-Muster Company", ExpandableListItem.Type.HEADER));

        //Expandable Heading
        ExpandableListItem fruits = new ExpandableListItem<>("Fruit", ExpandableListItem.Type.HEADING);
        //Children
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
