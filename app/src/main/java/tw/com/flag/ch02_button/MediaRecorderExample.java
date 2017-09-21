package tw.com.flag.ch02_button;

import java.io.File;
import java.io.IOException;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MediaRecorderExample extends Activity {

    private Button recordButn           = null;
    private Button stopButn             = null;
    private Button playButn             = null;
    private MediaRecorder mediaRecorder = null;
    private MediaPlayer mediaPlayer     = null ;

    private MediaPlayer.OnPreparedListener prepareListener=new MediaPlayer.OnPreparedListener() {
        public void onPrepared(MediaPlayer player) {
            playButn.setEnabled(true);
        }
    };

    private MediaPlayer.OnCompletionListener completionListener=new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer player) {
            A.a("Play complete !");
            Toast.makeText(MediaRecorderExample.this,"Play complete !", Toast.LENGTH_LONG).show();
            playButn.setEnabled(true);
            mediaPlayer.release();
            mediaPlayer=null;
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        recordButn = (Button) findViewById(R.id.recordButn);
        stopButn   = (Button) findViewById(R.id.stopButn);
        playButn   = (Button) findViewById(R.id.playButn);

        //錄音
        recordButn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try {

                    mediaRecorder = new MediaRecorder();

                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    mediaRecorder.setOutputFile("/sdcard/mytest.3gp");

                    mediaRecorder.prepare();

                    mediaRecorder.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // Stop Recording
        stopButn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mediaRecorder != null) {
                    A.a();
                    mediaRecorder.stop();
                    mediaRecorder.release();
                    mediaRecorder = null;

                    mediaPlayer=new MediaPlayer();
                    try {
                        mediaPlayer.setDataSource("/sdcard/mytest.3gp");
                    } catch (Exception e) {
                        A.a();
                    }
                    mediaPlayer.setOnPreparedListener(prepareListener);
                    //
                    try {
                        mediaPlayer.prepare();
                    }catch (Exception e) {
                        A.a();
                    }
                }
            }
        });

        // Play recorded file mytest.3gp
        playButn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                A.a();
                mediaPlayer.setOnCompletionListener(completionListener);
                mediaPlayer.setVolume(1.0f,1.0f);

                mediaPlayer.start();
                playButn.setEnabled(false);

            }
        });




    }
}