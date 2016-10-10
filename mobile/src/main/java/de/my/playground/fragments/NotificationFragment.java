package de.my.playground.fragments;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import de.my.playground.MainActivity;
import de.my.playground.R;
import de.my.playground.misc.CustomIntent;
import de.my.playground.services.NLService;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {

    private NLService mNotificationListenerService;
    private Button mBtn_add;
    private Button mBtn_cancel;
    private Button mBtn_clear;
    private TextView mText;
    private int mNotificationId = 0;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent sm = new Intent(getContext(), NLService.class);
        getContext().bindService(sm, notificationListenerConnection, Context.BIND_AUTO_CREATE);

        View v = inflater.inflate(R.layout.fragment_notification, container, false);

        mBtn_add = (Button) v.findViewById(R.id.notif_btn_add);
        mBtn_add.setOnClickListener(
                v1 -> {
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(getContext())
                                    .setSmallIcon(R.drawable.ic_beach_access_black_24dp)
                                    .setContentTitle("ARRRRRR")
                                    .setContentText(getMessageText());
                    Intent resultIntent = new Intent(getContext(), MainActivity.class);
                    PendingIntent resultPendingIntent =
                            PendingIntent.getActivity(
                                    getContext(),
                                    0,
                                    resultIntent,
                                    PendingIntent.FLAG_UPDATE_CURRENT
                            );
                    mBuilder.setContentIntent(resultPendingIntent);
                    NotificationManager mNotifyMgr = (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);

                    mNotificationId++;
                    mNotifyMgr.notify(mNotificationId, mBuilder.build());
                    initializeView();
                });
        mBtn_cancel = (Button) v.findViewById(R.id.notif_btn_cancel);
        mBtn_cancel.setOnClickListener(
                v1 -> {
                    NotificationManager mNotifyMgr = (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);
                    mNotifyMgr.cancel(mNotificationId);
                    mNotificationId--;
                    initializeView();
                }
        );
        mBtn_clear = (Button) v.findViewById(R.id.notif_btn_clear);
        mBtn_clear.setOnClickListener(
                v1 -> {
                    NotificationManager mNotifyMgr = (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);
                    mNotifyMgr.cancelAll();
                    mNotificationId = 0;
                    initializeView();
                }
        );

        mText = (TextView) v.findViewById(R.id.notif_txt);

        initializeView();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CustomIntent.NOTIFICATION_POSTED_ACTION);
        intentFilter.addAction(CustomIntent.NOTIFICATION_REMOVED_ACTION);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mBroadcastListener, intentFilter);

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getContext().unbindService(notificationListenerConnection);
        notificationListenerConnection = null;
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mBroadcastListener);
    }

    ServiceConnection notificationListenerConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            initializeView();
            mNotificationListenerService = ((NLService.ServiceBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mNotificationListenerService = null;
        }
    };

    BroadcastReceiver mBroadcastListener = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case CustomIntent.NOTIFICATION_POSTED_ACTION:
                    mText.append("Notification posted: " + intent.getStringExtra(CustomIntent.NOTIFICATION_POSTED_EXTRA) + System.getProperty("line.separator"));
                    break;
                case CustomIntent.NOTIFICATION_REMOVED_ACTION:
                    mText.append("Notification posted: " + intent.getStringExtra(CustomIntent.NOTIFICATION_REMOVED_EXTRA) + System.getProperty("line.separator"));
                    break;
            }
        }
    };


    private void initializeView() {
        if (mText == null) return;

        mText.setText("");
        if (mNotificationListenerService == null) {
            mBtn_add.setEnabled(false);
            mBtn_cancel.setEnabled(false);
            mBtn_clear.setEnabled(false);
        } else {
            if(mNotificationId == 0){
                mBtn_cancel.setEnabled(false);
                mBtn_clear.setEnabled(false);
            } else {
                mBtn_cancel.setEnabled(true);
                mBtn_clear.setEnabled(true);
            }
            mBtn_add.setEnabled(true);
        }
    }

    private String getMessageText() {
        String[] msg = new String[]{
                "Take what you can, give nothing back",
                "You can always trust the untrustworthy because you can always trust that they will be untrustworthy. Its the trustworthy you can’t trust.",
                "If ye can’t trust a pirate, ye damn well can’t trust a merchant either!",
                "There comes a time in most men’s lives where they feel the need to raise the Black Flag.",
                "Not all treasure is silver and gold- Pirates of the Carribean",
                "Yarrrr! there be ony two ranks of leader amongst us pirates! Captain and if your really notorious then it’s Cap’n!",
                "The rougher the seas, the smoother we sail. Ahoy!",
                "Give me freedom or give me the rope. For I shall not take the shackles that subjugate the poor to uphold the rich.",
                "Why are pirates pirates? cuz they arrrrrr",
                "Now and then we had a hope that if we lived and were good, God would permit us to be pirates.",
                "Drink up me hearties yoho …a pirates life for me",
                "Why is the rum gone?",
                "To err is human but to arr is pirate!!",
                "Life’s pretty good, and why wouldn’t it be? I’m a pirate, after all.",
                "The existence of the sea means the existence of pirates.",
                "Where there is a sea there are pirates.",
                "If ye thinks he be ready to sail a beauty, ye better be willin’ to sink with her.",
                "Always be yourself, unless you can be a pirate. Then always be a pirate."};
        return msg[(int)(Math.random()*msg.length)];
    }
}
