package tw.com.flag.ch02_button;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;

import java.util.List;

/**
 * Created by user on 2017/9/14.
 */
class ASRListener implements RecognitionListener {   // {{{

    private MyASRService parentService = null;

    // TODO: change package name to remove ch02_button.Used to load the 'native-lib' library on application startup.
    // Todo: To enable the following JNI mechanism
//    static {
//        System.loadLibrary("native-lib");
//    }
//    public native void goHomeJNI();

    public ASRListener(MyASRService asrservice) {
        this.parentService = asrservice;
    }


    @Override
    public void onReadyForSpeech(Bundle bundle) {
        A.a();
        parentService.receiveErrorLock = false;
    }

    @Override
    public void onBeginningOfSpeech() {
        A.a();
    }

    @Override
    public void onRmsChanged(float v) {
        // A.a(); // peter. lots of times. thus comment out.   20170907
        // The sound level in the audio stream has changed.
        // Log.d(TAG, "sound level changed:"+v);
    }

    @Override
    public void onBufferReceived(byte[] bytes) {  //  A.a();
        // Log.d(TAG, "onBufferReceived");
    }

    @Override
    public void onEndOfSpeech() {
//        A.a();
    }

    @Override
    public void onError(int errorCode) {  // A.a();

//        A.a("errorCode:" + errorCode + ", lock:" + parentService.receiveErrorLock);

        if (parentService.receiveErrorLock == false) {
//            A.a();
            if (isErrorLock(errorCode)) {
//                A.a();
                parentService.receiveErrorLock = true;
//                parentService.ReStartGoogleASR();

                MyASRService.serviceRunning = false ;
                Intent it = new Intent(parentService, MyAudioService.class);
                parentService.startService(it);
            }
            else {
//                A.a();
            }
        }
//        A.a();
    }

    private boolean isErrorLock(int errorCode) {
        return (errorCode == SpeechRecognizer.ERROR_NO_MATCH) ||
                (errorCode == SpeechRecognizer.ERROR_SPEECH_TIMEOUT) ||
                (errorCode == SpeechRecognizer.ERROR_NETWORK_TIMEOUT) ||
                (errorCode == SpeechRecognizer.ERROR_NETWORK);
    }

    @Override
    public void onResults(Bundle bundle) {
//        A.a("onResults()");
        receiveResults(bundle);
    }

    @Override
    public void onPartialResults(Bundle bundle) {  // A.a();
        //Log.d(TAG, "onPartialResults()");
        //receiveResults(bundle);
    }

    @Override
    public void onEvent(int i, Bundle bundle) {    // A.a();
    }


    private void receiveResults(Bundle results) {   // {{{
//        A.a();

        if ((results != null) && results.containsKey(SpeechRecognizer.RESULTS_RECOGNITION)) {

            List<String> heard = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

            if (heard.size() >= 1) {
                A.a("spoken words size = " + heard.size());
                receiveWhatWasHeard(heard);
                parentService.ReStartGoogleASR();
            }
        } else {
//            A.a();
        }
    }   // }}}

    private void receiveWhatWasHeard(List<String> heard) {   // {{{


        for (String word : heard) {

            A.a(" 1. word heard = " + word);

            if (word.contains("回首頁")) {
//                A.a(" command 回首頁 is heard. ");
                goHomeLauncher();
                return;
            }
        }
    }  // }}}

    private void goHomeLauncher() {


        // case 1 : this one ask user which app to start for once or all
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_LAUNCHER);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        parentService.startActivity(startMain);


        // case 2: this one gets exception error
//				Intent startMain = new Intent(Intent.ACTION_MAIN);
//				startMain.addCategory(Intent.CATEGORY_HOME);
//				startActivity(startMain);

        // case 3: this one gets exception error
//				startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME).setPackage(getPackageManager().queryIntentActivities(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME), getPackageManager().MATCH_DEFAULT_ONLY).get(0).activityInfo.packageName));

    }
//


}  // }}}
