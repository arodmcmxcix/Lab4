package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button startButton;
    private TextView downloadText;
    private volatile  boolean stopThread  = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);
        downloadText = findViewById(R.id.textView);
    }

    public void mockFileDownloader(){
        // Queue the mock downloader
        runOnUiThread(new Runnable(){
            @Override
            public void run() {
                startButton.setText("Downloading...");

            }
        });

        for(int downloadProgress = 0; downloadProgress <= 100; downloadProgress += 10) {
            if(stopThread) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startButton.setText("Start");
                        downloadText.setText("");
                    }
                });
                return;
            }

            Log.d(TAG, "Download Progress: " + downloadProgress + "%");
            int finalDownloadProgress = downloadProgress;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    downloadText.setText("Download Progress: " + finalDownloadProgress + "%");
                }
            });
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        runOnUiThread(new Runnable(){
            @Override
            public void run() {
                startButton.setText("Start");
                downloadText.setText("");
            }
        });



    }

    public void startDownload(View view){
        stopThread = false;
        ExampleRunnable runnable =new ExampleRunnable();
        new Thread(runnable).start();
    }

    public void stopDownload(View view){
        stopThread = true;

    }

    public class ExampleRunnable implements Runnable{
        @Override
        public void run() {
            // Change the text
            mockFileDownloader();
        }
    }
}