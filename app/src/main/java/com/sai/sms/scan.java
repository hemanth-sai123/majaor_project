package com.sai.sms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tutorialspoint.R;

public class scan extends AppCompatActivity {
Button b1,b2;
TextView name1;
    DatabaseHandler db = new DatabaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        b1=findViewById(R.id.scanc);
        name1=findViewById(R.id.text);
        b2=findViewById(R.id.retrive);


        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i=new Intent( scan.this,barcode.class);
                startActivity(i);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            //String k=db.getAllContacts();
                //name1.setText(k);
            }
        });
    }
}
