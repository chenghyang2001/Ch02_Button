package tw.com.flag.ch02_button;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MediaRecorderExample extends Activity {

    private Button recoButn = null;
    private Button stopButn = null;
    private Button exitButn = null;
           Context mContext = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // init UI component
        recoButn = (Button) findViewById(R.id.recordButn);
        stopButn = (Button) findViewById(R.id.stopButn);
        exitButn = (Button) findViewById(R.id.exitButn);
        recoButn.setEnabled(true);
        stopButn.setEnabled(false);

        mContext = getApplicationContext();

        A.a();

        // Record Button
        recoButn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                recoButn.setEnabled(false);
                stopButn.setEnabled(true);

                Intent it = new Intent(mContext, MyAudioService.class);
                mContext.startService(it);

            }
        });

        // Stop Button
        stopButn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // adjust UI
                recoButn.setEnabled(true);
                stopButn.setEnabled(false);

                Intent it = new Intent(mContext, MyAudioService.class);
                mContext.stopService(it);

            }
        });

        // Exit Button
        exitButn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                A.a();
                finish();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}