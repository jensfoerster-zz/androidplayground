package de.my.playground.fragments.Scrollable;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import de.my.playground.R;


/**
 * Created by jensfoerster on 5/4/2016.
 */
public class CardItemViewHolder extends RecyclerView.ViewHolder {
    public TextView mTextView;

    public CardItemViewHolder(View v) {
        super(v);
        mTextView = (TextView) v.findViewById(R.id.title);
    }

    public void onBindView(String title) {
        mTextView.setText(title);
    }
}
