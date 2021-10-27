package com.android_project.qrscanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
    //Initializing variable
    Button btScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assigning variable
        btScan = findViewById(R.id.bt_scan);

        btScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Initializing intent integrator. IntenetIntegrator is a utility class
                IntentIntegrator intentIntegrator = new IntentIntegrator(
                       MainActivity.this

                );
                //Setting prompt text
                intentIntegrator.setPrompt("For flashlight, use volume up key");
                //Sets beep sound
                intentIntegrator.setBeepEnabled(true);
                //Locking display orientation
                intentIntegrator.setOrientationLocked(true);
                //Set capture activity
                intentIntegrator.setCaptureActivity(Capture.class);
                //Initiate scan
                intentIntegrator.initiateScan();

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Initialize intent result
        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode, resultCode,data
        );
        //check condition
        if (intentResult.getContents() != null){
            //When result content is not null
            //Initialize alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    MainActivity.this
            );
            //Set title
            builder.setTitle("Scan Result");
            //Set message
            builder.setMessage(intentResult.getContents());
            //Set positive scan result button
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                   //Dismiss dialog
                   dialogInterface.dismiss();
                }
            });
            //Show alert dialog
            builder.show();
        }else{
            //When  result content is null
            //Display toast
            Toast.makeText(getApplicationContext(), "Error! Image not scanned", Toast.LENGTH_SHORT ).show();
        }

    }
}