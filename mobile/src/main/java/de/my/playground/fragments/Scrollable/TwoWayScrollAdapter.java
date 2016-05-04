package de.my.playground.fragments.Scrollable;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.my.playground.R;

public class TwoWayScrollAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String[] mData = new String[0];

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_card, parent, false);

        return new CardItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof CardItemViewHolder) ((CardItemViewHolder)holder).onBindView(mData[position]);
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    public void setData (String... data){
        mData = data;
        notifyDataSetChanged();
    }
}
