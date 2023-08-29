package com.sai.sms;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;


import android.Manifest;
import android.content.pm.PackageManager;

import android.app.Activity;


import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.telecom.TelecomManager;
import android.telephony.SmsManager;


import android.telephony.TelephonyManager;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorialspoint.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SensorEventListener, StepListener {
public Timer timer;
    logdatabase d=new logdatabase(this);
    private static final int CAMERA_REQUEST = 1888;
    private static final int VIDEO_REQUEST=2322;
    ImageButton what;
    FileOutputStream out;
    MediaRecorder recorder;
    private static Uri imageUri = null;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    Button sendBtn,voicei,safeside,panicside;
    EditText txtphoneNo;
    EditText txtMessage;
    String phoneNo;
    FloatingActionButton callpolice;
    String message;
    private TextView textView,TvSteps;
    //private Button BtnStart,BtnStop,b1;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    public static boolean steps=false;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    public static int numSteps;
    public static String myown="";
    public static int num=0;
    //Button btnScanBarcode;
    private static final int REQUEST_PHONE_CALL = 1;
    public static int change_value=10000;
    private static  final int voice_Req=200;
    int iin;
    private static  final int REQUEST_LOCATION=1;
    public long ml;

    Button getlocationBtn;
    private final int select_audio = 120;

    LocationManager locationManager;
    String latitude,longitude;
    database dd=new database(this);
    DatabaseHandler db = new DatabaseHandler(this);
    public static ArrayList a1;
    important_database dd1=new important_database(this);
    public static ArrayList a12;
    public static ArrayList a129;
    public static ArrayList a13;
    public contact_database cont=new contact_database(this);
    public checkdatabase con=new checkdatabase(this);
    public All_contacts conta=new All_contacts(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         //a1=dd.getAllContact();
        a1=con.phone();

        //a12=dd1.getAllContactt();
        //Add permission
        try
        {
            String gdetp=d.getContact("CALL");
            //d.updateContact("CALL","CALLPOLICE");
            //Toast.makeText(MainActivity.this,"Power button is set with Calling to Police contacts",Toast.LENGTH_LONG).show();
        }
        catch(Exception e)
        {
            d.scanmethod("CALL","CALLPOLICE");
        }
        try
        {
           String gdetp=d.getContact("POWERBUTTON");

        }
        catch(Exception e)
        {
            d.scanmethod("POWERBUTTON","SMS");
        }


        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);



        getlocationBtn=findViewById(R.id.getLocation);
        callpolice = findViewById(R.id.call1820);
        what = findViewById(R.id.whatsappm);
        /*b1 = findViewById(R.id.import_contact);

*/
        callpolice.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "float", Toast.LENGTH_SHORT).show();
