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

    private static ProgressDialog mProgressDialog;
    static File g_File = null;
    static BufferedWriter g_output = null;

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

    public static void toast(Context mContext, String string) {
        Toast.makeText(mContext, string, Toast.LENGTH_SHORT).show();
    }

    public static void blockUI(Context mContext, final String message) {
        if (message != null) {
            mProgressDialog = ProgressDialog.show(mContext, "", message, true);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        } else {
            if (mProgressDialog != null)
                mProgressDialog.dismiss();
        }
    }

    public static void l(String string) {
        Log(
                " [file = " + new Throwable().getStackTrace()[1].getFileName() + " ]" +
                        " [line = " + new Throwable().getStackTrace()[1].getLineNumber() + " ]" +
                        " [method = " + new Throwable().getStackTrace()[1].getMethodName() + " ]" +
                        " [ " + string + " ]"
        );
    }

    public static void l() {
        Log(
                " [file = " + new Throwable().getStackTrace()[1].getFileName() + " ]" +
                        " [line = " + new Throwable().getStackTrace()[1].getLineNumber() + " ]" +
                        " [method = " + new Throwable().getStackTrace()[1].getMethodName() + " ]"
        );
    }

    public static void dialog_normal(Context mContext, String strTitle, String strMessage) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle(strTitle); // set title
        // set dialog message
        alertDialogBuilder.setMessage(strMessage).setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create(); // create alert dialog
        alertDialog.show(); // show it
    }


    public static void b() {
        Log.i(new Throwable().getStackTrace()[1].getFileName(),
                " [line = " + String.format("%5d", new Throwable().getStackTrace()[1].getLineNumber()) + "]" +
                        " [method = " + new Throwable().getStackTrace()[1].getMethodName() + " ]" +
                        " [xxx] "
        );

        Log(
                " [file = " + new Throwable().getStackTrace()[1].getFileName() + " ]" +
                        " [line = " + new Throwable().getStackTrace()[1].getLineNumber() + " ]" +
                        " [method = " + new Throwable().getStackTrace()[1].getMethodName() + " ]"
        );


    }


    public static void b(String string) {

        Log.i(new Throwable().getStackTrace()[1].getFileName(),
                " [line = " + String.format("%5d", new Throwable().getStackTrace()[1].getLineNumber()) + "]" +
                        " [method = " + new Throwable().getStackTrace()[1].getMethodName() + " ]" +
                        " [ " + string + " ]" +
                        " [pandora xxx] "
        );

        Log(
                " [file = " + new Throwable().getStackTrace()[1].getFileName() + " ]" +
                        " [line = " + new Throwable().getStackTrace()[1].getLineNumber() + " ]" +
                        " [method = " + new Throwable().getStackTrace()[1].getMethodName() + " ]" +
                        " [ " + string + " ]"
        );

    }

    public static File getLogFile() {
        if (g_File == null)
            g_File = new File(getNewFilePath());
        return g_File;
    }

    public static BufferedWriter getBufferWriter() {
        if (g_output == null)
            try {
                g_output = new BufferedWriter(new FileWriter(getLogFile()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        return g_output;
    }

    private static String getVersionString() {
//    		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }

    static public void Log(String strMessage) {
        BufferedWriter getBufferWriter = getBufferWriter();
        try {
            getBufferWriter.write(strMessage + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public void closeFile() {
        try {
            getBufferWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        g_output = null;
        g_output = null;
    }

    static String getNewFilePath() {
        File myDirectory = new File(Environment.getExternalStorageDirectory(), "BBC");
        if (!myDirectory.exists()) {
            myDirectory.mkdirs();
        }

        String strFolderPath = Environment.getExternalStorageDirectory() + "/BBC/" + getVersionString() + ".log";
        return strFolderPath;
    }

}
