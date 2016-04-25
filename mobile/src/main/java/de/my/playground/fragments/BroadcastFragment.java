package de.my.playground.fragments;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import de.my.playground.R;

/**
 * Created by dep01181 on 9/30/2015.
 */
public class BroadcastFragment extends Fragment {

    private static final String TAG = "BroadcastFragment";
    private BroadcastReceiver mReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        initializeBroadcastListener();

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

    private void initializeBroadcastListener() {
        mReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                TextView tv = (TextView) getActivity().findViewById(R.id.textview_broadcasts);
                tv.append(intent.getAction() + System.getProperty ("line.separator"));
            }
        };

        getActivity().getApplicationContext().registerReceiver(mReceiver, new IntentFilter(Intent.ACTION_SCREEN_ON));
        getActivity().getApplicationContext().registerReceiver(mReceiver, new IntentFilter(Intent.ACTION_SCREEN_OFF));
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(mReceiver, new IntentFilter(getResources().getString(R.string.INTENT_BUTTON_PRESSED)));
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        /* this happens when the receiver has been unregistered or not yet registered */
        try{ getActivity().getApplicationContext().unregisterReceiver(mReceiver); }
        catch (IllegalArgumentException iae) { Log.e(TAG, iae.getMessage()); }
        try{ LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).unregisterReceiver(mReceiver); }
        catch (IllegalArgumentException iae) { Log.e(TAG, iae.getMessage()); }
    }
}
