package tw.com.flag.ch02_button;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MediaRecorderExample extends Activity {

    private Button recoButn               = null;
    private Button stopButn               = null;
    private Button exitButn               = null;
    private MediaPlayer mediaPlayer       = null;
    private MediaRecorder mediaRecorder   = null;
            Timer                   timer = null ;
    LimitedSizeQueue<Integer> queueVolume = new LimitedSizeQueue<Integer>(4);


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        queueVolume.add(0);
        queueVolume.add(0);
        queueVolume.add(0);
        queueVolume.add(0);

        recoButn = (Button) findViewById(R.id.recordButn);
        stopButn = (Button) findViewById(R.id.stopButn);
        exitButn = (Button) findViewById(R.id.exitButn);

        recoButn.setEnabled(true);
        stopButn.setEnabled(false);

        // Record Button
        recoButn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try {

                    recoButn.setEnabled(false);
                    stopButn.setEnabled(true);


                    if (mediaRecorder == null) {
                        mediaRecorder = new MediaRecorder();
                    }
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    mediaRecorder.setOutputFile("/sdcard/mytest.3gp");

                    timer = new Timer();
//                    timer.scheduleAtFixedRate(new RecorderTask(mediaRecorder), 0, 20);
                    timer.scheduleAtFixedRate(new RecorderTask(mediaRecorder), 0, 50);
//                    timer.scheduleAtFixedRate(new RecorderTask(mediaRecorder), 0, 100);
//                    timer.scheduleAtFixedRate(new RecorderTask(mediaRecorder), 0, 500);
                    mediaRecorder.setOutputFile("/dev/null");

                    mediaRecorder.prepare();
                    mediaRecorder.start();

                    A.a();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // Stop Button
        stopButn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mediaRecorder != null) {

                    timer.cancel();

                    recoButn.setEnabled(true);
                    stopButn.setEnabled(false);
                    mediaRecorder.stop();
                    mediaRecorder.release();
                    mediaRecorder = null;

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

    // Start Service Button Event Handler
    public void startService(View view) {

        MyAsyncTask task = new MyAsyncTask();
        task.execute("String 1" ); A.a();

//        if (MyService.serviceRunning == false) {
//            Intent intent = new Intent(MediaRecorderExample.this, MyService.class);
//            startService(intent);
//        }
//


    }

    // Stop Service Button Event Handler
    public void stopService(View view) {

        if ( MyService.serviceRunning == true ) {
            Intent intent = new Intent(MediaRecorderExample.this, MyService.class);
            stopService(intent);
        }

    }

    public class RecorderTask extends TimerTask {

        private MediaRecorder recorder = null;

        public RecorderTask(MediaRecorder recorder) {
            this.recorder = recorder;
            A.a();
        }

        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (recorder != null) {

                        int      amplitude = recorder.getMaxAmplitude();
                        double amplitudeDb = 20 * Math.log10((double) Math.abs(amplitude));
                        int          intDb = (int) amplitudeDb;

                        queueVolume.add(intDb);
//                        queueVolume.display();
//                        A.a( " isVolume Large = " + queueVolume.isVolumeLarge() )  ;
//                        A.a("amplitudeDb = " + amplitudeDb);

                        if ((queueVolume.isVolumeLarge() == true) && (MyService.serviceRunning == false) ) {

                            A.a("111.    isVolumeLarge = true");
                            timer.cancel();

                            if (mediaRecorder != null) {

                                mediaRecorder.stop();
                                mediaRecorder.release();
                                mediaRecorder = null;

                            }

                            MyAsyncTask task = new MyAsyncTask();
                            task.execute("String 1" ); A.a();

//                            if (MyService.serviceRunning == false) {
//                                Intent intent = new Intent(MediaRecorderExample.this, MyService.class);
//                                startService(intent);
//                            }
//

                        }
                    }
                }
            });
        }
    }


    public class LimitedSizeQueue<K> extends ArrayList<K> {

        private int maxSize;

        public LimitedSizeQueue(int size) {
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


    private void testQueue() {

        LimitedSizeQueue<Integer> queueNames = new LimitedSizeQueue<Integer>(3);
        queueNames.add(10);
        queueNames.add(20);
        queueNames.add(30);

        for (int i : queueNames) {
            A.a(" i = " + i);
        }

        queueNames.add(40);
        for (int i : queueNames) {
            A.a(" i = " + i);
        }

        queueNames.add(50);
        for (int i : queueNames) {
            A.a(" i = " + i);
        }
    }
    private void stopMyService() {
        if ((queueVolume.isVolumeLarge() == false) && (MyService.serviceRunning == true) ) {

            A.a("2.    isVolumeLarge = false");
            Intent intent = new Intent(MediaRecorderExample.this, MyService.class);
            stopService(intent);
        }
    }

    private class MyAsyncTask extends AsyncTask<String, String, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            A.a("strings length =" + strings.length );

            if (MyService.serviceRunning == false) {
                Intent intent = new Intent(MediaRecorderExample.this, MyService.class);
                startService(intent);
            }


            return null;
        }




    }


    public static class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            A.a("Action: " + intent.getAction());
        }
    }

}