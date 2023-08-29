package com.sai.sms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;



import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import android.util.Log;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.example.tutorialspoint.R;

import java.util.List;

public class setpattern extends AppCompatActivity {
    logdatabase d=new logdatabase(this);
    PatternLockView patternLockView;
 public static int i=1;
 public String r1="",r2="",jp,r3="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setpattern);

        patternLockView = findViewById(R.id.patternVie);

        patternLockView.addPatternLockListener(new PatternLockViewListener() {


            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List progressPattern) {

            }

            @Override
            public void onComplete(List pattern) {
                try {
                    jp = d.getContact("SPOOR ENORMITY AT UNKNOWN LOCATION");
                    if(i==1)
                    {
                        r1=PatternLockUtils.patternToString(patternLockView, pattern);
                        if(jp.equals(r1))
                        {
                            i++;
                            Toast.makeText(setpattern.this, "Enter New Password ", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            i=1;
                            Toast.makeText(setpattern.this, "Please enter old password again ", Toast.LENGTH_LONG).show();
                        }
                    }
                    else if(i==2)
                    {
                        r2=PatternLockUtils.patternToString(patternLockView, pattern);
                        i++;
                        Toast.makeText(setpattern.this, "Please confirm again", Toast.LENGTH_LONG).show();
                    }

                    else if(i==3)
                    {
                        r3=PatternLockUtils.patternToString(patternLockView, pattern);
                        if(r2.equals(r3))
                        {
                            if(d.updateContact("SPOOR ENORMITY AT UNKNOWN LOCATION",r3))
                            {
                                i=1;
                                Intent i=new Intent( setpattern.this,loginpattern.class);
                                startActivity(i);
                                finish();
                            }


                        }
                        else{
                            i=1;
                            Toast.makeText(setpattern.this, "New password and confirm password should be same,Try again later", Toast.LENGTH_LONG).show();
                        }
                    }
                    //Toast.makeText(setpattern.this, "ooooooooooooooooo", Toast.LENGTH_LONG).show();
                }
                catch(Exception e)
                {
                    if(i==1)
                    {
                        r1=PatternLockUtils.patternToString(patternLockView, pattern);
                        i++;
                        Toast.makeText(setpattern.this, "Please confirm again", Toast.LENGTH_LONG).show();
                    }
                    else if(i==2)
                    {
                        r2=PatternLockUtils.patternToString(patternLockView, pattern);
                        if(r1.equals(r2))
                        {
                            if(d.scanmethod("SPOOR ENORMITY AT UNKNOWN LOCATION",r2))
                            {
                                Intent i=new Intent( setpattern.this,loginpattern.class);
                                startActivity(i);
                                finish();
                            }

                        }
                        else
                        {
                            Toast.makeText(setpattern.this, "Try again later", Toast.LENGTH_LONG).show();
                        }
                        i=1;
                    }
                }
                //Toast.makeText(setpattern.this, "data here"+jp, Toast.LENGTH_LONG).show();
/*
                Log.d(getClass().getName(), "Pattern complete: " +
                        PatternLockUtils.patternToString(patternLockView, pattern));
                if (PatternLockUtils.patternToString(patternLockView, pattern).equalsIgnoreCase("846301257")) {
                    patternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
                    Toast.makeText(setpattern.this, "Welcome back, CodingDemos", Toast.LENGTH_LONG).show();
                } else {
                    patternLockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
                    Toast.makeText(setpattern.this, "Incorrect password"+PatternLockUtils.patternToString(patternLockView, pattern), Toast.LENGTH_LONG).show();
                }*/
            }

            @Override
            public void onCleared() {

            }
        });
    }
}