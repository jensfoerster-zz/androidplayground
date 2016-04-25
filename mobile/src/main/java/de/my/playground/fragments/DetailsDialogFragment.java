package de.my.playground.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import de.my.playground.R;


public class DetailsDialogFragment extends DialogFragment {

    public static final int REQUEST_CODE = 0x00001234;

    public static final String sIntent = "de.my.playground.action.closed";
    public static final String sIntentExtra = "de.my.playground.extra.result";

    private static final String TAG = "DetailsDialogF";
    private static final String mBundleKey = "pew";

    private OnClickListener mListener;
    private boolean mCreatedAsPopup = false;
    private String mText;

    public interface OnClickListener {
        void onDialogOk(String displayValue);
        void onDialogDismiss(String displayValue);
    }

    private void setListener(OnClickListener lstnr){
        if(mListener != null) throw new InternalError("only one listener is supported.");
        mListener = lstnr;
    }

    /**
     * You can either use the activity result ....
     **/
    public static DetailsDialogFragment newInstance(String text, Fragment callbackFragment, int requestCode) {
        DetailsDialogFragment dialog = new DetailsDialogFragment();
        Bundle b = new Bundle();
        b.putString(mBundleKey, text);
        dialog.setArguments(b);
        dialog.setTargetFragment(callbackFragment, requestCode);
        return dialog;
    }

    /**
     * .... or a listener to get the result from our dialog.
     **/
    public static DetailsDialogFragment newInstance(String text, OnClickListener lstnr) {
        DetailsDialogFragment dialog = new DetailsDialogFragment();
        Bundle b = new Bundle();
        b.putString(mBundleKey, text);
        dialog.setArguments(b);
        dialog.setListener(lstnr);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //retrieve the argument
        mText = getArguments().getString(mBundleKey);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.dialog_layout, container, false);

        fillView(view);
        addToolbar(view);

        //make changes to your view depending on the creation as a popup/fullscreen dialog
        if (mCreatedAsPopup) {
            Log.d(TAG, "created as dialog");
        } else {
            Log.d(TAG, "created as fullscreen activity");
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

        ((TextView)view.findViewById(R.id.dialog_text)).setText(mText);

        // We have to implement the buttons ourselves (no AlertDialogHelper) because the view
        // can be instantiated from onCreateView only
        (view.findViewById(R.id.dialog_buttonDismiss)).setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.onDialogDismiss(((Button) v).getText().toString());
                        } else {
                            Intent i = createIntent(((Button) v).getText().toString());
                            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
                        }
                        dismiss();
                    }
                });

        (view.findViewById(R.id.dialog_buttonOk)).setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.onDialogOk(((Button) v).getText().toString());
                        } else {
                            Intent i = createIntent(((Button) v).getText().toString());
                            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
                        }
                        dismiss();
                    }
                });
    }

    private void addToolbar(View view) {
        final Toolbar toolbar = (Toolbar) view.findViewById(R.id.dialog_toolbar);
        toolbar.setTitle(R.string.dialog_toolbar_title);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_close_clear_cancel);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void hideToolbar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.dialog_toolbar);
        toolbar.setVisibility(View.GONE);
    }

    private Intent createIntent(String data) {
        Intent i = new Intent();
        i.setAction(sIntent);
        i.putExtra(sIntentExtra, data);
        return i;
    }
}