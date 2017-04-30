package com.project.spaceapps.beebox.beebox.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by Matheus on 29/04/2017.
 */

public class Connection extends BroadcastReceiver {

    public String message;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getExtras() != null) {
            final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
                message = "Internet " + networkInfo.getTypeName() + " Conectada";
                Log.i(Constants.TAG, message);
            } else if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, Boolean.FALSE)) {
                message = "Não está conectado";
                Log.d(Constants.TAG, message);
            } else {
                message = "Não registrada a conexão";
                Log.d(Constants.TAG, message);
            }

            Intent i = new Intent(Constants.STATUSCONNECTION);
            i.putExtra(Constants.MESSAGE, message);
            context.sendBroadcast(i);
        }
    }
}
