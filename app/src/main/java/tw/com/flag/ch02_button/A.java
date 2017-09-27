package tw.com.flag.ch02_button;

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
