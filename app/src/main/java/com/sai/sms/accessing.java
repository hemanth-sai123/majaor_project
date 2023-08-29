package com.sai.sms;



import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorialspoint.R;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class accessing extends AppCompatActivity{

    public static String myown="";
    LocationManager locationManager;
    String latitude,longitude;
    private static  final int REQUEST_LOCATION=1;
    public String i="";
    public checkdatabase dd=new checkdatabase(this);
    public logdatabase d=new logdatabase(this);
    public static ArrayList a1;
    String getdat="",getda="";
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatesms);

        try{
            getdat=d.getContact("POWERBUTTON");
        }
        catch (Exception e)
        {
            d.scanmethod("POWERBUTTON","SMS");
            getdat="SMS";
        }
        try{
            getda=d.getContact("CALL");
        }
        catch (Exception e)
        {
            d.scanmethod("CALL","CALLIMPORTANT");
            getda="CALLIMPORTANT";
        }
        if(getdat.equals("SMS")) {
            try {
                a1 = dd.getphones("IMPORTANT_CONTACTS");
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                //Check gps is enable or not

                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    //Write Function To enable gps

                    on();
                } else {
                    //GPS is already On then
                    Log.e("LOB", "working");
                    String k = "";
                    k = ge();
                    smsqq(k);
                    //t1.setText(k);
                }
                finish();
                //Intent i = new Intent(accessing.this, loginpattern.class);
                //startActivity(i);

            }catch (Exception e)
            {
                Toast.makeText(accessing.this,"Important contacts are empty",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        else if(getdat.equals("CALLING"))
        {
            String lo="";
try {
    if (getda.equals("CALLIMPORTANT")) {
        a1 = dd.getphones("IMPORTANT_CONTACTS");
        lo="Important";
    } else if (getda.equals("CALLPOLICE")) {
        a1 = dd.getphones("POLICE_CONTACTS");
        lo="Police";
    }

}
catch (Exception e)
{

}
            Random random = new Random();
            try {
                //a12=cont.phone();
                //a1 = con.getphones("POLICE_CONTACTS");
                String k = (String) a1.get(random.nextInt(a1.size()));
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + k));


                if (ContextCompat.checkSelfPermission(accessing.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(accessing.this, new String[]{Manifest.permission.CALL_PHONE},67);
                }
                else
                {
                    startActivity(intent);
                    Toast.makeText(accessing.this, "Calling", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
            catch(Exception e)
            {
                Toast.makeText(accessing.this,lo+" contacts are empty",Toast.LENGTH_SHORT).show();
                finish();
            }
        }

    }


public void saii()
{

}


    public void smsqq(String kl){


        if (true) {

            //SmsManager smsManager = SmsManager.getDefault();
            //smsManager.sendTextMessage("7032098875", null,kl, null, null);
           String s1;
            for(Object str:a1) {
                s1=(String)str;
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(s1, null,kl, null, null);
            }


            //smsManager.sendTextMessage("9502549235", null,"sampou is not replay", null, null);
            //smsManager.sendTextMessage("9949335791", null,myown, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(accessing.this,
                    "SMS faild, please try again.", Toast.LENGTH_LONG).show();

        }

    }

    public void on() {

        final AlertDialog.Builder builder= new AlertDialog.Builder(getApplicationContext());

        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        final AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
    public String ge() {

        //Check Permissions again

        if (ActivityCompat.checkSelfPermission(accessing.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(accessing.this,

                Manifest.permission.ACCESS_COARSE_LOCATION) !=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(accessing.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else
        {
            Location LocationGps= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (LocationGps !=null)
            {
                double lat=LocationGps.getLatitude();
                double longi=LocationGps.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);
                myown = "I AM IN DANGER PLEASE HELP"+ "   my location is   https://www.google.com/maps/dir/?api=1&destination="+latitude+","+longitude;




            }
            else if (LocationNetwork !=null)
            {
                double lat=LocationNetwork.getLatitude();
                double longi=LocationNetwork.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);




                myown = "I AM IN DANGER PLEASE HELP"+ "   my location is   https://www.google.com/maps/dir/?api=1&destination="+latitude+","+longitude;





            }
            else if (LocationPassive !=null)
            {
                double lat=LocationPassive.getLatitude();
                double longi=LocationPassive.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

                myown= "I AM IN DANGER PLEASE HELP"+ "   my location is   https://www.google.com/maps/dir/?api=1&destination="+latitude+","+longitude;



            }
            else
            {
                myown= "I AM IN DANGER PLEASE HELP"+ "   my location is   https://www.google.com/maps/dir/?api=1&destination="+latitude+","+longitude;
                Toast.makeText(accessing.this, "Can't Get Your Location", Toast.LENGTH_SHORT).show();

            }

            //Thats All Run Your App
        }
        return myown;

    }
}
