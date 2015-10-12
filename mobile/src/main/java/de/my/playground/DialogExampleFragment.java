package de.my.playground;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by dep01181 on 10/12/2015.
 */
public class DialogExampleFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog_example, container, false);

        (v.findViewById(R.id.dialog_openpopup)).setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DetailsDialog detailsFragment = new DetailsDialog();

                        //set arguments (Bundle)
                        //detailsFragment.setArguments();

                        // Show details as dialog
                        detailsFragment.show(getFragmentManager(), "");
                        //fragmentManager.beginTransaction().add(R.id.drawer_layout, detailsFragment).addToBackStack(null).commit();

                    }
                });

        (v.findViewById(R.id.dialog_openfull)).setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DetailsDialog detailsFragment = new DetailsDialog();

                        //set arguments (Bundle)
                        //detailsFragment.setArguments();

                        // Show details fullscreen
                        getFragmentManager().beginTransaction().add(R.id.drawer_layout, detailsFragment).addToBackStack(null).commit();

                    }
                });
        return v;
    }
}
