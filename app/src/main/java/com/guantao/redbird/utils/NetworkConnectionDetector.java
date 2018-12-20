package com.guantao.redbird.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class NetworkConnectionDetector {
    private Context _context;

    /**
     * 获取当前网络类型
     *
     */
    public static final int NETTYPE_WIFI = 0x01;
    public static final int NETTYPE_MOBILE = 0x02;

    public NetworkConnectionDetector(Context context) {

        this._context = context;
    }

    /**
     * 判断是否联网
     * @return
     */
    public boolean isConnectingToInternet() {

        ConnectivityManager connectivity = (ConnectivityManager) _context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {

            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {

                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {

                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取网络类型编码
     * @return
     */
    public int getNetworkType() {
        int netType = 0;
        ConnectivityManager connectivityManager = (ConnectivityManager) _context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isAvailable()) {
            int nType = networkInfo.getType();
            if (nType == ConnectivityManager.TYPE_MOBILE) {
                netType = NETTYPE_MOBILE;
            } else if (nType == ConnectivityManager.TYPE_WIFI) {
                netType = NETTYPE_WIFI;
            }
        }
        return netType;
    }

    /**
     * 获取Wifi名字
     * @return
     */
    public String getWifiName() {

        if (getNetworkType() == NETTYPE_WIFI) {
            WifiManager wifiManager = (WifiManager) _context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            return wifiInfo.getSSID();
        }else {
            return null;
        }
    }

    /**
     * 获取IP地址
     * @return
     */
    public String getIP() {

        if (getNetworkType() == NETTYPE_WIFI) {
            return getLocalIpAddress();
        }else if (getNetworkType() == NETTYPE_MOBILE) {
            return getIpAddress();
        }else {
            return null;
        }
    }

    /**
     * 获取本机WIFI网络的IP地址
     *
     * @return
     */
    private String getLocalIpAddress() {
        WifiManager wifiManager = (WifiManager) _context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        // 获取32位整型IP地址
        int ipAddress = wifiInfo.getIpAddress();

        // 返回整型地址转换成“*.*.*.*”地址
        return String.format("%d.%d.%d.%d", (ipAddress & 0xff),(ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff),(ipAddress >> 24 & 0xff));
    }

    /**
     * 获取本机3G网络的IP地址
     * @return
     */
    private String getIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()
                            && inetAddress instanceof Inet4Address) {
                        // if (!inetAddress.isLoopbackAddress() && inetAddress
                        // instanceof Inet6Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
