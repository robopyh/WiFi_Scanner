package com.mobilki.wi_fiscanner;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private RecyclerView recyclerView;
    private List<ScanResult> scanResult;
    private Context context;

    MyAdapter(RecyclerView recyclerView, Context context) {
        this.recyclerView = recyclerView;
        scanResult = MainActivity.scanResultList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        view.setOnClickListener(new MyOnClickListener());
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(scanResult.get(position).SSID);

        final String[] securityModes = {"WEP", "PSK", "EAP"};
        boolean security = false;
        for (int i = securityModes.length - 1; i >= 0; i--) {
            if (scanResult.get(position).capabilities.contains(securityModes[i])) {
                security = true;
                break;
            }
        }

        int level = WifiManager.calculateSignalLevel(scanResult.get(position).level, 5);

        switch (level) {
            case 0:
                holder.imageView.setImageResource(R.drawable.ic_signal_wifi_0_bar_black_18dp);
                return;
            case 1:
                if (security)
                    holder.imageView.setImageResource(R.drawable.ic_signal_wifi_1_bar_lock_black_18dp);
                else
                    holder.imageView.setImageResource(R.drawable.ic_signal_wifi_1_bar_black_18dp);
                return;
            case 2:
                if (security)
                    holder.imageView.setImageResource(R.drawable.ic_signal_wifi_2_bar_lock_black_18dp);
                else
                    holder.imageView.setImageResource(R.drawable.ic_signal_wifi_2_bar_black_18dp);
                return;
            case 3:
                if (security)
                    holder.imageView.setImageResource(R.drawable.ic_signal_wifi_3_bar_lock_black_18dp);
                else
                    holder.imageView.setImageResource(R.drawable.ic_signal_wifi_3_bar_black_18dp);
                return;
            case 4:
                if (security)
                    holder.imageView.setImageResource(R.drawable.ic_signal_wifi_4_bar_lock_black_18dp);
                else
                    holder.imageView.setImageResource(R.drawable.ic_signal_wifi_4_bar_black_18dp);
                return;
            default:
        }
    }

    @Override
    public int getItemCount() {
        return scanResult.size();
    }

    private class MyOnClickListener implements View.OnClickListener {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildLayoutPosition(v);
            Intent intent = new Intent(context, Info.class);
            intent.putExtra("Position", itemPosition);
            context.startActivity(intent);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView mTextView;
        ImageView imageView;
        ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.ssid);
            imageView = (ImageView) v.findViewById(R.id.icon);
        }
    }
}
