package tw.com.flag.ch02;

import android.util.Log;


public class A {

    public static void a() {
        Log.i(new Throwable().getStackTrace()[1].getFileName(),
                " [line = " + String.format("%5d", new Throwable().getStackTrace()[1].getLineNumber()) + "]" +
                        " file = " + new Throwable().getStackTrace()[1].getFileName() +
                        " [method = " + new Throwable().getStackTrace()[1].getMethodName() + " ]" +
                        " [ xxx] "
        );
    }

    public static void a(String str) {
        Log.i(new Throwable().getStackTrace()[1].getFileName(),
                " [line = " + String.format("%5d", new Throwable().getStackTrace()[1].getLineNumber()) + "]" +
                        " file = " + new Throwable().getStackTrace()[1].getFileName() +
                        " [method = " + new Throwable().getStackTrace()[1].getMethodName() + " ]" +
                        " [ " + str + " ]" +
                        " [ xxx] "
        );
    }
}


/*   ================  Backup Area  below ====================================

//                        queueVolume.display();
//                        A.a( " isVolume Large = " + queueVolume.isVolumeLarge() )  ;
//                        A.a("amplitudeDb = " + amplitudeDb);


//    private void testQueue() {
//
//        RecorderActivity.MyQueue<Integer> queueNames = new RecorderActivity.MyQueue<Integer>(3);
//        queueNames.add(10);
//        queueNames.add(20);
//        queueNames.add(30);
//
//        for (int i : queueNames) {
//            A.a(" i = " + i);
//        }
//
//        queueNames.add(40);
//        for (int i : queueNames) {
//            A.a(" i = " + i);
//        }
//
//        queueNames.add(50);
//        for (int i : queueNames) {
//            A.a(" i = " + i);
//        }
//    }

//    private void stopMyService() {
//        if ((queueVolume.isVolumeLarge() == false) && (MyASRService.serviceRunning == true)) {
//
//            A.a("2.    isVolumeLarge = false");
//            Intent intent = new Intent(RecorderActivity.this, MyASRService.class);
//            stopService(intent);
//        }
//    }

    // Start Service Button Event Handler
    public void startService(View view) {



//        if (MyASRService.serviceRunning == false) {
//            Intent intent = new Intent(RecorderActivity.this, MyASRService.class);
//            startService(intent);
//        }



    }

    // Stop Service Button Event Handler
    public void stopService(View view) {

//        if (MyASRService.serviceRunning == true) {
//            Intent intent = new Intent(RecorderActivity.this, MyASRService.class);
//            stopService(intent);
//        }

    }





 */