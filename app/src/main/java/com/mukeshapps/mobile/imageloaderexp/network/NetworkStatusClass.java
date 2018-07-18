package com.mukeshapps.mobile.imageloaderexp.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by ng6b05a on 7/18/2018.
 * Use this class to check and manage network connectivity
 */

public class NetworkStatusClass {

    /**
     * Check network connectivity
     * @param context :  context of Android component
     * @return : statas, true or false
     */
    public static boolean isNetworkConnected(Context context) {
        boolean isConnected;
        try{
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return isConnected;
    }
}