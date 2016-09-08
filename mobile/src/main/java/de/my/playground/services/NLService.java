package de.my.playground.services;

import android.content.Intent;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

/**
 * Created by dep01181 on 07.09.2016.
 */
public class NLService extends NotificationListenerService {

    public static String TAG = "NotificationListenerTesting";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Inside on create");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        TAG = "onNotificationPosted";
        Log.d(TAG, "id = " + sbn.getId() + "Package Name" + sbn.getPackageName() +
                "Post time = " + sbn.getPostTime() + "Tag = " + sbn.getTag());
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        TAG = "onNotificationRemoved";
        Log.d(TAG, "id = " + sbn.getId() + "Package Name" + sbn.getPackageName() +
                "Post time = " + sbn.getPostTime() + "Tag = " + sbn.getTag());

    }

    public class ServiceBinder {
        public NLService getService() {
            return NLService.this;
        }
    }

    /*

    *** TO USE THIS ADD THE FOLLOWING LINES TO THE CALLING CLASS ***

    Intent sm = new Intent(this, NLService.class);
    bindService(sm, notificationListenerConnection, Context.BIND_ABOVE_CLIENT);
    startService(new Intent(this, NLService.class));


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

