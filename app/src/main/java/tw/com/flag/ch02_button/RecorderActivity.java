package tw.com.flag.ch02_button;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RecorderActivity extends Activity {

    private Button recoButn = null;
    private Button stopButn = null;
    private Button exitButn = null;
           Context mContext = null;

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

        A.a("111");
        A.a("111");

        // Record Button
        recoButn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                recoButn.setEnabled(false);
                stopButn.setEnabled(true);

                Intent it = new Intent(mContext, MyAudioService.class);
                mContext.startService(it);

            }
        });

        // Stop Button
        stopButn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // adjust UI
                recoButn.setEnabled(true);
                stopButn.setEnabled(false);

                Intent it = new Intent(mContext, MyAudioService.class);
                mContext.stopService(it);

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

}