package com.hry1993sd.tetris;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.net.InetAddress;


public class PlayWithPeopleActivityClient extends AppCompatActivity {

    public static InetAddress hostAddress;
    public static int hostPort;

    private NsdManager mNsdManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNsdManager = (NsdManager) getApplicationContext().getSystemService(Context.NSD_SERVICE);
        mNsdManager.discoverServices("_http._tcp.", NsdManager.PROTOCOL_DNS_SD, mDiscoveryListener);
    }

    NsdManager.DiscoveryListener mDiscoveryListener = new NsdManager.DiscoveryListener() {

        // Called as soon as service discovery begins.
        @Override
        public void onDiscoveryStarted(String regType) {
            Log.d("", "Service discovery started");
        }

        @Override
        public void onServiceFound(NsdServiceInfo service) {

            if (!service.getServiceType().equals("_http._tcp.")) {
                // Service type is the string containing the protocol and
                // transport layer for this service.
                Log.d("", "Unknown Service Type: " + service.getServiceType());
            } else {
                // connect to the service and obtain serviceInfo
                mNsdManager.resolveService(service, mResolveListener);
            }
        }

        @Override
        public void onServiceLost(NsdServiceInfo service) {
            // When the network service is no longer available.
            // Internal bookkeeping code goes here.
            Log.e("", "service lost" + service);
        }

        @Override
        public void onDiscoveryStopped(String serviceType) {
            Log.i("", "Discovery stopped: " + serviceType);
        }

        @Override
        public void onStartDiscoveryFailed(String serviceType, int errorCode) {
            Log.e("", "Discovery failed: Error code:" + errorCode);
            mNsdManager.stopServiceDiscovery(this);
        }

        @Override
        public void onStopDiscoveryFailed(String serviceType, int errorCode) {
            Log.e("", "Discovery failed: Error code:" + errorCode);
            mNsdManager.stopServiceDiscovery(this);
        }
    };

    NsdManager.ResolveListener mResolveListener = new NsdManager.ResolveListener() {

        @Override
        public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
            // Called when the resolve fails. Use the error code to debug.
            Log.e("", "Resolve failed " + errorCode);
            Log.e("", "service = " + serviceInfo);
        }

        @Override
        public void onServiceResolved(NsdServiceInfo serviceInfo) {
            Log.d("", "Resolve Succeeded. " + serviceInfo);

            // Obtain port and IP
            hostPort = serviceInfo.getPort();
            hostAddress = serviceInfo.getHost();
        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mNsdManager != null) {
            mNsdManager.stopServiceDiscovery(mDiscoveryListener);
        }
    }
}
