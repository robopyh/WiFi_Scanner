package com.mobilki.wi_fiscanner;

import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Info extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        int itemPosition = getIntent().getExtras().getInt("Position");
        ScanResult item = MainActivity.scanResultList.get(itemPosition);
        String info = "BSSID: " + item.BSSID + "\n" + item.capabilities + "\nFrequency: " + item.frequency + "\nLevel: " + item.level;
        setTitle(item.SSID);
        TextView infoText = (TextView) findViewById(R.id.textinfo);
        infoText.setText(info);
    }
}
