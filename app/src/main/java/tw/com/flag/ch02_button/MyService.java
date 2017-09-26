package tw.com.flag.ch02_button;


import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.Date;

public class MyService extends Service {

    public static Boolean serviceRunning = false;

    public MyService() {
        A.a();
    }

    private Handler handler = new Handler();

    @Override
    public IBinder onBind(Intent intent) {
        A.a();
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        A.a();
        serviceRunning = true;
        handler.postDelayed(showTime, 1000);
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        A.a();
        serviceRunning = false;
        handler.removeCallbacks(showTime);
        super.onDestroy();
    }

    private Runnable showTime = new Runnable() {
        public void run() {
//            Log.i("time:", new Date().toString());
            A.a("time:" + new Date().toString() );
            handler.postDelayed(this, 1000);
        }
    };
}
