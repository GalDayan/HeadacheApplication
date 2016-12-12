package com.example.gal.headache.util;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by Gal on 30/11/2016.
 */
public class ApplicationUtils {

    public static boolean applicationInstalled(Context context, String uri) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }
}
