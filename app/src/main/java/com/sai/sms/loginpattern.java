package com.sai.sms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.tutorialspoint.R;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class loginpattern extends AppCompatActivity {
    logdatabase d=new logdatabase(this);
    Timer timer;
    public ArrayList a232=new ArrayList();
    PatternLockView patternLockView;
    public String get="";
    public int counter=5;
    public int check=1;
    ImageView image;
    TextView cou;
    Button b3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpattern);

        b3=findViewById(R.id.butto);
        cou=findViewById(R.id.count);
        image=findViewById(R.id.countimage);
        image.setVisibility(View.INVISIBLE);


        b3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {


                Intent i=new Intent( loginpattern.this,setpattern.class);
                //Intent i=new Intent( loginpattern.this,pinlock.class);
                startActivity(i);

            }
        });
        ShowGif();
        patternLockView = findViewById(R.id.patternView);

        patternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {
                ShowGif();
            }

            @Override
            public void onProgress(List progressPattern) {
                        show();
            }

            @Override
            public void onComplete(List pattern) {
                int po=0;
                try {
                    get = d.getContact("SPOOR ENORMITY AT UNKNOWN LOCATION");
                    Log.d(getClass().getName(), "Pattern complete: " +
                            PatternLockUtils.patternToString(patternLockView, pattern));
                    if (PatternLockUtils.patternToString(patternLockView, pattern).equalsIgnoreCase(get)) {
                        patternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
                        //Toast.makeText(loginpattern.this, "Welcome back, CodingDemos", Toast.LENGTH_LONG).show();
                        Intent i=new Intent( loginpattern.this,MainActivity.class);
                        startActivity(i);
                    } else {
                        patternLockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
                        Toast.makeText(loginpattern.this, "Incorrect Pattern" + get, Toast.LENGTH_LONG).show();
                        Show();

                        if(check==3) {
                            pleasewait();
                            patternLockView.setVisibility(View.INVISIBLE);
                            image.setVisibility(View.VISIBLE);
                            cou.setVisibility(View.VISIBLE);
                            new CountDownTimer(5000,1000){
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    cou.setText(String.valueOf(counter));
                                    counter--;
                                }

                                @Override
                                public void onFinish() {
                                      counter=5;
                                      ShowGif();
                                }
                            }.start();
                            try {
                                timer = new Timer();
                                timer.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                check=1;
                                                cou.setVisibility(View.INVISIBLE);
                                                image.setVisibility(View.INVISIBLE);
                                                patternLockView.setVisibility(View.VISIBLE);
                                                timer.cancel();


                                            }
                                        });
                                    }
                                }, 5000, 1);
                            } catch (Exception e) {
                                Toast.makeText(loginpattern.this, "Please try again later", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            check++;
                        }

                        }





                }
                catch(Exception e)
                {
                    Toast.makeText(loginpattern.this, "Pattern is not updated in database", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onCleared() {
                       ShowGif();
            }
        });
    }
    public void ShowGif() {
        ImageView imageView = findViewById(R.id.ima);
        Glide.with(loginpattern.this).load(R.drawable.login).into(imageView);
    }
    public void pleasewait() {
        ImageView imageView = findViewById(R.id.ima);
        Glide.with(loginpattern.this).load(R.drawable.wait).into(imageView);
    }
    public void Show() {
        ImageView imageView = findViewById(R.id.ima);
        Glide.with(loginpattern.this).load(R.drawable.imaget).into(imageView);
    }
    public void show() {
        ImageView imageView = findViewById(R.id.ima);
        Glide.with(loginpattern.this).load(R.drawable.load).into(imageView);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.hemanth,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:

                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setTitle("RPF(Railway Protection Force)");
                builder2.setMessage(
                        "This app is developed by"+" \n \n \n P.BHUVANESWARI   - 16M61A0591\n" +
                                "  P.HEMANTH SAI     - 16M61A0594\n" +
                                "  Y.PRAVALLIKA         - 16M61A05B3\n" +
                                "  Y.SAI SOUJANYA     - 16M61A05B5 \n \n \n"+"Under the guidance of:\n" +
                                "Mr.Md.Sajid Pasha M.TECH\n" +
                                " Assistant professor \n");
                builder2.setNegativeButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder2.create().show();


                return true;
            case R.id.PIN:
                         d.updateContact("CHECK","PIN");
                         Intent i=new Intent(loginpattern.this,pinlock.class);
                         startActivity(i);
                         finish();



                return true;
            case R.id.setpattern:


                Intent ipat=new Intent( loginpattern.this,setpattern.class);
                //Intent i=new Intent( loginpattern.this,pinlock.class);
                startActivity(ipat);


                return true;

            case R.id.usermanual:


                //String url = "https://drive.google.com/open?id=1Shx56qUmk9SE75ug05Jzd8xgDKxAjkK4";
                String url = "https://drive.google.com/file/d/1YOrs_5oo7a5kGiLqoJBR_MdvPbD51H2k/view?usp=drivesdk";
                Intent im = new Intent(Intent.ACTION_VIEW);
                im.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                im.setData(Uri.parse(url));
                startActivity(im);


                return true;


        }
        return super.onOptionsItemSelected(item);
    }
}
