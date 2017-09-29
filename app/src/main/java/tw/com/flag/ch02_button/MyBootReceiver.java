package tw.com.flag.ch02_button;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBootReceiver extends BroadcastReceiver {

    Context m_context ;

    @Override
    public void onReceive(Context context, Intent intent) {

        A.a("111 Action: " + intent.getAction());

        Toast.makeText(context, "111111", Toast.LENGTH_SHORT).show();

        m_context = context ;
        Intent it = new Intent(context, MyAudioService.class);
        context.startService(it);


    }
}
