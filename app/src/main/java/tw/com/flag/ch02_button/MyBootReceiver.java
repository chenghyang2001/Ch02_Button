package tw.com.flag.ch02_button;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import tw.com.flag.ch02.A;

public class MyBootReceiver extends BroadcastReceiver {

    Context m_context ;

    @Override
    public void onReceive(Context context, Intent intent) {

//        A.a("111 Action: " + intent.getAction());
//        Toast.makeText(context, "111", Toast.LENGTH_SHORT).show();

        A.a("222 Action: " + intent.getAction());
        Toast.makeText(context, "222", Toast.LENGTH_SHORT).show();


        m_context = context ;
        Intent it = new Intent(context, MyAudioService.class);

        // TODO: 20170929 peter. comment it out today for developing. enable it on release.
        context.startService(it);


    }
}
