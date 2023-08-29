package com.sai.sms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tutorialspoint.R;

public class important_contact extends AppCompatActivity {
Button b1,b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_important_contact);
        b1=findViewById(R.id.addimport);
        b2=findViewById(R.id.deleteimport);
        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i=new Intent( important_contact.this,addimportant.class);
                startActivity(i);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i=new Intent( important_contact.this,deleteimportant.class);
                startActivity(i);
            }
        });
    }
}
