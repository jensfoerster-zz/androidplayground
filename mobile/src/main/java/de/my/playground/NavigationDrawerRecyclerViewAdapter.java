package de.my.playground;

/**
 * Created by dep01181 on 9/21/2015.
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

/**
 * Created by anandbose on 09/06/15.
 * https://github.com/anandbose/ExpandableListViewDemo/
 */
public class NavigationDrawerRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ExpandableListItem> displayedData;

    public NavigationDrawerRecyclerViewAdapter(List<ExpandableListItem> data) {
        displayedData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view;
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        switch (ExpandableListItemType.fromInt(type)) {
            case HEADER:
                view = inflater.inflate(R.layout.navigationdrawer_header, parent, false);
                viewHolder = new HeaderViewHolder(view);
                break;
            case HEADING:
                view = inflater.inflate(R.layout.expandablelist_heading, parent, false);
                viewHolder = new ListHeadingViewHolder(view);
                break;
            case CHILD:
                view = inflater.inflate(R.layout.expandablelist_entry, parent, false);
                viewHolder = new RecyclerView.ViewHolder(view){};
                break;
            case MISC:
                view = inflater.inflate(R.layout.expandablelist_misc, parent, false);
                viewHolder = new RecyclerView.ViewHolder(view){};
                break;
        }
        return viewHolder;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ExpandableListItem item = displayedData.get(position);

        switch (item.type) {
            case HEADER:
                HeaderViewHolder hvh = (HeaderViewHolder) holder;
                hvh.name.setText(item.text);
                hvh.hospital.setText(item.text);
                hvh.unit.setText(item.text);
                break;
            case HEADING:
                final ListHeadingViewHolder itemController = (ListHeadingViewHolder) holder;
                itemController.refferalItem = item;
                itemController.header_title.setText(item.text);
                if (item.invisibleChildren == null) {
                    itemController.btn_expand_toggle.setImageResource(R.drawable.ic_action_expand);
                } else {
                    itemController.btn_expand_toggle.setImageResource(R.drawable.ic_action_next_item);
                }
                itemController.btn_expand_toggle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        notifyItemChanged(position);
                        if (item.invisibleChildren == null) {
                            item.invisibleChildren = new ArrayList<ExpandableListItem>();
                            int count = 0;
                            int pos = displayedData.indexOf(itemController.refferalItem);
                            while (displayedData.size() > pos + 1 && displayedData.get(pos + 1).type == ExpandableListItemType.CHILD) {
                                item.invisibleChildren.add(displayedData.remove(pos + 1));
                                count++;
                            }
                            notifyItemRangeRemoved(pos + 1, count);
                            itemController.btn_expand_toggle.setImageResource(R.drawable.ic_action_expand);
                        } else {
                            int pos = displayedData.indexOf(itemController.refferalItem);
                            int index = pos + 1;
                            for (ExpandableListItem i : item.invisibleChildren) {
                                displayedData.add(index, i);
                                index++;
                            }
                            notifyItemRangeInserted(pos + 1, index - pos - 1);
                            itemController.btn_expand_toggle.setImageResource(R.drawable.ic_action_next_item);
                            item.invisibleChildren = null;
                        }
                    }
                });
                itemController.header_title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        notifyItemChanged(position);
                        Snackbar.make(v, ((TextView)v).getText(), Snackbar.LENGTH_SHORT).show();
                    }
                });
                break;
            case CHILD:
                TextView itemTextView = (TextView) holder.itemView.findViewById(R.id.expandablelist_entry);
                itemTextView.setText(displayedData.get(position).text);
                itemTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        notifyItemChanged(position);
                        Snackbar.make(v, ((TextView) v).getText(), Snackbar.LENGTH_SHORT).show();
                    }
                });
                break;
            case MISC:
                TextView itemTextView2 = (TextView) holder.itemView.findViewById(R.id.expandablelist_misc);
                itemTextView2.setText(displayedData.get(position).text);
                itemTextView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        notifyItemChanged(position);
                        Snackbar.make(v, ((TextView) v).getText(), Snackbar.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return displayedData.get(position).type.getValue();
    }

    @Override
    public int getItemCount() {
        return displayedData.size();
    }

   private class ListHeadingViewHolder extends RecyclerView.ViewHolder {

        public TextView header_title;
        public ImageView btn_expand_toggle;
        public ExpandableListItem refferalItem;

        public ListHeadingViewHolder(View itemView) {
            super(itemView);
            header_title = (TextView) itemView.findViewById(R.id.expandablelist_heading);
            btn_expand_toggle = (ImageView) itemView.findViewById(R.id.btn_expand_toggle);
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public TextView hospital;
        public TextView unit;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.navigationdrawer_name);
            hospital = (TextView) itemView.findViewById(R.id.navigationdrawer_hospital);
            unit = (TextView) itemView.findViewById(R.id.navigationdrawer_unit);
        }
    }

    public static class ExpandableListItem {
        public ExpandableListItemType type;
        public String text;
        public List<ExpandableListItem> invisibleChildren;

        public ExpandableListItem(ExpandableListItemType type, String text) {
            this.type = type;
            this.text = text;
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