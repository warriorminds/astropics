package com.warriorminds.astropics.utils;

import android.content.Context;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

/**
 * Created by rodrigo.guerrero on 6/26/2015.
 */
public class Utils {

    public static String extractId(String url) {
        url = url.substring(0, url.length() - 1); // remove last /
        int lastIndex = url.lastIndexOf("/");
        String id = url.substring(lastIndex + 1, url.length());
        return id;
    }

    public static void setArrayInformation(TextView viewValue, String[] info, boolean breakLine) {
        if (info != null && info.length > 0) {
            String newString = "";
            for (int i = 0; i < info.length; i++) {
                if (breakLine)
                    newString += (info[i].trim() + "\n");
                else
                    newString += (info[i] + ", ");
            }

            newString = newString.substring(0, newString.length() - 2);
            viewValue.setText(newString);
        }
    }

    public static void hideKeyboard(AppCompatActivity activity,
                                    IBinder windowToken) {
        InputMethodManager mgr =
                (InputMethodManager) activity.getSystemService
                        (Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(windowToken, 0);
    }
}
