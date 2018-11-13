package pkg.json.broadcast_test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import static android.content.Context.WIFI_SERVICE;

public class ConnectionReceiver extends BroadcastReceiver {
    private Observer mObserver;

    public ConnectionReceiver(Observer observer) {
        mObserver = observer;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        WifiManager wifiManager = (WifiManager)context.getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        if (info == null) {
            //Disconnected NetWork!
            mObserver.onDisconnect();
        }else if(info.isConnected()) {
            //Connected NetWork!
            if (info.getTypeName().equals("WIFI")) {
                //Wi-Fi接続ではある
                if(wifiInfo.getSSID().equals("drireco")) {
                    mObserver.driveConnect();
                }else {
                    mObserver.onConnect();
                }
            }else if (info.getTypeName().equals("mobile")) {
                //スマホ回線である。
                mObserver.onConnect();
            }else {
                mObserver.onDisconnect();
            }
        }else {
            //Unexpected ERROR!
            mObserver.onDisconnect();
        }
    }

    //----- コールバックを定義 -----
    interface Observer {
        void driveConnect();
        void onConnect();
        void onDisconnect();
    }
}