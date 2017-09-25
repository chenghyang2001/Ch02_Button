package tw.com.flag.ch02_button;

import java.io.IOException;
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
    private Button playButn = null;
    private Button exitButn = null;
    private MediaPlayer mediaPlayer = null;
    private MediaRecorder mediaRecorder = null;
    Timer timer;

    private MediaPlayer.OnPreparedListener prepareListener = new MediaPlayer.OnPreparedListener() {
        public void onPrepared(MediaPlayer player) {
            A.a("Play Prepared !");
            Toast.makeText(MediaRecorderExample.this, "Play Prepared !", Toast.LENGTH_LONG).show();
            playButn.setEnabled(true);
        }
    };

    private MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer player) {
            A.a("Play complete !");
            Toast.makeText(MediaRecorderExample.this, "Play complete !", Toast.LENGTH_LONG).show();
//            playButn.setEnabled(true);
            mediaPlayer.release();
            mediaPlayer = null;
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        recoButn = (Button) findViewById(R.id.recordButn);
        stopButn = (Button) findViewById(R.id.stopButn);
        playButn = (Button) findViewById(R.id.playButn);
        exitButn = (Button) findViewById(R.id.exitButn);

        recoButn.setEnabled(true);
        stopButn.setEnabled(false);
        playButn.setEnabled(false);

        // Record Button
        recoButn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try {

                    recoButn.setEnabled(false);
                    stopButn.setEnabled(true);
                    playButn.setEnabled(false);


                    if (mediaRecorder == null) {
                        mediaRecorder = new MediaRecorder();
                    }
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    mediaRecorder.setOutputFile("/sdcard/mytest.3gp");

                    timer = new Timer();
                    timer.scheduleAtFixedRate(new RecorderTask(mediaRecorder), 0, 500);
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

                    timer.cancel(); A.a();

                    recoButn.setEnabled(true); A.a();
                    stopButn.setEnabled(false); A.a();
                    playButn.setEnabled(true); A.a();

                    mediaRecorder.stop(); A.a();
                    mediaRecorder.release(); A.a();
                    mediaRecorder = null; A.a();




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

        // Play Button.
        playButn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                A.a();

                playButn.setEnabled(false);

                if (mediaPlayer != null) {
                    mediaPlayer.setOnCompletionListener(completionListener);
                    mediaPlayer.setVolume(1.0f, 1.0f);
                    mediaPlayer.start();
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

    private class RecorderTask extends TimerTask {

        private MediaRecorder recorder = null ;

        public RecorderTask(MediaRecorder recorder) {
            this.recorder = recorder;
            A.a();
        }

        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if ( recorder != null ) {
                        int amplitude = recorder.getMaxAmplitude();
                        double amplitudeDb = 20 * Math.log10((double) Math.abs(amplitude));
                        A.a("amplitudeDb = " + amplitudeDb);
                    }
                }
            });
        }
    }

}