package com.nd.screen_word_capturing;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import com.androidyuan.lib.screenshot.ScreenShotActivity;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class NotificationIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.nd.screen_word_capturing.action.FOO";
    private static final String ACTION_BAZ = "com.nd.screen_word_capturing.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.nd.screen_word_capturing.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.nd.screen_word_capturing.extra.PARAM2";

    public NotificationIntentService() {
        super("NotificationIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, NotificationIntentService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, NotificationIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
     //   throw new UnsupportedOperationException("Not yet implemented");
        startActivity(new Intent(this, ScreenShotActivity.class));

    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
       // throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        // TODO: It would be nice to have an option to hold a partial wakelock
        // during processing, and to have a static startService(Context, Intent)
        // method that would launch the service & hand off a wakelock.

        super.onCreate();
        createNotification();
    }

    public void createNotification() {
        NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder nb = new Notification.Builder(this);
        nb.setSmallIcon(R.mipmap.ic_launcher);
        nb.setContentTitle("Content Title");
        nb.setContentText("Content Text");
        nb.setAutoCancel(false);
        nb.setOngoing(true);



        //        (R.mipmap.ic_launcher,"Hello,there!", System.currentTimeMillis());
        //n.flags = Notification.FLAG_AUTO_CANCEL;

        Intent intent = new Intent("com.nd.screen_word_capturing.start");//这个意图的action为FILE_NOTIFICATION
        PendingIntent mPI = PendingIntent.getBroadcast(this,0,intent,0);

        nb.setContentIntent(mPI);

        //n.setLatestEventInfo(this, "在"+SearchBroadCast.mServiceSearchPath+"下搜索", "搜索关键字为"+SearchBroadCast.mServiceKeyword+"【点击可取消搜索】", mPI);

        //n.flags=Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;      //可以让通知不被删掉

        nm.notify(R.string.app_name,   nb.build());
        //nm.cancel(R.string.app_name);  //取消通知

    }
}
