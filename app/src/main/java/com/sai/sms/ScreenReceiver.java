package com.sai.sms;
import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ScreenReceiver extends BroadcastReceiver  {
   public accessing a=new accessing();
    public static boolean wasScreenOn = true;
    public static int j=0;
    int iin;

    @Override
    public void onReceive(final Context c, final Intent intent) {
        Log.e("LOB","onReceive");
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {

            // do whatever you need to do here
            wasScreenOn = false;
            Log.e("LOB","wasScreenOn"+wasScreenOn);
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {


            iin++;
            Handler h=new Handler();
            Runnable r=new Runnable() {
                @Override
                public void run() {
                    iin=0;
                }
            };
            if(iin==1)
            {
                h.postDelayed(r,2000);
            }
            else if(iin==2)
            {
                iin=0;
                Intent i=new Intent(c,accessing.class);
                c.startActivity(i);
                j=0;



            }
















            j++;
            if(j==3)
            {



            }
            // and do whatever you need to do here
            wasScreenOn = true;

        } else if(intent.getAction().equals(Intent.ACTION_USER_PRESENT)){

        }
    }
}