package de.my.playground.keystore;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by dep01181 on 12/15/2015.
 */
public class AliasAdapter extends ArrayAdapter<String> {
    public AliasAdapter(Context context) {
        // We want users to choose a key, so use the appropriate layout.
        super(context, android.R.layout.simple_list_item_single_choice);
    }

    /**
     * This clears out all previous aliases and replaces it with the
     * current entries.
     */
    public void setAliases(List<String> items) {
        clear();
        addAll(items);
        notifyDataSetChanged();
    }
}
