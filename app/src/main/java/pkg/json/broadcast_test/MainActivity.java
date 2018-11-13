package pkg.json.broadcast_test;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ConnectionReceiver.Observer {
    TextView text;
    ConnectionReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.text);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Registers BroadcastReceiver to track network connection changes.
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        mReceiver = new ConnectionReceiver(this);
        registerReceiver(mReceiver, filter);
    }

    /* ConnectionReciver によって必要なるメソッド。
    * 以下３点*/
    @Override
    public void driveConnect() {
        //Connected with Wi-Fi access point "drireco"
        text.setText("Drive-Recorder!");
    }

    @Override
    public void onConnect() {
        //ネットワークに接続した時の処理
        text.setText("Connect!");
    }

    @Override
    public void onDisconnect() {
        //ネットワークが切断された時の処理
        text.setText("Disconnect");
    }
    /* 上記、ConnectionReciver により必要な３点 */

    @Override
    public void onPause() {
        super.onPause();
        if(mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
    }
}
