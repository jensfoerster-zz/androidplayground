package de.my.playground;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by dep01181 on 9/30/2015.
 */
public class BroadcastFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_broadcast, container, false);

        FloatingActionButton send = (FloatingActionButton) v.findViewById(R.id.fab_bc);
        send.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent(getResources().getString(R.string.INTENT_BUTTON_PRESSED)));
            }
        });

        return v;
    }
}
