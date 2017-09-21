package tw.com.flag.ch02_button;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author peter
 */
public class A {

    public static void a() {
        Log.i(new Throwable().getStackTrace()[1].getFileName(),
                " [line = " + String.format("%5d", new Throwable().getStackTrace()[1].getLineNumber()) + "]" +
                        " file = " + new Throwable().getStackTrace()[1].getFileName() +
                        " [method = " + new Throwable().getStackTrace()[1].getMethodName() + " ]" +
                        " [Ch16_iTankLed NX Rear Device pandora xxx] "
        );

    }

    public static void a(String str) {
        Log.i(new Throwable().getStackTrace()[1].getFileName(),
                " [line = " + String.format("%5d", new Throwable().getStackTrace()[1].getLineNumber()) + "]" +
                        " file = " + new Throwable().getStackTrace()[1].getFileName() +
                        " [method = " + new Throwable().getStackTrace()[1].getMethodName() + " ]" +
                        " [ " + str + " ]" +
                        " [Ch16_iTankLed NX Rear Device pandora xxx] "
        );

    }



}
