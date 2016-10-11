package de.my.playground.services;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import de.my.playground.misc.CustomIntent;

/**
 * Notification Listener Service - sends broadcasts for every new notification.
 * As an example this Service can be started via onBind or startService.
 * Created by dep01181 on 07.09.2016.
 */
public class NLService extends NotificationListenerService {

    public static String TAG = "NLService";
    private ServiceBinder mBinder = new ServiceBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "on create");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return mBinder;
    }

    @Override
    public void onListenerConnected() {
        Log.d(TAG, "onListenerConnected");
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Log.d(TAG, "onNotificationPosted id = " + sbn.getId() + "Package Name" + sbn.getPackageName() +
                "Post time = " + sbn.getPostTime() + "Tag = " + sbn.getTag());

        Intent i = new Intent(CustomIntent.NOTIFICATION_POSTED_ACTION);
        i.putExtra(CustomIntent.NOTIFICATION_POSTED_EXTRA, sbn.getId());
        LocalBroadcastManager.getInstance(this).sendBroadcast(i);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.d(TAG, "onNotificationRemoved id = " + sbn.getId() + "Package Name" + sbn.getPackageName() +
                "Post time = " + sbn.getPostTime() + "Tag = " + sbn.getTag());

        Intent i = new Intent(CustomIntent.NOTIFICATION_REMOVED_ACTION);
        i.putExtra(CustomIntent.NOTIFICATION_REMOVED_EXTRA, sbn.getId());
        LocalBroadcastManager.getInstance(this).sendBroadcast(i);
    }

    public class ServiceBinder extends Binder {
        public NLService getService() {
            return NLService.this;
        }
    }

    /*

    *** TO USE THIS ADD THE FOLLOWING LINES TO THE CALLING CLASS ***

    Intent sm = new Intent(this, NLService.class);
    bindService(sm, notificationListenerConnection, Context.BIND_AUTO_CREATE);
    [...]
    unbindService(notificationListenerConnection);

    ServiceConnection notificationListenerConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mNotificationListenerService = ((NLService.ServiceBinder)service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mNotificationListenerService = null;
        }
    };

    *** OR ***
    startService(sm);
    stopService(sm);


    *** TO USE THIS ADD THE FOLLOWING LINES TO THE AndroidManifest ***

    <service
            android:name=".NLService"
            android:permission="android.permission.BIND_NOTIFICATION_LISTENER_SERVICE">
            <intent-filter>
                <action android:name="android.service.notification.NotificationListenerService"/>
            </intent-filter>
        </service>
     */
}

