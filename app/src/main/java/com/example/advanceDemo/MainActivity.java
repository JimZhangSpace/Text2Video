package com.example.advanceDemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.advanceDemo.recorderUtils.AudioRecorderUtils;
import com.lansoeditor.advanceDemo.R;

public class MainActivity extends AppCompatActivity implements AudioRecorderUtils.OnAudioStatusUpdateListener{

    private Context mContext;
    private AudioRecorderUtils audioRecorderUtils = new AudioRecorderUtils();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main2);

        audioRecorderUtils.setOnAudioStatusUpdateListener(this);

        findViewById(R.id.record_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                audioRecorderUtils.startRecord(mContext);

            }
        });

        findViewById(R.id.stop_record).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                audioRecorderUtils.stopRecord();

            }
        });

    }

    @Override
    public void onUpdate(double db, long time) {
        Log.i("Tag","onUpdate");
    }

    @Override
    public void onStop(long time, String filePath) {
        Log.i("Tag","onStop");
    }

    @Override
    public void onError() {
        Log.i("Tag","onError");
    }

}