/*
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
                    h.postDelayed(r,250);
                }
                else if(iin==2)
                {
                    iin=0;

                    Toast.makeText(MainActivity.this, "call182", Toast.LENGTH_SHORT).show();
                    try {
                        //a12=cont.phone();

                        String k = "182";
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + k));


                        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
                        }
                        else
                        {
                            startActivity(intent);
                        }
                    }
                    catch(Exception e)
                    {
                        Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
                    }


                }


 */

            }
        });
        what.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);

                //Check gps is enable or not

                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                {
                    //Write Function To enable gps

                    OnGPS();
                }
                else
                {
                    //GPS is already On then

                    getLocation();


                    String toNumber = "+91 70320 98875"; // contains spaces.
                    toNumber = toNumber.replace("+", "").replace(" ", "");

                    Intent sendIntent = new Intent("android.intent.action.MAIN");
                    sendIntent.putExtra("jid", toNumber + "@s.whatsapp.net");
                    sendIntent.putExtra(Intent.EXTRA_TEXT, myown);
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.setPackage("com.whatsapp");
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                }

            }
        });
        /*btnScanBarcode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i=new Intent( MainActivity.this,scan.class);
                startActivity(i);
            }
        });*/
        safeside=findViewById(R.id.safe);
        safeside.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


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
                    h.postDelayed(r,250);
                }
                else if(iin==2)
                {
                    iin=0;
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS},REQUEST_PHONE_CALL);
                    }
                    else
                    {
                        savesms();

                    }



                }






            }
        });
        panicside=findViewById(R.id.panic);
        panicside.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

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
                    h.postDelayed(r,250);
                }
                else if(iin==2)
                {
                    iin=0;

                    Random random = new Random();
                    try {
                        //a12=cont.phone();
                        a129 = con.getphones("POLICE_CONTACTS");
                        String k = (String) a129.get(random.nextInt(a129.size()));
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + k));

                        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
                        }
                        else
                        {
                            startActivity(intent);
                            Toast.makeText(MainActivity.this, "Calling", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch(Exception e)
                    {
                        Toast.makeText(MainActivity.this,"Police contacts are empty",Toast.LENGTH_SHORT).show();
                    }


                }













                //panicsms();
            }
        });


       voicei=findViewById(R.id.voice);
        voicei.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);

                //Check gps is enable or not

                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                {
                    //Write Function To enable gps

                    OnGPS();
                }
                else
                {

                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS},REQUEST_PHONE_CALL);
                    }
                    else
                    {
                        getLocation();
                        numSteps = 0;
                        sensorManager.registerListener(MainActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);
                        steps=true;

                        Intent i=new Intent( MainActivity.this,voice1.class);
                        startActivity(i);
                    }


                    //GPS is already On then


                }

            }
        });


        getlocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);

                //Check gps is enable or not

                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                {
                    //Write Function To enable gps

                    OnGPS();
                }
                else
                {
                    //GPS is already On then

                    getLocation();
                    Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Insert Subject here");

                    shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,myown);
                    startActivity(Intent.createChooser(shareIntent, "Share via"));
                }


            }
        });
        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);



        /*  sensor detector class */


        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);


        /*  sensor detector class */

        TvSteps = findViewById(R.id.tv_steps);
        //BtnStart =findViewById(R.id.btn_start);
        //BtnStop =findViewById(R.id.btn_stop);



       /* BtnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                //numSteps = 0;
                //sensorManager.registerListener(MainActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);
                Intent i=new Intent( MainActivity.this,storingphone.class);
                startActivity(i);
            }
        });*/


        /*BtnStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);

                //Check gps is enable or not

                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                {
                    //Write Function To enable gps

                    OnGPS();
                }
                else
                {
                    //GPS is already On then

                    Intent i=new Intent( MainActivity.this,updatesms.class);
                    startActivity(i);
                }


                //sensorManager.unregisterListener(MainActivity.this);

            }
        });
*/

        sendBtn = (Button) findViewById(R.id.btnSendSMS);
        txtphoneNo = (EditText) findViewById(R.id.editText);
        txtMessage = (EditText) findViewById(R.id.editText2);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendSMSMessage();

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.sai,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            /*case R.id.aadd:
                Toast.makeText(this,"add all",Toast.LENGTH_SHORT).show();
                Intent i=new Intent( MainActivity.this,add.class);
                startActivity(i);
                return true;*/
             /*case R.id.seenew:
                Toast.makeText(this,"add all",Toast.LENGTH_SHORT).show();
                Intent i=new Intent( MainActivity.this,seecontactsdetails.class);
                startActivity(i);
                return true; */
            case R.id.check:
                Toast.makeText(this,"add all",Toast.LENGTH_SHORT).show();
                Intent ip=new Intent( MainActivity.this,checkcontacts.class);
                startActivity(ip);
                return true;
            case R.id.powersms:

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS},REQUEST_PHONE_CALL);
                }
                else
                {
                    locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);

                    //Check gps is enable or not

                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                    {
                        //Write Function To enable gps

                        OnGPS();
                    }
                    else
                    {
                        d.updateContact("POWERBUTTON","SMS");
                        Toast.makeText(MainActivity.this,"Power button is set with sms",Toast.LENGTH_SHORT).show();
                        String gdet;

                    }

                }








                return true;
            case R.id.callingimportant:


                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
                }
                else
                {
                    String gdeti;
                    d.updateContact("POWERBUTTON","CALLING");
                    d.updateContact("CALL","CALLIMPORTANT");
                    Toast.makeText(MainActivity.this,"Power button is set with Calling to Important contacts",Toast.LENGTH_LONG).show();

                }










                return true;
            case R.id.callingpolice:



                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
                }
                else
                {

                    String gdetp;
                    d.updateContact("POWERBUTTON","CALLING");
                    d.updateContact("CALL","CALLPOLICE");
                    Toast.makeText(MainActivity.this,"Power button is set with Calling to Police contacts",Toast.LENGTH_LONG).show();


                }













                return true;
            case R.id.calling:

                Random random = new Random();
                try {
                    //a12=cont.phone();
                    a12 = con.getphones("IMPORTANT_CONTACTS");
                    String k = (String) a12.get(random.nextInt(a12.size()));
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + k));


                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
                    }
                    else
                    {
                        startActivity(intent);
                    }
                }
                catch(Exception e)
                {
                    Toast.makeText(this,"Important contacts are empty",Toast.LENGTH_SHORT).show();
                }



                return true;
            /*case R.id.adel:
                Toast.makeText(this,"delete all",Toast.LENGTH_SHORT).show();
                Intent ii=new Intent( MainActivity.this,delete.class);
                startActivity(ii);
                return true;*/
            /*case R.id.voiceout:

                locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);

                //Check gps is enable or not

                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                {
                    //Write Function To enable gps

                    OnGPS();
                }
                else
                {
                    //GPS is already On then

                    getLocation();
                }
                numSteps = 0;
                sensorManager.registerListener(MainActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);
                steps=true;


                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                        "Hi speak something");
                try {
                    startActivityForResult(intent, voice_Req);
                } catch (ActivityNotFoundException a) {

                }
                return true;
                */
           /* case R.id.iadd:
                Toast.makeText(this,"add important",Toast.LENGTH_SHORT).show();
                Intent i1=new Intent( MainActivity.this,addimportant.class);
                startActivity(i1);
                return true;*/
          /*  case R.id.voicein:

                locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);

                //Check gps is enable or not

                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                {
                    //Write Function To enable gps

                    OnGPS();
                }
                else
                {
                    //GPS is already On then

                    getLocation();
                }
                numSteps = 0;
                sensorManager.registerListener(MainActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);
                steps=true;

                Intent ivoice=new Intent( MainActivity.this,voice1.class);
                startActivity(ivoice);

                return true; */
            /*case R.id.idel:
                Toast.makeText(this,"delete importaant",Toast.LENGTH_SHORT).show();
                Intent i2=new Intent( MainActivity.this,deleteimportant.class);
                startActivity(i2);

                return true;*/
            case R.id.camera:

                locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);

                //Check gps is enable or not

                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                {
                    //Write Function To enable gps

                    OnGPS();
                }
                else
                {
                    //GPS is already On then

                    if (ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    {
                        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, REQUEST_LOCATION);
                    }
                    else {
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }

                }







                return true;
            case R.id.videorecord:




                locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);

                //Check gps is enable or not

                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                {
                    //Write Function To enable gps

                    OnGPS();
                }
                else
                {
                    //GPS is already On then

                    if (ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    {
                        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, REQUEST_LOCATION);
                    }
                    else {
                        Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);


                        // set the video image quality to high
                        videoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                        startActivityForResult(videoIntent, VIDEO_REQUEST);
                    }
                }





                return true;
            /*case R.id.imagesss:
                Toast.makeText(this,"delete importaant",Toast.LENGTH_SHORT).show();
                Intent i22=new Intent( MainActivity.this,imagesharing.class);
                startActivity(i22);

                return true;

             */

            case R.id.recordact:





                if (ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO}, 8);
                }
                else {
                    androidx.appcompat.app.AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                    alertDialog.setTitle("RECORDER");
                    alertDialog.setMessage("Record and share");

                    LinearLayout layout = new LinearLayout(this);
                    layout.setOrientation(LinearLayout.VERTICAL);

                    // Add a TextView here for the "Title" label, as noted in the comments
                    final Button username = new Button(this);
                    username.setText("Start Recording");
                    layout.addView(username); // Notice this is an add method

                    // Add another TextView here for the "Description" label
                    final Button phone = new Button(this);
                    phone.setText("Stop Recording");
                    //phone.setInputType(InputType.TYPE_CLASS_NUMBER);
                    layout.addView(phone); // Another add method

                    alertDialog.setView(layout);
                    username.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            username.setEnabled(false);
                            phone.setEnabled(true);

                            File dir = new File(getExternalFilesDir("audio").toString());
                            ml= System.currentTimeMillis();
                            //String k = "/storage/emulated/0/DCIM/" + ml + ".jpg";

                            //File dir=new File("~/m/#q=");
                            //dir.mkdir();
                            File file = new File(dir, ml + ".mp3");
       /* try {
            audiofile = File.createTempFile("sound", ".3gp", dir);
        } catch (IOException e) {
            Log.e(TAG, "external storage access error");
            return;
        }*/
                            //Creating MediaRecorder and specifying audio source, output format, encoder & output format
                            recorder = new MediaRecorder();
                            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                            recorder.setOutputFile(file.getAbsolutePath());
                            try {
                                recorder.prepare();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            recorder.start();
                            Toast.makeText(MainActivity.this,"Recording started,Speak something?",Toast.LENGTH_SHORT).show();

                        }
                    });
                    phone.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            username.setEnabled(true);
                            phone.setEnabled(false);
                            recorder.stop();
                            recorder.release();
                            Toast.makeText(MainActivity.this,"Recording stopped",Toast.LENGTH_SHORT).show();
                            //after stopping the recorder, create the sound file and add it to media library.
                            addRecordingToMediaLibrary();
                        }
                    });


                    alertDialog.show();
                }
                //Toast.makeText(this,"delete importaant",Toast.LENGTH_SHORT).show();

                return true;

           /* case R.id.contacts:




                Intent icontact=new Intent( MainActivity.this,contact.class);
                startActivity(icontact);

                return true;*/


            case R.id.changetime:
                android.app.AlertDialog.Builder builder;

                final SharedPreferences prefs = this.getSharedPreferences("sai", Context.MODE_PRIVATE);
                builder = new android.app.AlertDialog.Builder(this);
                final NumberPicker np = new NumberPicker(this);
                final int iv=5;
                final int fv=45;
                np.setMinValue(iv);
                np.setMaxValue(fv);
                np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        if(iv==newVal && fv==oldVal)
                        {
                            picker.setValue(iv);
                        }
                        else if(fv==newVal && iv==oldVal ){
                            picker.setValue(fv);
                        }
                        else {
                            picker.setValue((newVal < oldVal) ? oldVal - 5 : oldVal + 5);
                        }
                    }

                });
                np.setValue(prefs.getInt("Time", 10));
                builder.setView(np);
                builder.setTitle("Time(in Minutes) as seconds");
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                          change_value=1000*np.getValue();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                Dialog dialog = builder.create();
                dialog.getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                dialog.show();

                return true;
            case R.id.updatesmsa:
                if (ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS}, REQUEST_LOCATION);
                }
                else {
                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                    //Check gps is enable or not

                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        //Write Function To enable gps

                        OnGPS();
                    } else {
                        //GPS is already On then

                        String get;
                        try{
                            get=d.getContact("UPDATESMS");
                        }catch (Exception e)
                        {
                            d.scanmethod("UPDATESMS","0");
                            get="0";
                        }
                        open(get);
                        //t1.setText(k);
                    }

                }
                return true;
            /*case R.id.Retrive:
                String s1="";
                a13=db.getAllContacts();
                for(Object str:a13) {
                    s1 = s1 + (String) str;

                }
                 Toast.makeText(MainActivity.this,s1,Toast.LENGTH_SHORT).show();
                android.app.AlertDialog.Builder builder2 = new android.app.AlertDialog.Builder(this);
                builder2.setTitle("SPOOR ENORMITY AT UNKNOWN LOCATION");
                builder2.setMessage(s1);
                builder2.setNegativeButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder2.create().show();


                return true;
            case R.id.deletescan:

                db.deleteContact();
                return true;*/
            case R.id.record:

                if (ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_LOCATION);
                }
                else {
                    try {
                        //Intent in = new Intent(Intent.ACTION_PICK);
                        //in.setType("audio/*");
                        Intent audio = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                        startActivityForResult(audio, select_audio);
                        //startActivityForResult(in, );
                    }
                    catch(Exception e)
                    {
                        Toast.makeText(MainActivity.this,"Not supported",Toast.LENGTH_SHORT).show();
                    }
                }

                return true;
            /*case R.id.scanbar:
                Intent scan=new Intent( MainActivity.this,barcode.class);
                startActivity(scan);

                return true;*/

        }
        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST) {
            //imageUri = "m";
            String str="";
            locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            {
                //Write Function To enable gps

                OnGPS();
            }
            else
            {
                //GPS is already On then

                str=getLocation();
                imageUri = data.getData();
                //Log.e("LOB","wasScreenOn"+imageUri.toString());

                try {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    File f = Environment.getDownloadCacheDirectory();
                    File fff = Environment.getRootDirectory();

                    File dir = new File(getExternalFilesDir("hello").toString());
                    long ml = System.currentTimeMillis();
                    //String k = "/storage/emulated/0/DCIM/" + ml + ".jpg";
                    String k=getExternalFilesDir("hello").toString()+"/"+ml+".jpg";
                    //File dir=new File("~/m/#q=");
                    //dir.mkdir();
                    File file = new File(dir, ml + ".jpg");


                    Uri urip = Uri.parse(k);
                    //t23.setText(fff.toString());
                    //t23.setText(urip.toString());
                    shareImage(urip,str);
                    try {
                        out = new FileOutputStream(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    try {
                        out.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //Uri tempUri = getImageUri(getApplicationContext(), photo);
                    //t23.setText(tempUri.toString());
                    //Toast.makeText(MainActivity.this,"Here "+ getRealPathFromURI(tempUri),                 Toast.LENGTH_LONG).show();

                    //imageView.setImageBitmap(photo);
                }
                catch(Exception e)
                {
                    Toast.makeText(this, "Camera cancelled", Toast.LENGTH_SHORT).show();
                }
            }



        }
        else if(requestCode==select_audio)
        {
            try {
                imageUri = data.getData();
                shareaudio(imageUri);
            }
            catch(Exception e)
            {
                Toast.makeText(MainActivity.this,"Record cancelled",Toast.LENGTH_SHORT).show();
            }

        }
        else if(requestCode==voice_Req)
        {
            Toast.makeText(MainActivity.this,"voice data",Toast.LENGTH_SHORT).show();
            if (null != data) {

                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                if((result.get(0)).equals("hello"))
                {

                    smssms(MainActivity.this);
                    Toast.makeText(MainActivity.this,"voice data",Toast.LENGTH_SHORT).show();
                }
            }
        }
        else if(requestCode==VIDEO_REQUEST)
        {
           /* //Bitmap photo = (Bitmap) data.getExtras().get("data");
            File dir = new File(getExternalFilesDir("video").toString());
            long ml = System.currentTimeMillis();
            //String k = "/storage/emulated/0/DCIM/" + ml + ".jpg";
            String k=getExternalFilesDir("video").toString()+"/"+ml+".mp4";
            //File dir=new File("~/m/#q=");
            //dir.mkdir();
            File f = new File(dir, ml + ".mp4");


            try {
                out = new FileOutputStream(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //photo.compress(Bitmap.CompressFormat.WEBP, 100, out);
            try {
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
*/
try {

    imageUri = data.getData();

    //File f=new File("/storage/emulated/0/DCIM/Camera/lop.mp4");
    //Uri u=Uri.parse(f.getPath());
    sharevideo(imageUri);

}
catch (Exception em)
{
    Toast.makeText(MainActivity.this,"Video canncelled",Toast.LENGTH_SHORT).show();
}
        }


    }
    private void shareaudio(Uri imagePath) {
        String s1="";
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        sharingIntent.setType("audio/*");
        locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            //Write Function To enable gps

            OnGPS();
        }
        else
        {
            //GPS is already On then

            s1=getLocation();
        }
        sharingIntent.putExtra(Intent.EXTRA_STREAM, imagePath);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,s1);
        startActivity(Intent.createChooser(sharingIntent, "Share Audio Using"));
    }
    private void shareImage(Uri im,String s) {

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        sharingIntent.setType("image/*");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, im);

        //Check gps is enable or not


        
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,s);
        startActivity(Intent.createChooser(sharingIntent, "Share Image Using"));
    }
    private void sharevideo(Uri im) {
String str="";
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        sharingIntent.setType("video/*");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, im);

        //Check gps is enable or not
        locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //Check gps is enable or not

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            //Write Function To enable gps

            OnGPS();
        }
        else
        {
            //GPS is already On then

            str=getLocation();
        }

        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,str);
        startActivity(Intent.createChooser(sharingIntent, "Share Image Using"));
    }
    public void loc() {

        getLocation();

    }
    public String getLocation() {

        //Check Permissions again

        if (ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this,

                Manifest.permission.ACCESS_COARSE_LOCATION) !=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
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
                Toast.makeText(this, "Can't Get Your Location", Toast.LENGTH_SHORT).show();

            }

            //Thats All Run Your App
        }
         return myown;
    }

    public void open(String s){









        androidx.appcompat.app.AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("UPDATE SMS");
        alertDialog.setMessage("Send sms every time");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Add a TextView here for the "Title" label, as noted in the comments
        final Button username = new Button(this);
        username.setText("Start");
        layout.addView(username); // Notice this is an add method

        // Add another TextView here for the "Description" label
        final Button phone = new Button(this);
        phone.setText("Stop");
        //phone.setInputType(InputType.TYPE_CLASS_NUMBER);
        layout.addView(phone); // Another add method

        if(s.equals("0"))
        {
            username.setEnabled(true);
            phone.setEnabled(false);
        }
        else if(s.equals("1"))
        {
            username.setEnabled(false);
            phone.setEnabled(true);
        }



        alertDialog.setView(layout);
        username.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                username.setEnabled(false);
                phone.setEnabled(true);


                d.updateContact("UPDATESMS","1");

                //((AlertDialog)arg0).getButton(arg1).setVisibility(View.INVISIBLE);
                //a12=dd1.getAllContactt();
                a12= con.getphones("IMPORTANT_CONTACTS");
                timer = new Timer();
                //Toast.makeText(MainActivity.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                if(a12.size()!=0) {
                    try {

                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        getLocation();

                                        sms123();



                                    }
                                });
                            }
                        }, 0, change_value);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Please try again later", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(MainActivity.this,"Sms sending Started",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Important contacts empty", Toast.LENGTH_SHORT).show();
                    username.setEnabled(true);
                    phone.setEnabled(false);
                    d.updateContact("UPDATESMS","0");
                }











            }
        });
        final String ssp=s;
        phone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                username.setEnabled(true);
                phone.setEnabled(false);


                try {
                    timer.cancel();
                    String get="";
                    try{
                        get=d.getContact("UPDATESMS");
                    }catch (Exception e)
                    {
                        d.scanmethod("UPDATESMS","0");
                        get="0";
                    }
                    if(get.equals("1")) {
                        savesms();
                    }
                    d.updateContact("UPDATESMS","0");
                    Toast.makeText(MainActivity.this,"Sms sending Stopped",Toast.LENGTH_SHORT).show();
                }
                catch(Exception e){
                    Toast.makeText(MainActivity.this, "Sorry Not at started", Toast.LENGTH_LONG).show();
                }
            }
        });


        alertDialog.show();

















       /* android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Send sms automatically");
        alertDialogBuilder.setPositiveButton("Start",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        //((AlertDialog)arg0).getButton(arg1).setVisibility(View.INVISIBLE);
                        //a12=dd1.getAllContactt();
                        a12= con.getphones("IMPORTANT_CONTACTS");
                        timer = new Timer();
                        //Toast.makeText(MainActivity.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                        if(a12.size()!=0) {
                            try {

                                timer.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {


                                                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                                                //Check gps is enable or not

                                                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                                                    //Write Function To enable gps

                                                    OnGPS();
                                                } else {
                                                    //GPS is already On then

                                                    getLocation();

                                                    sms123();
                                                    //t1.setText(k);
                                                }


                                            }
                                        });
                                    }
                                }, 0, change_value);
                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this, "Please try again later", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Important constact empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        alertDialogBuilder.setNegativeButton("Stop",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //finish();
                try {
                    savesms();
                    timer.cancel();
                    Toast.makeText(MainActivity.this, "Stopped", Toast.LENGTH_LONG).show();
                }
                catch(Exception e){
                    Toast.makeText(MainActivity.this, "Sorry Not at started", Toast.LENGTH_LONG).show();
                }
            }
        });

        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show(); */
    }

    public void OnGPS() {

        final AlertDialog.Builder builder= new AlertDialog.Builder(this);

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




    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void step(long timeNs) {
        numSteps++;
        TvSteps.setText(TEXT_NUM_STEPS + numSteps);
       /* if(steps)
        {}
        else{
        if(numSteps!=6){
            TvSteps.setText(TEXT_NUM_STEPS + numSteps);}
        else
        {
            Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Insert Subject here");
            String app_url = "I AM IN DANGER PLEASE HELP"+ "   my location is   https://www.google.com/maps/dir/?api=1&destination=";
            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,app_url);
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        }}
        steps=false;*/
    }
    public void smssms(Context c){

        //a1=dd.getAllContact();
        if (numSteps>6) {


            String s1;
            for(Object str:a1) {
                s1=(String)str;
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(s1, null,myown, null, null);
            }


            //smsManager.sendTextMessage("9502549235", null,"sampou is not replay", null, null);
            //smsManager.sendTextMessage("9949335791", null,myown, null, null);
            Toast.makeText(c, "SMS sent.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(c, "SMS faild, please try again.", Toast.LENGTH_LONG).show();

        }
        //Toast.makeText(MainActivity.this, "SMgain.", Toast.LENGTH_LONG).show();

    }
    public void sms123()
    {
        if (true) {


            String s1;
            for(Object str:a12) {
                s1=(String)str;
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(s1, null,myown, null, null);
            }


            //smsManager.sendTextMessage("9502549235", null,"sampou is not replay", null, null);
            //smsManager.sendTextMessage("9949335791", null,myown, null, null);
            //Toast.makeText(MainActivity.this, "SMS sent.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this,
                    "SMS faild, please try again.", Toast.LENGTH_LONG).show();

        }
    }
    public void savesms()
    {
try {
    a12 = con.getphones("IMPORTANT_CONTACTS");
    if (true) {


        String s1;
        if(a12.size()!=0) {
            for (Object str : a12) {
                s1 = (String) str;
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(s1, null, "I AM SAFELY REACHED MY DESTINATION", null, null);


            }
            String get="";
            try{
                get=d.getContact("UPDATESMS");
            }catch (Exception e)
            {
                d.scanmethod("UPDATESMS","0");
                get="0";
            }
            if(get.equals("0")) {
                Toast.makeText(MainActivity.this, "SMS SENT", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(MainActivity.this, "Important contact are empty", Toast.LENGTH_SHORT).show();
        }


        //smsManager.sendTextMessage("9502549235", null,"sampou is not replay", null, null);
        //smsManager.sendTextMessage("9949335791", null,myown, null, null);
        //Toast.makeText(MainActivity.this, "SMS sent.", Toast.LENGTH_LONG).show();
    } else {
        Toast.makeText(MainActivity.this,
                "SMS faild, please try again.", Toast.LENGTH_LONG).show();

    }
}
catch (Exception e)
{
    Toast.makeText(MainActivity.this, "Important contact are empty", Toast.LENGTH_LONG).show();
}
    }

    public void sendSMSMessage() {
        phoneNo = txtphoneNo.getText().toString();
        message = txtMessage.getText().toString();


        if (ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS}, 1);
        }
        else {
            try {
                if (!phoneNo.isEmpty() && !message.isEmpty() ) {
                    if(phoneNo.length()==10) {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneNo, null, message, null, null);
                        Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
                        txtphoneNo.setText("");
                        txtMessage.setText("");

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),
                                "SMS faild, Please enter valid phone number", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, Please fill the both field.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            catch(Exception e)
            {
                Toast.makeText(MainActivity.this,"Not supported",Toast.LENGTH_SHORT).show();
            }
        }

     /*  if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {

            }
            else
                {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        } */
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null,message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }
    protected void addRecordingToMediaLibrary() {
        String k=getExternalFilesDir("audio").toString()+"/"+ml+".mp3";
        Uri urip = Uri.parse(k);
        //t23.setText(fff.toString());
        //t23.setText(urip.toString());
        shareauio(urip);
    }
    private void shareauio(Uri imagePath) {
        String s1="";
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        sharingIntent.setType("audio/*");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, imagePath);
        startActivity(Intent.createChooser(sharingIntent, "Share Audio Using"));
    }
}