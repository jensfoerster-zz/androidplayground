package de.my.playground.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import de.my.playground.R;

/**
 * Created by jensfoerster on 9/30/2015.
 */
public class BroadcastFragment extends Fragment {

    private static final String TAG = "BroadcastFragment";
    private BroadcastReceiver mReceiver;
    private TextView mTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_broadcast, container, false);

        mTextView = (TextView)v.findViewById(R.id.textview_broadcasts);

        FloatingActionButton send = (FloatingActionButton) v.findViewById(R.id.fab_bc);
        send.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent(getResources().getString(R.string.INTENT_BUTTON_PRESSED)));
                    }
                });

        initializeBroadcastListener();

        return v;
    }

    private void initializeBroadcastListener() {
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mTextView.append(intent.getAction() + System.getProperty("line.separator"));
            }
        };

        getActivity().getApplicationContext().registerReceiver(mReceiver, new IntentFilter(Intent.ACTION_SCREEN_ON));
        getActivity().getApplicationContext().registerReceiver(mReceiver, new IntentFilter(Intent.ACTION_SCREEN_OFF));
        getActivity().getApplicationContext().registerReceiver(mReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        getActivity().getApplicationContext().registerReceiver(mReceiver, new IntentFilter(Intent.ACTION_MEDIA_BUTTON));
        getActivity().getApplicationContext().registerReceiver(mReceiver, new IntentFilter(Intent.ACTION_POWER_CONNECTED));
        getActivity().getApplicationContext().registerReceiver(mReceiver, new IntentFilter(Intent.ACTION_POWER_DISCONNECTED));
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(mReceiver, new IntentFilter(getResources().getString(R.string.INTENT_BUTTON_PRESSED)));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        /* this happens when the receiver has been unregistered or not yet registered */
        try {
            getActivity().getApplicationContext().unregisterReceiver(mReceiver);
        } catch (IllegalArgumentException iae) {
            Log.e(TAG, iae.getMessage());
        }
        try {
            LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).unregisterReceiver(mReceiver);
        } catch (IllegalArgumentException iae) {
            Log.e(TAG, iae.getMessage());
        }
    }
}
