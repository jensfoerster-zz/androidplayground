package de.my.playground.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import de.my.playground.R;


public class DetailsDialog extends DialogFragment {

    private static final String TAG = "DetailsDialog";
    private boolean mCreatedAsPopup = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.dialog_layout, container, false);
        fillView(view);

        if(mCreatedAsPopup) {
            Log.d(TAG, "created as dialog");
            hideToolbar(view);
        }
        else {
            Log.d(TAG, "created as fullscreen activity");
            addToolbar(view);
        }
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog d = super.onCreateDialog(savedInstanceState);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mCreatedAsPopup = true;
        return d;
    }

    private void fillView(View view) {

        //the 'bad' thing:
        //  we have to implement the buttons ourselves (no AlertDialogHelper) because the view
        //  can be instantiated from onCreateView only
        (view.findViewById(R.id.dialog_buttonDismiss)).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                //show the snackbar in the tab host to make sure it will be displayed
                Snackbar.make(getActivity().findViewById(R.id.tabContainer), ((Button) v).getText(), Snackbar.LENGTH_SHORT).show();
            }
        });

        (view.findViewById(R.id.dialog_buttonOk)).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                //show the snackbar in the tab host to make sure it will be displayed
                Snackbar.make(getActivity().findViewById(R.id.tabContainer), ((Button) v).getText(), Snackbar.LENGTH_SHORT).show();            }
        });
    }

    private void addToolbar(View view) {
        final Toolbar toolbar = (Toolbar) view.findViewById(R.id.dialog_toolbar);
        toolbar.setTitle(R.string.dialog_toolbar_title);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_close_clear_cancel);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();            }
        });
    }

    private void hideToolbar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.dialog_toolbar);
        toolbar.setVisibility(View.GONE);
    }
}