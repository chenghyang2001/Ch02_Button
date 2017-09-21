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
    private Button recordButn;
    private Button stopButn;
    private Button playButn;
    private MediaRecorder mediaRecorder = null;
    private MediaPlayer mediaPlayer = null ;

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
        stopButn = (Button) findViewById(R.id.stopButn);
        playButn = (Button) findViewById(R.id.playButn);

        //錄音
        recordButn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //設定錄音檔名
                String fileName = "record.amr";
                try {
                    File SDCardpath = Environment.getExternalStorageDirectory();
                    File myDataPath = new File( SDCardpath.getAbsolutePath() + "/download" );
                    if( !myDataPath.exists() ) myDataPath.mkdirs();
//                    File recodeFile = new File(SDCardpath.getAbsolutePath() + "/download/"+fileName);
                    File recodeFile = new File(Environment.getExternalStorageDirectory() + "/mytest.3gp");

                    mediaRecorder = new MediaRecorder();

                    //設定音源
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    //設定輸出檔案的格式
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
                    //設定編碼格式
                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    //設定錄音檔位置
//                    mediaRecorder.setOutputFile( Environment.getExternalStorageDirectory() + "/mytest.3gp");
                    mediaRecorder.setOutputFile("/sdcard/mytest.3gp");
//                    mediaRecorder.setOutputFile(recodeFile.getAbsolutePath());

                    mediaRecorder.prepare();
                    A.a("path = " + recodeFile.getAbsolutePath());

                    //開始錄音
                    mediaRecorder.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //停止錄音
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