package com.hry1993sd.tetris;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.net.InetAddress;


public class PlayWithPeopleActivityServer extends AppCompatActivity {

    public static InetAddress hostAddress;
    public static int hostPort;

    private NsdManager mNsdManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNsdManager = (NsdManager) getApplicationContext().getSystemService(Context.NSD_SERVICE);
        NsdServiceInfo serviceInfo = new NsdServiceInfo();
        serviceInfo.setServiceName("tetris");
        serviceInfo.setServiceType("_http._tcp.");
        serviceInfo.setPort(9000);

        mNsdManager.registerService(serviceInfo,
                NsdManager.PROTOCOL_DNS_SD,
                mRegistrationListener);
    }

    NsdManager.RegistrationListener mRegistrationListener = new NsdManager.RegistrationListener() {

        @Override
        public void onServiceRegistered(NsdServiceInfo NsdServiceInfo) {
            String mServiceName = NsdServiceInfo.getServiceName();
            Log.d("", "Registered name : " + mServiceName);
        }

        @Override
        public void onRegistrationFailed(NsdServiceInfo serviceInfo,
                                         int errorCode) {
            // Registration failed! Put debugging code here to determine
            // why.
        }

        @Override
        public void onServiceUnregistered(NsdServiceInfo serviceInfo) {
            // Service has been unregistered. This only happens when you
            // call
            // NsdManager.unregisterService() and pass in this listener.
            Log.d("",
                    "Service Unregistered : " + serviceInfo.getServiceName());
        }

        @Override
        public void onUnregistrationFailed(NsdServiceInfo serviceInfo,
                                           int errorCode) {
            // Unregistration failed. Put debugging code here to determine
            // why.
        }


    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mNsdManager != null) {
            mNsdManager.unregisterService(mRegistrationListener);
        }
    }
}
