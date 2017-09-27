package tw.com.flag.ch02_button;


import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

import java.util.Date;

public class MyService extends Service {

    public static Boolean serviceRunning = false;
    protected 			RecognitionListener   	mSpeechRecognizerListner	= null  ;  // }}}
    public 				boolean 				receiveErrorLock 			= false ;
    protected           SpeechRecognizer 		speechRecognizer 			= null  ;
    protected           Intent  				recognizerIntent 			= null  ;


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
//        handler.postDelayed(showTime, 1000);

        if ( ( speechRecognizer == null ) && ( recognizerIntent == null  )) {
            initASR();
            ReStartGoogleASR();
        }

        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        A.a();
        serviceRunning = false;
        handler.removeCallbacks(showTime);

        if ( (speechRecognizer != null) && (recognizerIntent != null) ) {
            DestoryGoogleASR();
        }


        super.onDestroy();
    }

    private Runnable showTime = new Runnable() {
        public void run() {
//            Log.i("time:", new Date().toString());
            A.a("time:" + new Date().toString() );
            handler.postDelayed(this, 1000);
        }
    };

    private RecognitionListener getSpeechRecognizerListner() {  // A.a(); // {{{

        if (mSpeechRecognizerListner == null) {

            mSpeechRecognizerListner = new ASRListener(this);
            A.a( "mSpeechRecognizerListner="+mSpeechRecognizerListner);

        }
        return mSpeechRecognizerListner;

    }  // }}}

    public void ReStartGoogleASR() {  // A.a();   // {{{

        A.a( "ReStartGoogleASR()");

        synchronized(speechRecognizer) {

            if (speechRecognizer != null) {
                speechRecognizer.stopListening();
                speechRecognizer.cancel();
                speechRecognizer.destroy();
            }
            speechRecognizer.setRecognitionListener(getSpeechRecognizerListner());
            speechRecognizer.startListening(recognizerIntent);
        }

    }  // }}}

    public void DestoryGoogleASR() {  A.a(); // {{{

        A.a( "DestoryGoogleASR() 1111");

        synchronized(speechRecognizer) {
            A.a( "DestoryGoogleASR() 2222");
            if (speechRecognizer != null) {
                A.a( "DestoryGoogleASR() 3333");
                speechRecognizer.stopListening();
                speechRecognizer.cancel();
                speechRecognizer.destroy();
            }
            A.a( "DestoryGoogleASR() 4444");
        }
        A.a( "DestoryGoogleASR() 5555");

//		stopService(new Intent(getBaseContext(), ASRService.class));  // peter. 20170907 can not stop this service because the ASR need keep listening user's talking


    }  // }}}

    public void initASR() {

        if ( speechRecognizer == null ) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
            speechRecognizer.setRecognitionListener(getSpeechRecognizerListner());
        }

        if ( recognizerIntent == null ) {

            recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

            // this following line will cause null on ZTE phone (android 7.1)
//			recognizerIntent = RecognizerIntent.getVoiceDetailsIntent(m_ctx);

            recognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 30000);
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 30000);
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 30000);

            // accept partial results if they come
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
            if (!recognizerIntent.hasExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE)) {
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "com.dummy");
            }
        }

    }



}
