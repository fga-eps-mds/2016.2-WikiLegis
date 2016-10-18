package gppmds.wikilegis.controller;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

/**
 * Created by marcelo on 10/17/16.
 */
public class DataDownloadController {
    private static Context context;
    private static DataDownloadController instance = null;

    private DataDownloadController(final Context context) {
        this.context = context;
    }

    public static DataDownloadController getInstance(final Context context) {
        if (instance == null) {
            instance = new DataDownloadController(context);
        }
        return  instance;
    }

    public String connectionType() {
        final ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifi.isConnectedOrConnecting ()) {
            Log.i("Connection", "Wifi");

            return "Wifi";
        } else if (mobile.isConnectedOrConnecting ()) {
            Log.i("Connection", "Mobile 3G");

            return "Mobile data";
        } else {
            Log.i("Connection", "No network");

            return "No network";
        }
    }
}
