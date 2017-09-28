package tw.com.flag.ch02_button;


import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MyAudioService extends Service {

    private MediaRecorder mediaRecorder = null;
    private Timer timer = null;
    private MyQueue2<Integer> queueVolume = new MyQueue2<Integer>(4);

    public class LocalBinder extends Binder {
        MyAudioService getService() {
            return MyAudioService.this;
        }
    }

    private LocalBinder mLocBin = new LocalBinder();


    public MyAudioService() {
        A.a("in MyAudioService constructor ");


    }

    @Override
    public IBinder onBind(Intent intent) {
        A.a();
        return mLocBin;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        A.a();
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        A.a();
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


    }

    @Override
    public void onDestroy() {
        A.a();

        // stop timer
        if (timer != null) {
            timer.cancel();
        }

        // release media recorder resource
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }

        super.onDestroy();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        A.a();
        // start timer interval task
        timer.scheduleAtFixedRate(new RecorderTask(mediaRecorder), 0, 50);
        //
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ComponentName startService(Intent service) {
        A.a();
        return super.startService(service);
    }

    @Override
    public boolean stopService(Intent name) {
        A.a();
        return super.stopService(name);
    }

    public class MyQueue2<K> extends ArrayList<K> {

        private int maxSize;

        public MyQueue2(int size) {
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
        private int amplitude = 0;
        private double amplitudeDb = 0;
        private int intDb = 0;

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
                        Intent intent = new Intent(MyAudioService.this, MyService.class);
                        startService(intent);
                    }

                }
            } else {
                A.a("Media Recorder is null ");
            }


        }
    }


}
