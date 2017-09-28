package tw.com.flag.ch02_button;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBootReceiver extends BroadcastReceiver {

    Context m_context ;

    @Override
    public void onReceive(Context context, Intent intent) {

        A.a("44 Action: " + intent.getAction());

        m_context = context ;
        Intent it = new Intent(context, MyAudioService.class);
        context.startService(it);


    }
}
