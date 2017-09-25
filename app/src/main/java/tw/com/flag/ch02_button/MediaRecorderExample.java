package tw.com.flag.ch02_button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MediaRecorderExample extends Activity {

    private Button recoButn = null;
    private Button stopButn = null;
    private Button exitButn = null;
    private MediaPlayer mediaPlayer = null;
    private MediaRecorder mediaRecorder = null;
    Timer timer;
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
                    timer.scheduleAtFixedRate(new RecorderTask(mediaRecorder), 0, 100);
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
                    A.a();

                    timer.cancel();
                    A.a();

                    recoButn.setEnabled(true);
                    A.a();
                    stopButn.setEnabled(false);
                    A.a();

                    mediaRecorder.stop();
                    A.a();
                    mediaRecorder.release();
                    A.a();
                    mediaRecorder = null;
                    A.a();


//                    mediaPlayer = new MediaPlayer();
//                    try {
//                        mediaPlayer.setDataSource("/sdcard/mytest.3gp");
//                        mediaPlayer.setOnPreparedListener(prepareListener);
//                        mediaPlayer.prepare();
//                    } catch (Exception e) {
//                        A.a();
//                    }

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


        // test Queue >>
//        testQueue();
        // test Queue <<

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
                        int amplitude = recorder.getMaxAmplitude();
                        double amplitudeDb = 20 * Math.log10((double) Math.abs(amplitude));
                        int intDb = (int) amplitudeDb;
                        queueVolume.add(intDb);
//                        queueVolume.display();

//                        A.a( " isVolume Large = " + queueVolume.isVolumeLarge() )  ;



                        if (queueVolume.isVolumeLarge() == true) {

                            A.a( "xxxxxx.    isVolumeLarge = true" );
                        }

//                        A.a("amplitudeDb = " + amplitudeDb);


                    }
                }
            });
        }
    }

    public class LimitedSizeQueue<K> extends ArrayList<K> {

        private int maxSize;

        public LimitedSizeQueue(int size){
            this.maxSize = size;
        }

        public boolean add(K k){
            boolean r = super.add(k);
            if (size() > maxSize){
                removeRange(0,1);
            }
            return r;
        }

        public boolean isVolumeLarge() {
            boolean r = true ;
            int v = 0 ;
            for ( int i = 0 ; i < maxSize ; i++ ) {
                  v = (Integer) get(i);
                 if ( v < 50 ) {
                     r = false;
                     break;
                 }
            }
            return r ;
        }

        public void display ( ) {
            int i = 0 ;
            StringBuilder builder = new StringBuilder();
            for ( i = 0 ; i < maxSize ; i++ ) {
                builder.append("  " + get(i) );
            }
            A.a(  "builder= " + builder.toString() );
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


        for (int i :queueNames) {
            A.a( " i = " + i ) ;
        }

        queueNames.add(40);
        for (int i :queueNames) {
            A.a( " i = " + i ) ;
        }

        queueNames.add(50);
        for (int i :queueNames) {
            A.a( " i = " + i ) ;
        }
    }


}