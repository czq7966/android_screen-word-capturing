package com.nd.screen_word_capturing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Screen_Word_Capturing_Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");
        //Toast.makeText(context, "这是例子", Toast.LENGTH_LONG).show();
        NotificationIntentService.startActionFoo(context, "1", "2");
    }
}
