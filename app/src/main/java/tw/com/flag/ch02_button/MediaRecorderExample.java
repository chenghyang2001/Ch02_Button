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
    private MediaRecorder mediaRecorder = null;

    private Timer timer = null;
    private MyQueue<Integer> queueVolume = new MyQueue<Integer>(4);
    Context mContext = null ;



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


        // init queue
        queueVolume.add(0);
        queueVolume.add(0);
        queueVolume.add(0);
        queueVolume.add(0);

        // init timer
        if (timer == null) {
            timer = new Timer();
        }


        // init mediaRecorder
        if (mediaRecorder == null) {
            mediaRecorder = new MediaRecorder();
        }
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile("/dev/null");


        A.a();

        // Record Button
        recoButn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

//                try {
                    // adjust UI
                    recoButn.setEnabled(false);
                    stopButn.setEnabled(true);

                    Intent it = new Intent(mContext, MyAudioService.class);
                    mContext.startService(it);

//                    // start timer interval task
//                    timer.scheduleAtFixedRate(new RecorderTask(mediaRecorder), 0, 50);
//                    //
//                    mediaRecorder.prepare();
//                    mediaRecorder.start();

//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

            }
        });

        // Stop Button
        stopButn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mediaRecorder != null) {

                    Intent it = new Intent(mContext, MyAudioService.class);
                    mContext.stopService(it);

//                    // stop timer
//                    timer.cancel();
//
//                    // release media recorder resource
//                    mediaRecorder.stop();
//                    mediaRecorder.release();
//                    mediaRecorder = null;

                    // adjust UI
                    recoButn.setEnabled(true);
                    stopButn.setEnabled(false);

                }
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

    public class RecorderTask extends TimerTask {

        private MediaRecorder recorder = null;
        private int          amplitude = 0;
        private double     amplitudeDb = 0;
        private int              intDb = 0;

        public RecorderTask(MediaRecorder recorder) {
            A.a();
            this.recorder = recorder;
        }

        public void run() {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (recorder != null) {
                        amplitude   = recorder.getMaxAmplitude();
                        amplitudeDb = 20 * Math.log10((double) Math.abs(amplitude));
                        intDb       = (int) amplitudeDb;
                        queueVolume.add(intDb);

                        if ((queueVolume.isVolumeLarge() == true) && (MyService.serviceRunning == false)) {
                            A.a();
                            // stop timer and release microphone resource
                            timer.cancel();
                            if (mediaRecorder != null) {
                                mediaRecorder.stop();
                                mediaRecorder.release();
                                mediaRecorder = null;
                            }

                            if (MyService.serviceRunning == false) {
                                Intent intent = new Intent(MediaRecorderExample.this, MyService.class);
                                startService(intent);
                            }

                        }
                    } else {
                        A.a("Media Recorder is null ");
                    }
                }
            });
        }
    }

    public class MyQueue<K> extends ArrayList<K> {

        private int maxSize;

        public MyQueue(int size) {
            this.maxSize = size;
        }

        public boolean add(K k) {
            boolean r = super.add(k);
            if (size() > maxSize) {
                removeRange(0, 1);
            }
            return r;
        }

        public boolean isVolumeLarge() {
            boolean r = true;
            int v = 0;
            for (int i = 0; i < maxSize; i++) {
                v = (Integer) get(i);
                if (v < 50) {
                    r = false;
                    break;
                }
            }
            return r;
        }

        public void display() {
            int i = 0;
            StringBuilder builder = new StringBuilder();
            for (i = 0; i < maxSize; i++) {
                builder.append("  " + get(i));
            }
            A.a("builder= " + builder.toString());
        }

        public K getYongest() {
            return get(size() - 1);
        }

        public K getOldest() {
            return get(0);
       }
    }



}