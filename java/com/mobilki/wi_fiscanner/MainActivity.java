package com.mobilki.wi_fiscanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    WifiManager wifiManager;
    static List<ScanResult> scanResultList;
    ScanReceiver scanReceiver;
    RecyclerView recyclerView;
    MyAdapter myAdapter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(scanReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled())
        {
            Toast.makeText(getApplicationContext(), "Wi-Fi is disabled... Making it enabled", Toast.LENGTH_LONG).show();
            wifiManager.setWifiEnabled(true);
        }

        scanReceiver = new ScanReceiver();
        registerReceiver(scanReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        wifiManager.startScan();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    class ScanReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            scanResultList = wifiManager.getScanResults();
            Collections.sort(scanResultList, comparator);
            myAdapter = new MyAdapter(recyclerView, MainActivity.this);
            recyclerView.setAdapter(myAdapter);
        }
    }

    private Comparator<ScanResult> comparator = new Comparator<ScanResult>() {
        @Override
        public int compare(ScanResult lhs, ScanResult rhs) {
            return (lhs.level >rhs.level ? -1 : (lhs.level==rhs.level ? 0 : 1));
        }
    };
}
