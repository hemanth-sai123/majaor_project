package com.sai.sms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorialspoint.R;

public class delete extends AppCompatActivity {
    Button b1;
    TextView t1;
    EditText e1;
    database d=new database(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        b1=findViewById(R.id.deletephone);
        e1=findViewById(R.id.editdelete);
        t1=findViewById(R.id.textt);
        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                int l=e1.length();
                if(l==10)
                {
                    d.deleteContact(e1.getText().toString());
                    Toast.makeText(delete.this, "DELETED YOUR PHONE NUMBER", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(delete.this, "Phone number consist of 10 digits", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }
}
