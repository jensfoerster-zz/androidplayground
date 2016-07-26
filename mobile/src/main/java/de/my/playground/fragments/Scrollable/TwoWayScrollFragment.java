package de.my.playground.fragments.Scrollable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.my.playground.R;

public class TwoWayScrollFragment extends Fragment {

    private FixedGridLayoutManager mLayoutManager;
    private RecyclerView mRecycler;
    private RecyclerView.ItemDecoration mItemDecoration;
    private TwoWayScrollAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_scrollable, container, false);

        mRecycler = (RecyclerView) v.findViewById(R.id.scroll_rec);

        mLayoutManager = new FixedGridLayoutManager();
        mLayoutManager.setTotalColumnCount(10);
        mRecycler.setLayoutManager(mLayoutManager);


        mItemDecoration = new InsetDecoration(getActivity());
        mRecycler.addItemDecoration(mItemDecoration);


        mAdapter = new TwoWayScrollAdapter();
        mAdapter.setData(
                "1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24", "25",
                "1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24", "25",
                "1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24", "25",
                "1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24", "25",
                "1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24", "25");
        mRecycler.setAdapter(mAdapter);

        return v;
    }
}
