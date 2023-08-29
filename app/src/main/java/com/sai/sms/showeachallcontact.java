package com.sai.sms;



import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorialspoint.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class showeachallcontact extends AppCompatActivity {
    public All_contacts cont=new All_contacts(this);
    String phonenum="";
    public static String user="";
    private static final int CAMERA_REQUEST = 1888;
    ImageView imageView;
    TextView t23;
    public static int i=0;
    FileOutputStream out;
    All_ImageHelper help=new All_ImageHelper(this);
    private static Uri imageUri = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showeachcontact);
        Bundle b = getIntent().getExtras();
        //TextView name = (TextView) findViewById(R.id.usernametext);
        TextView phone = (TextView) findViewById(R.id.phonetext);
        Button edit = (Button) findViewById(R.id.editcon);
        Button add = (Button) findViewById(R.id.addimage);
        Button delete = (Button) findViewById(R.id.deletecon);
        imageView=findViewById(R.id.imag);
        user=b.getCharSequence("name").toString();
        //name.setText("Nmae:-   "+user);
        try {
            phonenum= cont.getphonebyname(user);
        }
        catch(Exception e)
        {
            Intent i=new Intent( showeachallcontact.this,all_contactclass.class);
            startActivity(i);
            finish();
        }
        try{

            Bitmap bip=help.getContact(phonenum);
            if(bip!=null) {
                imageView.setImageBitmap(bip);
                add.setText("EDIT IMAGE");
                i++;
            }
            else
            {

                add.setText("ADD IMAGE");
                //Toast.makeText(this, "not ", Toast.LENGTH_SHORT).show();
            }

        }
        catch(Exception e)
        {

        }




        phone.setText("PHONE:-   "+phonenum);

        edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                androidx.appcompat.app.AlertDialog.Builder alertDialog = new AlertDialog.Builder(showeachallcontact.this);
                alertDialog.setTitle("CONTACT");
                alertDialog.setMessage("EDIING CONTACT");

                LinearLayout layout = new LinearLayout(showeachallcontact.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                // Add a TextView here for the "Title" label, as noted in the comments
                final EditText username = new EditText(showeachallcontact.this);
                username.setHint("Name");
                username.setText(user);
                layout.addView(username); // Notice this is an add method

                // Add another TextView here for the "Description" label
                final EditText phone = new EditText(showeachallcontact.this);
                phone.setHint("Phone Number");
                phone.setText(phonenum);
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

                alertDialog.setPositiveButton("EDIT",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String k=username.getText().toString();
                                String kk=phone.getText().toString();
                                if(!k.isEmpty() && kk.length()==10) {

                                    cont.updateContact(user,k,kk);
                                    Toast.makeText(showeachallcontact.this, "Contact saved", Toast.LENGTH_SHORT).show();
                                    Intent icontact=new Intent( showeachallcontact.this,showeachallcontact.class);



                                    Bundle bb = new Bundle();
                                    String valuee=k;
                                    //Toast.makeText(getApplicationContext(),value,Toast.LENGTH_SHORT).show();
                                    bb.putString("name", valuee);
                                    //Intent i=new Intent(contact.this,showeachcontact.class);
                                    icontact.putExtras(bb);
                                    startActivity(icontact);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(showeachallcontact.this, "Sorry not saved", Toast.LENGTH_SHORT).show();
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




            }
        });

        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {




                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(showeachallcontact.this);
                alertDialogBuilder.setMessage("Are you sure, You wanted to Delete?");
                alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        cont.deleteContact(user);
                        help.deleteContact(phonenum);
                        Toast.makeText(showeachallcontact.this, "Deleted", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent( showeachallcontact.this,all_contactclass.class);
                        startActivity(i);
                        finish();
                    }
                });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();



            }
        });



        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (ActivityCompat.checkSelfPermission(showeachallcontact.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(showeachallcontact.this,new String[]{Manifest.permission.CAMERA}, 1);
                }
                else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }




            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST) {
            try {
                //imageUri = "m";
                imageUri = data.getData();
                //Log.e("LOB","wasScreenOn"+imageUri.toString());


                Bitmap photo = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                if (i > 0) {
                    help.deleteContact(phonenum);
                }
                help.scanmethod(phonenum, byteArray);
            /*File f= Environment.getDownloadCacheDirectory();
            File fff=Environment.getRootDirectory();

            File dir=new File(getExternalFilesDir("hellop").toString());
            dir.mkdir();
            long ml=System.currentTimeMillis();
            String k=getExternalFilesDir("hellop").toString()+"/"+ml+".jpg";


            File file=new File(dir,ml+".jpg");
            byte[] image= new byte[(int) file.length()];
            //help.scanmethod("helloll",image); */
                Bitmap b = help.getContact(phonenum);


                //imageView.setImageBitmap(b);
                Intent icontat=new Intent( showeachallcontact.this,showeachallcontact.class);



                Bundle bbb = new Bundle();
                String valuere=user;
                //Toast.makeText(getApplicationContext(),value,Toast.LENGTH_SHORT).show();
                bbb.putString("name", valuere);
                //Intent i=new Intent(contact.this,showeachcontact.class);
                icontat.putExtras(bbb);
                startActivity(icontat);
                finish();
            }
            catch(Exception e)
            {
                Toast.makeText(this, "Camera cancelled", Toast.LENGTH_SHORT).show();
            }

        }
    }

}
