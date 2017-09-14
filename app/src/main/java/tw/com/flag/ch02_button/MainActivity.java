package tw.com.flag.ch02_button;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity" ;
    private PowerManager.WakeLock mWakeLock;
    private PowerManager mPowerManager = null;
    private boolean                    mScreenOn = true;
    WindowManager.LayoutParams params = null ;
    private Thread thread = null ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        params = this.getWindow().getAttributes();

        mPowerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);



        A.a("11111.  mPowerManager = " + mPowerManager );
    }

    int size=31; //字型大小, 初值 30(sp)

    public void bigger(View v) {
        TextView txv;
        txv = (TextView) findViewById(R.id.txv);
        txv.setTextSize(++size);

        /** Turn on: */
        // turn on screen
        A.a( "ON!");
//        mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "tag");
//        mWakeLock.acquire();

        A.a("aaa  222222222222222222222222222222222");

//        Intent startMain = new Intent(Intent.ACTION_MAIN);
//        startMain.addCategory(Intent.CATEGORY_HOME);
//        startActivity(startMain);

        A.a("333333333333333333333333333333333");

    }


    public void turnoff(View v) {

        /** Turn off: */
        // turn off screen

        A.a("3333333333333333333333333333333333");

        // SLEEP 2 SECONDS HERE ...
        final Handler handler = new Handler();
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        A.a("5555555555555555555555555555555555555555");
                        mWakeLock.release();
                    }
                });
            }
        }, 5000);


        A.a( "OFF!");
        mWakeLock = mPowerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK , "tag");
        mWakeLock.acquire();
        A.a("44444444444444444444444444444444444");






    }

    protected void onStart () {
        Log.v(TAG, "onStart!");
        A.a( "onStart!");
        super.onStart();
    }
    protected void onRestart() {
        Log.v(TAG, "onRestart!");
        A.a( "onRestart!");
        super.onRestart();
    }
    protected void onResume () {
        Log.v(TAG, "onResume!");
        A.a( "onResume!");
        super.onResume();
    }
    protected void onStop () {
        Log.v(TAG, "onStop!");
        A.a( "onStop!");
        super.onStop();
    }
    protected void onPause() {
        Log.v(TAG, "onPause!");
        A.a( "onPause!");
        super.onPause();
    }
    protected void onDestroy() {
        Log.v(TAG, "onDestroy");
        A.a( "onDestroy");
        super.onDestroy();
    }

}
