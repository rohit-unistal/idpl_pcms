package unistal.com.idpl_pcms.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
//import android.support.v4.content.ContextCompat;
import android.util.Log;

import androidx.core.content.ContextCompat;

import unistal.com.idpl_pcms.service.NotifyService;

public class SensorRestarterBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(SensorRestarterBroadcastReceiver.class.getSimpleName(), "Service Stops! Oops!!!!");
        //context.startService(new Intent(context, NotifyService.class));
        Context oAppContext = context.getApplicationContext();

        if (oAppContext == null) {
            oAppContext = context;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ContextCompat.startForegroundService(oAppContext, new Intent(oAppContext, NotifyService.class));
        }else {
            Intent serviceIntent = new Intent(oAppContext, NotifyService.class);
            oAppContext.startService(serviceIntent);
        }

    }
}