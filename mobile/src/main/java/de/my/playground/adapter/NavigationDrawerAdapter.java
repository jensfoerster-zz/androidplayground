package de.my.playground.adapter;

/**
 * Created by dep01181 on 9/21/2015.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import de.my.playground.R;

/**
 * Created by anandbose on 09/06/15.
 * https://github.com/anandbose/ExpandableListViewDemo/
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface NavigationDrawerListener {
        void onNavigationDrawerItemSelected(Fragment f);
    }

    private final Context mContext;
    private final ArrayList<FragmentInfo> mFragments;
    private final NavigationDrawerListener mListener;

    public NavigationDrawerAdapter(Context ctx, ArrayList<FragmentInfo> fragments, NavigationDrawerListener listener) {
        mContext = ctx;
        mFragments = fragments;
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view;
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (type == 0) {
            view = inflater.inflate(R.layout.list_header, parent, false);
            viewHolder = new HeaderViewHolder(view);
        } else {
            view = inflater.inflate(R.layout.list_entry_fragment, parent, false);
            viewHolder = new FragmentViewHolder(view);
        }
        return viewHolder;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder hvh = (HeaderViewHolder) holder;
            hvh.name.setText(mContext.getString(R.string.example_name));
            hvh.add_line1.setText(mContext.getString(R.string.example_line1));
            hvh.add_line2.setText(mContext.getString(R.string.example_line2));
        } else {
            FragmentViewHolder vh = (FragmentViewHolder) holder;
            vh.onBindView(mFragments.get(position-1));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mFragments.size() + 1;
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView add_line1;
        public TextView add_line2;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.navigationdrawer_name);
            add_line1 = (TextView) itemView.findViewById(R.id.navigationdrawer_add_line1);
            add_line2 = (TextView) itemView.findViewById(R.id.navigationdrawer_add_line2);
        }
    }

    private class FragmentViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView textView;
        private Fragment fragment;

        public FragmentViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.frag_image);
            textView = (TextView) itemView.findViewById(R.id.frag_name);
        }

        public void onBindView(final FragmentInfo frag) {
            textView.setText(frag.name);
            imageView.setImageDrawable(frag.image);
            fragment = frag.frag;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onNavigationDrawerItemSelected(fragment);
                }
            });
        }
    }

}