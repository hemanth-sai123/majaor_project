package com.sai.sms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tutorialspoint.R;

public class pinsetall extends AppCompatActivity {
    EditText e1,e2,e3;
    Button b1;
    String getr="";
    public static int i=0;
    logdatabase d=new logdatabase(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinsetall);
        e1=findViewById(R.id.oldpin);
        e2=findViewById(R.id.newpin);
        e3=findViewById(R.id.conpin);
        b1=findViewById(R.id.butpin);
        try{
            getr = d.getContact("PINDATA");
            e1.setVisibility(View.VISIBLE);
            b1.setText("UPDATE");
            i=1;
        }
        catch(Exception e){
            b1.setText("SET");
            e1.setVisibility(View.INVISIBLE);
            i=0;
        }
        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String  e22 = e2.getText().toString();
                String  e23 = e3.getText().toString();
              if(i==1)
              {



                if (getr.equals(e1.getText().toString())) {
                    if ((e2.getText().toString()).equals(e3.getText().toString())) {

                        if(!e22.isEmpty() && !e23.isEmpty()) {
                            d.updateContact("PINDATA", e2.getText().toString());
                            //d.scanmethod("PINDATA",e1.getText().toString());
                            Intent i = new Intent(pinsetall.this, pinlock.class);
                            startActivity(i);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(pinsetall.this, "pin and confirm is empty", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(pinsetall.this, "pin and confirm should be same", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(pinsetall.this, "Wrong password ,please try again later", Toast.LENGTH_LONG).show();
                }
            }
            else
            {

                if(((e2.getText().toString()).equals(e3.getText().toString())))
                {
                    if(!e22.isEmpty() && !e23.isEmpty()) {
                        d.scanmethod("PINDATA", e2.getText().toString());
                        Intent i = new Intent(pinsetall.this, pinlock.class);
                        startActivity(i);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(pinsetall.this, "Entry are empty", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(pinsetall.this, "pin and confirm should be same", Toast.LENGTH_LONG).show();
                }
            }


            }
        });
    }
}
