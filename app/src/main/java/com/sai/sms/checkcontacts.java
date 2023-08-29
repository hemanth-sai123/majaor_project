package com.sai.sms;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;


import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorialspoint.R;

import java.util.ArrayList;

public class checkcontacts extends AppCompatActivity {
    ListView listView;
    TextView text;
    String[] listItem;
    public checkdatabase con=new checkdatabase(this);
    important_database dd=new important_database(this);
    logdatabase dc=new logdatabase(this);
    public static ArrayList a13=new ArrayList();
    public ArrayList string=new ArrayList();
    String get="";
    public int i=0;

    public ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        text=findViewById(R.id.textcol);
        listView=(ListView)findViewById(R.id.listView);

        //textView=(TextView)findViewById(R.id.textView);
        //listItem = getResources().getStringArray(R.array.array_technology);
        //listItem= new String[]{"hello", "hai"};
        //listItem=
        //a13=dd.getAllContactt();
        try {

            get = dc.getContact("SETCONTACTS");
            Toast.makeText(checkcontacts.this, get, Toast.LENGTH_SHORT).show();

        }
        catch(Exception e)
        {
            dc.scanmethod("SETCONTACTS","ALL_CONTACTS");
            get="ALL_CONTACTS";
        }


        try {
            if (get.equals("ALL_CONTACTS")) {
                a13 = con.getAllContactt();
                text.setText("All contacts");
                   ////to get phone number of impotrant contacts
            }
            else if(get.equals("IMPORTANT_CONTACTS"))
            {
                //a13.add("hello");
                a13 = con.getphone("IMPORTANT_CONTACTS");
                text.setText("Important contacts");
            }
            else if(get.equals("POLICE_CONTACTS"))
            {
                a13 = con.getphone("POLICE_CONTACTS");
                text.setText("Police contacts");
            }
            else if(get.equals("OTHERS"))
            {
                a13 = con.getphone("OTHERS");
                text.setText("Other contacts");
            }

        }
        catch(Exception e)
        {
            a13.add("EMPTY");
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, android.R.id.text2, a13);
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
                Intent i=new Intent(checkcontacts.this,showeachcheckcontact.class);
                i.putExtras(b);
                startActivity(i);
                //con.deleteContact(value);    //remove code

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.checkcontacts,menu);
        MenuItem item=menu.findItem(R.id.searc);
        SearchView sear= (SearchView) item.getActionView();
        sear.setQueryHint("Type to search");

        sear.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.addcontact:
                i++;
                if(i==1) {
                    if (get.equals("ALL_CONTACTS")) {
                        //a13 = con.getAllContactt();
                        string.add("IMPORTANT_CONTACTS");
                        string.add("POLICE_CONTACTS");
                        string.add("OTHERS");

                        //String[] s = { "IMPORTANT_CONTACTS","POLICE_CONTACTS" ,"OTHERS"};
                        ////to get phone number of impotrant contacts
                    } else if (get.equals("IMPORTANT_CONTACTS")) {
                        //a13.add("hello");
                        string.add("IMPORTANT_CONTACTS");
                        //a13 = con.getphone("IMPORTANT_CONTACTS");
                    } else if (get.equals("POLICE_CONTACTS")) {
                        string.add("POLICE_CONTACTS");
                    } else if (get.equals("OTHERS")) {
                        string.add("OTHERS");
                    }
                }
                final ArrayAdapter<String> adp = new ArrayAdapter<String>(checkcontacts.this, android.R.layout.simple_spinner_item, string);
                final Spinner sp = new Spinner(checkcontacts.this);




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
                sp.setAdapter(adp);
                layout.addView(sp);

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
                                String kkk=sp.getSelectedItem().toString();
                                if(!k.isEmpty() && kk.length()==10) {
                                   try {
                                       String m = con.getnamebypho(kk);
                                       //String mn = con.getphobyname(k);
                                       Toast.makeText(checkcontacts.this, "Sorry,Number already saved as '"+m+"'", Toast.LENGTH_LONG).show();
                                       //String mn=con.getnamebypho(k);
                                   }
                                    catch(Exception e)
                                    {    int km;
                                        try {
                                            km = con.getcount("IMPORTANT_CONTACTS");
                                        }
                                        catch (Exception el)
                                        {
                                          km=0;
                                        }
                                        if(km>=10 && kkk.equals("IMPORTANT_CONTACTS"))
                                        {
                                            Toast.makeText(checkcontacts.this, "Morethan ten contacts cannot saved", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            con.scanmethodt(k, kk, kkk);
                                            Toast.makeText(checkcontacts.this, kkk, Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(checkcontacts.this, checkcontacts.class);
                                            startActivity(i);
                                            finish();
                                        }

                                    }
                                }
                                else
                                {
                                    Toast.makeText(checkcontacts.this, "Sorry not saved", Toast.LENGTH_SHORT).show();
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
            case R.id.alcontacts:
                //Toast.makeText(this,"add all",Toast.LENGTH_SHORT).show();
                 //= dc.getContact("SETCONTACTS");
                dc.updateContact("SETCONTACTS","ALL_CONTACTS");
                Intent ip=new Intent( checkcontacts.this,checkcontacts.class);
                startActivity(ip);
                finish();
                return true;
            case R.id.important:
                //Toast.makeText(this,"add all",Toast.LENGTH_SHORT).show();
                //= dc.getContact("SETCONTACTS");
                dc.updateContact("SETCONTACTS","IMPORTANT_CONTACTS");
                Intent im=new Intent( checkcontacts.this,checkcontacts.class);
                startActivity(im);
                finish();
                return true;
            case R.id.other:
                //Toast.makeText(this,"add all",Toast.LENGTH_SHORT).show();
                //= dc.getContact("SETCONTACTS");
                dc.updateContact("SETCONTACTS","OTHERS");
                Intent o=new Intent( checkcontacts.this,checkcontacts.class);
                startActivity(o);
                finish();
                return true;
            case R.id.police:
                //Toast.makeText(this,"add all",Toast.LENGTH_SHORT).show();
                //= dc.getContact("SETCONTACTS");
                dc.updateContact("SETCONTACTS","POLICE_CONTACTS");
                Intent p=new Intent( checkcontacts.this,checkcontacts.class);
                startActivity(p);
                finish();
                return true;




        }
        return super.onOptionsItemSelected(item);

    }

    public void onDestroy() {

        super.onDestroy();

    }

}