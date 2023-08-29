package com.sai.sms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Handler;
import com.bumptech.glide.Glide;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.tutorialspoint.R;

public class screenout extends AppCompatActivity {
    public String get="";
    logdatabase d=new logdatabase(this);
    private static int SPLASH_SCREEN_TIME_OUT=2000;
    //#After completion of 1000 ms, the next activity will get started.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            get = d.getContact("CHECK");
        }
        catch(Exception e)
        {
            d.scanmethod("CHECK","PATTERN");
            get="PATTERN";
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //This method is used so that your splash activity
        //can cover the entire screen.

        setContentView(R.layout.activity_screenout);
        //this will bind your MainActivity.class file with activity_main.
        //sai();
        startService(new Intent(getApplicationContext(), LockService.class));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(get.equals("PATTERN")) {
                    Intent i = new Intent(screenout.this, loginpattern.class);
                    //Intent is used to switch from one activity to another.

                    startActivity(i);
                    //invoke the SecondActivity.
                }
                else if(get.equals("PIN"))
                {

                    Intent i = new Intent(screenout.this, pinlock.class);
                    //Intent is used to switch from one activity to another.

                    startActivity(i);
                }                finish();
                //the current activity will get finished.
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
    /*public void sai() {
        ImageView imageView = findViewById(R.id.imageView);
        Glide.with(screenout.this).load(R.drawable.saigif).into(imageView);
    }

     */
}
