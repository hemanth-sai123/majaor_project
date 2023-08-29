package com.sai.sms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.bumptech.glide.Glide;
import com.example.tutorialspoint.R;

import java.util.ArrayList;

public class pinlock extends AppCompatActivity {
Button b1,b2;
EditText e1;
    logdatabase d=new logdatabase(this);

    public String getdata="";
    public ArrayList a2382=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinlock);
        b1=findViewById(R.id.pinlogin);
        b2=findViewById(R.id.pinset);
        e1=findViewById(R.id.pin);                          //234
        Sho();
        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Sho();
                int po=0;
                try {
                    getdata = d.getContact("PINDATA");
                    if(getdata.equals(e1.getText().toString())) {
                        Intent i = new Intent(pinlock.this,MainActivity.class);
                        startActivity(i);
                    }
                    else{
                        Toast.makeText(pinlock.this, "Wrong password", Toast.LENGTH_LONG).show();
                        Show();
                    }
                    Sho();

                }
                catch(Exception e)
                {
                    Toast.makeText(pinlock.this, "Please set your password", Toast.LENGTH_LONG).show();

                }
                //Intent i=new Intent( pinlock.this,loginpattern.class);
                //startActivity(i);

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {


                Intent i=new Intent( pinlock.this,pinsetall.class);
                startActivity(i);
                /*try {
                    getdata = d.getContact("PINDATA");
                    Intent i=new Intent( pinlock.this,pinupdate.class);
                    startActivity(i);

                }
                catch(Exception e)
                {

                    Intent i=new Intent( pinlock.this,setpin.class);
                    startActivity(i);
                }*/

                //Intent i=new Intent( pinlock.this,loginpattern.class);
                //startActivity(i);

            }
        });
    }
    public void Sho() {
        ImageView imageView = findViewById(R.id.ima);
        Glide.with(pinlock.this).load(R.drawable.login).into(imageView);
    }
    public void Show() {
        ImageView imageView = findViewById(R.id.ima);
        Glide.with(pinlock.this).load(R.drawable.imaget).into(imageView);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.pattern,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.aboutp:

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
            case R.id.PINp:
                d.updateContact("CHECK","PATTERN");
                Intent i=new Intent(pinlock.this,loginpattern.class);
                startActivity(i);
                finish();



                return true;
            case R.id.userman:
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
