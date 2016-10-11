package de.my.playground.adapter;

/**
 * Created by jensfoerster on 9/21/2015.
 */
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.my.playground.misc.ExpandableListItem;
import de.my.playground.R;

/**
 * Created by anandbose on 09/06/15.
 * https://github.com/anandbose/ExpandableListViewDemo/
 */
public class ExpandableListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ExpandableListItem> displayedData;

    public ExpandableListAdapter(List<ExpandableListItem> data) {
        displayedData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view;
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        switch (ExpandableListItemType.fromInt(type)) {
            case HEADING:
                view = inflater.inflate(R.layout.list_expandable_heading, parent, false);
                viewHolder = new ListHeadingViewHolder(view);
                break;
            case CHILD:
                view = inflater.inflate(R.layout.list_expandable_entry, parent, false);
                viewHolder = new CustomTestViewHolder(view);
                break;
            case MISC:
                view = inflater.inflate(R.layout.list_expandable_misc, parent, false);
                viewHolder = new MiscItemViewHolder(view);
                break;
        }
        return viewHolder;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        ExpandableListItem item = getItemForDisplayPosition(position);

        switch (item.getType()) {
            case HEADING:
                ListHeadingViewHolder itemController = (ListHeadingViewHolder) holder;
                itemController.onBindView(item);
                break;
            case CHILD:
                CustomTestViewHolder tv = (CustomTestViewHolder) holder;
                tv.onBindView(item.getValue().toString());
                break;
            case MISC:
                MiscItemViewHolder misc = (MiscItemViewHolder)holder;
                misc.onBindView(item.getValue().toString());
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        //displayed position != position in list: children can be present
        return getItemForDisplayPosition(position).getType().getValue();
    }

    @Override
    public int getItemCount() {
        int count = 0;
        for (ExpandableListItem item : displayedData){
            count++;
            if (!item.isCollapsed){
                count+=item.children.size();
            }
        }
        return count;
    }

    private ExpandableListItem getItemForDisplayPosition(int displayPosition){
        int count = 0;
        for (ExpandableListItem currentItem : displayedData){
            if(count == displayPosition) {
                return currentItem;
            }
            count++;
            if (!currentItem.isCollapsed){
                for(ExpandableListItem child : (ArrayList<ExpandableListItem>) currentItem.children){
                    if(count == displayPosition) {
                        return child;
                    }
                    count++;
                }
            }
        }
        return new ExpandableListItem<Object>(null, ExpandableListItem.Type.UNKNOWN);
    }

   private class ListHeadingViewHolder extends RecyclerView.ViewHolder {

        public TextView header_title;
        public ImageView btn_expand_toggle;

        public ListHeadingViewHolder(View itemView) {
            super(itemView);
            header_title = (TextView) itemView.findViewById(R.id.navigationdrawer_heading);
            btn_expand_toggle = (ImageView) itemView.findViewById(R.id.btn_expand_toggle);
        }

       public void onBindView(ExpandableListItem item){

           header_title.setText(item.getValue().toString());
           if (item.isCollapsed) {
               btn_expand_toggle.setImageResource(R.drawable.ic_action_next_item);
           } else {
               btn_expand_toggle.setImageResource(R.drawable.ic_action_expand);
           }

           btn_expand_toggle.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   int position = getAdapterPosition();
                   ExpandableListItem viewItem = getItemForDisplayPosition(position);
                   if (!viewItem.isCollapsed) {
                       viewItem.isCollapsed = true;
                       btn_expand_toggle.setImageResource(R.drawable.ic_action_next_item);
                       notifyItemChanged(position);
                       notifyItemRangeRemoved(position + 1, viewItem.children.size());
                   } else {
                       viewItem.isCollapsed = false;
                       btn_expand_toggle.setImageResource(R.drawable.ic_action_expand);
                       notifyItemChanged(position);
                       notifyItemRangeInserted(position + 1, viewItem.children.size());
                   }
               }
           });
           header_title.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   int position = getAdapterPosition();
                   ExpandableListItem viewItem = getItemForDisplayPosition(position);
                   notifyItemChanged(position);
                   Snackbar.make(v, (viewItem.getValue().toString()), Snackbar.LENGTH_SHORT).show();
               }
           });
       }
    }

    private class CustomTestViewHolder extends RecyclerView.ViewHolder{

        public TextView text;

        public CustomTestViewHolder(View itemView){
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.navigationdrawer_entry);
        }

        public void onBindView(String textToDisplay) {
            text.setText(textToDisplay);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ExpandableListItem item = getItemForDisplayPosition(getAdapterPosition());
                    notifyItemChanged(getAdapterPosition());
                    Snackbar.make(v, item.getValue().toString(), Snackbar.LENGTH_SHORT).show();
                }
            });
        }
    }

    public class MiscItemViewHolder extends RecyclerView.ViewHolder {
        public TextView text;

        public MiscItemViewHolder(View view) {
            super(view);
            text = (TextView) view.findViewById(R.id.navigationdrawer_misc);
        }

        public void onBindView(String textToDisplay) {
            text.setText(textToDisplay);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ExpandableListItem item = getItemForDisplayPosition(getAdapterPosition());
                    notifyItemChanged(getAdapterPosition());
                    switch (item.getSubType()){
                        case ABOUT:
                            Snackbar.make(v, item.getValue().toString(), Snackbar.LENGTH_SHORT).show();
                            break;
                        case FRUITS:
                            /* no-op */
                            break;
                    }
                }
            });
        }
    }

    public enum ExpandableListItemType {
        HEADER(0),
        HEADING(1),
        CHILD(2),
        MISC(3),
        UNKNOWN(99);

        private int value;

        ExpandableListItemType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static ExpandableListItemType fromInt(int i) {
            for (ExpandableListItemType t : ExpandableListItemType.values()) {
                if (t.getValue() == i) {
                    return t;
                }
            }
            return UNKNOWN;
        }
    }

}