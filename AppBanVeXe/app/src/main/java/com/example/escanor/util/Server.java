package com.example.escanor.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.aware.WifiAwareManager;
import android.text.format.Formatter;

import static android.content.Context.WIFI_SERVICE;

public class Server
{
    public static String localhost="192.168.1.8";
    public static String DuongDan="http://"+localhost+"/server/getVeXe.php";
    public static String setVeXe="http://"+localhost+"/server/setVeXe.php";

    public static String getWifiAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        return (ipAddress & 0xFF) + "." +
                ((ipAddress >> 8) & 0xFF) + "." +
                ((ipAddress >> 16) & 0xFF) + "." +
                (ipAddress >> 24 & 0xFF);
    }

}
