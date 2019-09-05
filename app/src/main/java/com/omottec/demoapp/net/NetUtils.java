package com.omottec.demoapp.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public final class NetUtils {
    private NetUtils() {}

    public static String getWifiInfo(Context context) {
        if (context == null) return "";
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String wifiState = getWifiStateName(wifiManager.getWifiState());
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        StringBuilder wifiStrBuilder = new StringBuilder("{wifiState:")
                .append(wifiState);
        if (wifiInfo == null) {
            return wifiStrBuilder.append('}').toString();
        } else {
            String ip = intIp2StrIp(wifiInfo.getIpAddress());
            return wifiStrBuilder.append(", ").append(wifiInfo)
                    .append(", clientIp:").append(ip)
                    .append(", clientMac:").append(getMacByHost(ip))
                    .append('}').toString();
        }
    }

    public static String getNetInfo(Context context) {
        if (context == null) return "";
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null)
            return new StringBuilder("netInfo:no active net")
                    .append("wifiInfo:").append(getWifiInfo(context))
                    .toString();
        else
            return new StringBuilder("netInfo:").append(networkInfo)
                    .append("wifiInfo:").append(getWifiInfo(context))
                    .toString();
    }

    public static String getWifiStateName(int wifiState) {
        switch (wifiState) {
            case WifiManager.WIFI_STATE_DISABLED:
                return "WIFI_STATE_DISABLED";
            case WifiManager.WIFI_STATE_DISABLING:
                return "WIFI_STATE_DISABLING";
            case WifiManager.WIFI_STATE_ENABLING:
                return "WIFI_STATE_ENABLING";
            case WifiManager.WIFI_STATE_ENABLED:
                return "WIFI_STATE_ENABLED";
            case WifiManager.WIFI_STATE_UNKNOWN:
                return "WIFI_STATE_UNKNOWN";
                default:
                    return "WIFI_STATE_ILLEGAL";
        }
    }

    public static String getNetType(int type) {
        switch (type) {
            case ConnectivityManager.TYPE_WIFI:
                return "TYPE_WIFI";
            case ConnectivityManager.TYPE_MOBILE:
                return "TYPE_MOBILE";
            case ConnectivityManager.TYPE_MOBILE_DUN:
                return "TYPE_MOBILE_DUN";
            case ConnectivityManager.TYPE_VPN:
                return "TYPE_VPN";
            case ConnectivityManager.TYPE_BLUETOOTH:
                return "TYPE_BLUETOOTH";
            case ConnectivityManager.TYPE_ETHERNET:
                return "TYPE_ETHERNET";
            case ConnectivityManager.TYPE_DUMMY:
                return "TYPE_DUMMY";
            case ConnectivityManager.TYPE_WIMAX:
                return "TYPE_WIMAX";
                default:
                    return "TYPE_UNKNOWN";
        }
    }

    public static String getIp(Context context) {
        if (context == null) return "";
        NetworkInfo netInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
        if (netInfo == null || !netInfo.isAvailable())
            return "";
        if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            return wifiInfo == null ? "" : intIp2StrIp(wifiInfo.getIpAddress());
        } else if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            try {
                Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                while (interfaces.hasMoreElements()) {
                    NetworkInterface networkInterface = interfaces.nextElement();
                    Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress inetAddress = inetAddresses.nextElement();
                        if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address)
                            return inetAddress.getHostAddress();
                    }
                }
                return "";
            } catch (SocketException e) {
                e.printStackTrace();
                return "";
            }
        } else {
            return "";
        }
    }

    public static String intIp2StrIp(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }


    public static String getMacByHost(String host) {
        try {
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(InetAddress.getByName(host));
            byte[] macBytes = networkInterface.getHardwareAddress();
            StringBuilder macStrBuilder = new StringBuilder();
            for (byte b : macBytes)
                macStrBuilder.append(String.format("%02X:", b));
            if (macStrBuilder.length() > 0)
                macStrBuilder.deleteCharAt(macStrBuilder.length() - 1);
            return macStrBuilder.toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "";
        } catch (SocketException e) {
            e.printStackTrace();
            return "";
        }
    }
}
