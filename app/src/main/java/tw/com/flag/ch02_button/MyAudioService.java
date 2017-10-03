package tw.com.flag.ch02_button;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MyAudioService extends Service {

    public static Boolean serviceAudioRunning = false;
    private       MediaRecorder mediaRecorder = null;
    private               Timer         timer = null;
    private      MyQueue<Integer> queueVolume = new MyQueue<Integer>(4);

    public MyAudioService() {
        A.a("in MyAudioService constructor ");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        A.a();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        A.a();

        serviceAudioRunning = true;

        // init queue
        queueVolume.add(0);
        queueVolume.add(0);
        queueVolume.add(0);
        queueVolume.add(0);
        // init mediaRecorder
        if (mediaRecorder == null) {
            mediaRecorder = new MediaRecorder();
        }
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile("/dev/null");

        // init timer
        if (timer == null) {
            timer = new Timer();
        }
        timer.scheduleAtFixedRate(new RecorderTask(mediaRecorder), 0, 50);

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        A.a();

        // stop timer
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        // release media recorder resource
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }

        MyAudioService.serviceAudioRunning = false;

        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
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

    public class RecorderTask extends TimerTask {

        private MediaRecorder recorder = null;
        private          int amplitude = 0;
        private     double amplitudeDb = 0;
        private              int intDb = 0;

        public RecorderTask(MediaRecorder recorder) {
            A.a();
            this.recorder = recorder;
        }

        @Override
        public void run() {
            A.a();
            if (recorder != null) {
                amplitude = recorder.getMaxAmplitude();
                amplitudeDb = 20 * Math.log10((double) Math.abs(amplitude));
                intDb = (int) amplitudeDb;

                if (intDb > 0) {
                    queueVolume.add(intDb);
//                    A.a("intDb = " + intDb  + " my asr service is " + MyASRService.serviceRunning );
                }

                if ((queueVolume.isVolumeLarge() == true) && (MyASRService.serviceRunning == false)) {
                    A.a();

                    MyAudioService.serviceAudioRunning = false;

                    if (mediaRecorder != null) {
                        A.a("22. mediaRecorder = " + mediaRecorder);
                        mediaRecorder.stop();
                        mediaRecorder.release();
                        mediaRecorder = null;
                    }

                    if (MyASRService.serviceRunning == false) {
                        Intent intent = new Intent(MyAudioService.this, MyASRService.class);
                        startService(intent);
                    }

                    // stop timer and release microphone resource
                    timer.cancel();
                    timer = null;

                }
            } else {
                A.a("Media Recorder is null ");
            }
        }
    }


}
