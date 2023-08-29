package com.sai.sms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.FileNotFoundException;



import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorialspoint.R;

public class imagesharing extends AppCompatActivity implements OnClickListener {
    private static Button selectImage, shareImage;
    private static ImageView imageView;

TextView t1;
    // Uri for image path
    private static Uri imageUri = null;

    private final int select_photo = 1; // request code fot gallery intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image);

        init();
        setListeners();

    }

    // Initialize views
    private void init() {
        selectImage = (Button) findViewById(R.id.select_image);
        shareImage = (Button) findViewById(R.id.share_image);
t1=findViewById(R.id.textr);

        imageView = (ImageView) findViewById(R.id.share_imageview);

        ;
    }

    // Implement click listeners
    private void setListeners() {
        selectImage.setOnClickListener(this);
        shareImage.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.select_image:
                // Intent to gallery
                Intent in = new Intent(Intent.ACTION_PICK);
                in.setType("image/*");
                startActivityForResult(in, select_photo);// start
                // activity
                // for
                // result
                break;
            case R.id.share_image:

                // share image
                shareImage(imageUri);

                break;


        }
    }

    protected void onActivityResult(int requestcode, int resultcode,
                                    Intent imagereturnintent) {
        super.onActivityResult(requestcode, resultcode, imagereturnintent);
        switch (requestcode) {
            case select_photo:
                if (resultcode == RESULT_OK) {
                    try {

                        imageUri = imagereturnintent.getData();// Get intent
                        // data
                         t1.setText(imageUri.toString());
                        Bitmap bitmap = Utils.decodeUri(imagesharing.this, imageUri, 200);// call
                        // deocde
                        // uri
                        // method
                        // Check if bitmap is not null then set image else show
                        // toast
                        if (bitmap != null) {
                            imageView.setImageBitmap(bitmap);// Set image over
                            // bitmap
                            shareImage.setVisibility(View.VISIBLE);// Visible button
                            // if bitmap is
                            // not null
                        } else {
                            shareImage.setVisibility(View.GONE);
                            Toast.makeText(imagesharing.this,
                                    "Error while decoding image.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (FileNotFoundException e) {

                        e.printStackTrace();
                        Toast.makeText(imagesharing.this, "File not found.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
        }
    }

    // Share image
    private void shareImage(Uri imagePath) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        sharingIntent.setType("image/*");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, imagePath);
        startActivity(Intent.createChooser(sharingIntent, "Share Image Using"));
    }


}
