package com.sai.sms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;


import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorialspoint.R;

import java.util.ArrayList;

public class contact extends AppCompatActivity {
    ListView listView;
    TextView textView;
    String[] listItem;
    public contact_database con=new contact_database(this);
    important_database dd=new important_database(this);
    public static ArrayList a13=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        listView=(ListView)findViewById(R.id.listView);
        //textView=(TextView)findViewById(R.id.textView);
        //listItem = getResources().getStringArray(R.array.array_technology);
        //listItem= new String[]{"hello", "hai"};
        //listItem=
        //a13=dd.getAllContactt();
       try {
            a13 = con.getAllContactt();
        }
        catch(Exception e)
        {
            a13.add("EMPTY");
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, android.R.id.text2, a13);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub

                Bundle b = new Bundle();

                //Inserts a String value into the mapping of this Bundle









                String value=adapter.getItem(position);
                Toast.makeText(getApplicationContext(),value,Toast.LENGTH_SHORT).show();
                b.putString("name", value);
                Intent i=new Intent(contact.this,showeachcontact.class);
                i.putExtras(b);
                startActivity(i);
                //con.deleteContact(value);    //remove code

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.addcontact,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.addcontact:
                /*String[] s = { "India ", "Arica", "India ", "Arica", "India ", "Arica",
                        "India ", "Arica", "India ", "Arica" };
                final ArrayAdapter<String> adp = new ArrayAdapter<String>(contact.this, android.R.layout.simple_spinner_item, s);
                final Spinner sp = new Spinner(contact.this);
                sp.setAdapter(adp);
                layout.addView(sp);*/



                androidx.appcompat.app.AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("CONTACT");
                alertDialog.setMessage("ADDITING CONTACT");

                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.VERTICAL);

                // Add a TextView here for the "Title" label, as noted in the comments
                final EditText username = new EditText(this);
                username.setHint("Name");
                layout.addView(username); // Notice this is an add method

                // Add another TextView here for the "Description" label
                final EditText phone = new EditText(this);
                phone.setHint("Phone Number");
                phone.setInputType(InputType.TYPE_CLASS_NUMBER);
                layout.addView(phone); // Another add method

                alertDialog.setView(layout);
                /*final EditText input = new EditText(MainActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);

                alertDialog.setView(input);
                //alertDialog.setIcon(R.drawable.key);*/

                alertDialog.setPositiveButton("ADD",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                             String k=username.getText().toString();
                                String kk=phone.getText().toString();
                                if(!k.isEmpty() && kk.length()==10) {

                                    con.scanmethodt(k, kk);
                                    Toast.makeText(contact.this, "ADDED", Toast.LENGTH_SHORT).show();
                                    Intent i=new Intent( contact.this,contact.class);
                                    startActivity(i);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(contact.this, "Sorry not saved", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                alertDialog.setNegativeButton("DISMISS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();


                return true;



        }
        return super.onOptionsItemSelected(item);

    }

    public void onDestroy() {

        super.onDestroy();

    }

}