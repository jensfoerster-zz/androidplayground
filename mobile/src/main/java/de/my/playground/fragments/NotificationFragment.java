package de.my.playground.fragments;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import de.my.playground.R;
import de.my.playground.services.NLService;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {

    private NLService mNotificationListenerService;
    private Button mBtn_add;
    private Button mBtn_update;
    private Button mBtn_clear;
    private TextView mText;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent sm = new Intent(getContext(), NLService.class);
        getContext().bindService(sm, notificationListenerConnection, Context.BIND_ABOVE_CLIENT);
        getContext().startService(new Intent(getContext(), NLService.class));

        View v = inflater.inflate(R.layout.fragment_notification, container, false);

        mBtn_add = (Button)v.findViewById(R.id.notif_btn_add);
        mBtn_update = (Button)v.findViewById(R.id.notif_btn_update);
        mBtn_clear = (Button)v.findViewById(R.id.notif_btn_clear);

        mText = (TextView) v.findViewById(R.id.notif_txt);

        initializeView();

        return v;
    }

    ServiceConnection notificationListenerConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            initializeView();
            mNotificationListenerService = ((NLService.ServiceBinder)service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mNotificationListenerService = null;
        }
    };

    private void initializeView(){
        if(mText == null) return;

        mText.setText("");
        if(mNotificationListenerService == null){
            mBtn_add.setEnabled(false);
            mBtn_update.setEnabled(false);
            mBtn_clear.setEnabled(false);
        } else {
            mBtn_add.setEnabled(true);
            mBtn_update.setEnabled(true);
            mBtn_clear.setEnabled(true);
        }
    }
}
