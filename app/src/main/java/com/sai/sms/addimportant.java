package com.sai.sms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorialspoint.R;

import java.util.ArrayList;

public class addimportant extends AppCompatActivity {
Button b1;
    TextView t1;
    EditText e1;
    important_database d=new important_database(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addimportant);
        b1=findViewById(R.id.addphonei);
        e1=findViewById(R.id.editaddi);
        t1=findViewById(R.id.texti);
        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int l=e1.length();
                if(l==10)
                {
                    d.scanmethodt(e1.getText().toString());

                }
                else
                {
                    Toast.makeText(addimportant.this, "Phone number consist of 10 digits", Toast.LENGTH_SHORT).show();
                }

                String s1="";
                ArrayList a1=d.getAllContactt();
                for(Object str:a1) {
                    s1=s1+(String)str;

                }

                t1.setText(s1);





            }
        });
    }
}
