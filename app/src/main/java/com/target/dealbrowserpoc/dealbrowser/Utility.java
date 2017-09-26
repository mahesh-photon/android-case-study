package com.target.dealbrowserpoc.dealbrowser;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;

/**
 * Created by mcnaik on 9/24/17.
 */

public class Utility {

    public static final String DEAL_URL =  "http://target-deals.herokuapp.com/api/deals";

    public static boolean isStringEmpty(@Nullable final String s) {
        return s == null || s.length() == 0;
    }

    public static boolean isStringEmpty(@Nullable final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connMgr != null) {
            final NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            return (networkInfo != null && networkInfo.isConnected());
        }
        return false;
    }
}


