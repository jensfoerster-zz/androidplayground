package de.my.playground.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import de.my.playground.R;

/**
 * Created by dep01181 on 10/12/2015.
 */
public class DialogExampleFragment extends Fragment implements DetailsDialogFragment.OnClickListener {

    private static final String TAG = "DialogExampleFragment";
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_dialog_example, container, false);

        (mView.findViewById(R.id.dialog_openpopup)).setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openDialogPopup();
                    }
                });

        (mView.findViewById(R.id.dialog_openfull)).setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openDialogFullscreen();
                    }
                });
        return mView;
    }

    private void openDialogPopup() {
        // instantiate the dialog using a listener
        DetailsDialogFragment detailsFragment =
                DetailsDialogFragment.newInstance(
                        getResources().getString(R.string.lorem_long),
                        this);
        detailsFragment.show(getFragmentManager(), "");
    }

    private void openDialogFullscreen() {
        // instantiate the dialog using the "onActivityResult"-interface
        DetailsDialogFragment detailsFragment =
                DetailsDialogFragment.newInstance(
                        getResources().getString(R.string.lorem_long),
                        this,
                        DetailsDialogFragment.REQUEST_CODE);
        getFragmentManager().beginTransaction().add(R.id.drawer_layout, detailsFragment).addToBackStack(null).commit();
    }

    /**
     * listener interface implementation
     */
    @Override
    public void onDialogOk(String displayValue) {
        Log.d(TAG, "onDialogOk " +displayValue);
        Snackbar.make(mView, displayValue, Snackbar.LENGTH_SHORT);
    }
    /**
     * listener interface implementation
     */
    @Override
    public void onDialogDismiss(String displayValue) {
        Log.d(TAG, "onDialogDismiss " +displayValue);
        Snackbar.make(mView, displayValue, Snackbar.LENGTH_SHORT);
    }

    /**
     * listen for the dialog results via onActivityResult
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DetailsDialogFragment.REQUEST_CODE
                && resultCode == Activity.RESULT_OK
                && data != null
                && data.getAction().equals(DetailsDialogFragment.sIntent)
                && data.hasExtra(DetailsDialogFragment.sIntentExtra)) {
            String string = data.getStringExtra(DetailsDialogFragment.sIntentExtra);
            {
                Log.d(TAG, "onActivityResult " +string);
                Snackbar.make(mView, string, Snackbar.LENGTH_SHORT);
            }
        }
    }

}
