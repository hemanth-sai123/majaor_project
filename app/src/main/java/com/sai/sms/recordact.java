package com.sai.sms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;

import java.io.File;
import java.io.IOException;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tutorialspoint.R;

public class recordact extends Activity {
    MediaRecorder recorder;
    File audiofile = null;
    static final String TAG = "MediaRecording";
    Button startButton,stopButton;
    public long ml;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordact);
        startButton = (Button) findViewById(R.id.button1);
        stopButton = (Button) findViewById(R.id.button2);
    }

    public void startRecording(View view) throws IOException {
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
        //Creating file
        File dir = new File(getExternalFilesDir("audio").toString());
        ml= System.currentTimeMillis();
        //String k = "/storage/emulated/0/DCIM/" + ml + ".jpg";

        //File dir=new File("~/m/#q=");
        //dir.mkdir();
        File file = new File(dir, ml + ".mp3");
       /* try {
            audiofile = File.createTempFile("sound", ".3gp", dir);
        } catch (IOException e) {
            Log.e(TAG, "external storage access error");
            return;
        }*/
        //Creating MediaRecorder and specifying audio source, output format, encoder & output format
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(file.getAbsolutePath());
        recorder.prepare();
        recorder.start();
    }

    public void stopRecording(View view) {
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        //stopping recorder
        recorder.stop();
        recorder.release();
        //after stopping the recorder, create the sound file and add it to media library.
        addRecordingToMediaLibrary();
    }

    protected void addRecordingToMediaLibrary() {
        String k=getExternalFilesDir("audio").toString()+"/"+ml+".mp3";
        Uri urip = Uri.parse(k);
        //t23.setText(fff.toString());
        //t23.setText(urip.toString());
        shareaudio(urip);
    }
    private void shareaudio(Uri imagePath) {
        String s1="";
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        sharingIntent.setType("audio/*");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, imagePath);
        startActivity(Intent.createChooser(sharingIntent, "Share Audio Using"));
    }
}