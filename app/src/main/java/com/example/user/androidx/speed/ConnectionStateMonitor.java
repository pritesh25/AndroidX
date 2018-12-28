package com.example.user.androidx.speed;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.util.Log;

public class ConnectionStateMonitor extends ConnectivityManager.NetworkCallback {

    private static final String TAG = ConnectionStateMonitor.class.getSimpleName();
    final NetworkRequest networkRequest;
    public NetworkCallBack callBack;

    public ConnectionStateMonitor(NetworkCallBack callBack) {
        this.callBack = callBack;
        networkRequest = new NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR).addTransportType(NetworkCapabilities.TRANSPORT_WIFI).build();
    }

    public void enable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityManager.registerNetworkCallback(networkRequest , this);
    }

    // Likewise, you can have a disable method that simply calls ConnectivityManager#unregisterCallback(networkRequest) too.

    @Override
    public void onAvailable(Network network) {
        // Do what you need to do here
        Log.d(TAG,"onAvailable");
        callBack.onConnected();
    }

    @Override
    public void onLosing(Network network, int maxMsToLive) {
        super.onLosing(network, maxMsToLive);
        Log.d(TAG,"onLosing");
    }

    @Override
    public void onLost(Network network) {
        super.onLost(network);
        Log.d(TAG,"onLost");
        callBack.onDisconnected();
    }

    @Override
    public void onUnavailable() {
        super.onUnavailable();
        Log.d(TAG,"onUnavailable");
    }

    @Override
    public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);
        Log.d(TAG,"onCapabilitiesChanged");
        Log.d(TAG,"Upload   = "+networkCapabilities.getLinkUpstreamBandwidthKbps()+" kbps");
        Log.d(TAG,"Download = "+networkCapabilities.getLinkDownstreamBandwidthKbps()+" kbps");
    }

    @Override
    public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
        super.onLinkPropertiesChanged(network, linkProperties);
        Log.d(TAG,"onLinkPropertiesChanged");
    }

    public interface NetworkCallBack {
        void onConnected();
        void onDisconnected();
    }
}