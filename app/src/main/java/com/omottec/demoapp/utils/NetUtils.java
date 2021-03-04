package com.omottec.demoapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import androidx.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.webkit.WebSettings;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * Created by qinbingbing on 12/5/16.
 */

public final class NetUtils {
    public static final int NETWORK_NONE = 0;
    public static final int NETWORK_WIFI = 1;
    public static final int NETWORK_2G = 2;
    public static final int NETWORK_3G = 3;
    public static final int NETWORK_4G = 4;
    public static final int NETWORK_MOBILE = 5;

    private NetUtils() { throw new AssertionError(); }

    public static String getWifiIp(Context context) {
        String ip = null;
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager.isWifiEnabled()) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo == null) return ip;
            int i = wifiInfo.getIpAddress();
            ip = new StringBuilder()
                    .append(i & 0xff)
                    .append(".").append((i >> 8) & 0xff)
                    .append(".").append((i >> 16) & 0xff)
                    .append(".").append((i >> 24) & 0xff)
                    .toString();
        }
        return ip;
    }

    public static String getMobileIp() {
        try {
            Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
            if (networks == null) return null;
            while (networks.hasMoreElements()) {
                NetworkInterface network = networks.nextElement();
                Enumeration<InetAddress> addresses = network.getInetAddresses();
                if (addresses == null) continue;
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (!address.isLoopbackAddress())
                        return address.getHostAddress().toString();
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getNetworkType(Context context) {
        ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conManager == null) return NETWORK_NONE;

        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isAvailable()) return NETWORK_NONE;

        NetworkInfo wifiNet = conManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNet != null && wifiNet.getState() != null) {
            NetworkInfo.State state = wifiNet.getState();
            if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING)
                return NETWORK_WIFI;
        }

        NetworkInfo mobileNet = conManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNet != null && mobileNet.getState() != null) {
            NetworkInfo.State state = mobileNet.getState();
            String subtypeName = networkInfo.getSubtypeName();
            if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                switch (mobileNet.getSubtype()) {
                    case TelephonyManager.NETWORK_TYPE_GPRS: // 联通2g
                    case TelephonyManager.NETWORK_TYPE_CDMA: // 电信2g
                    case TelephonyManager.NETWORK_TYPE_EDGE: // 移动2g
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        return NETWORK_2G;

                    case TelephonyManager.NETWORK_TYPE_EVDO_A: // 电信3g
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        return NETWORK_3G;

                    case TelephonyManager.NETWORK_TYPE_LTE:
                        return NETWORK_4G;

                    default:
                        if ("TD-SCDMA".equalsIgnoreCase(subtypeName)
                                || "WCDMA".equalsIgnoreCase(subtypeName)
                                || "CDMA2000".equalsIgnoreCase(subtypeName))
                            return NETWORK_MOBILE;

                }
            }
        }
        return NETWORK_NONE;
    }

    public static String getIp(Context context) {
        int networkType = getNetworkType(context);
        switch (networkType) {
            case NETWORK_NONE:
                return null;
            case NETWORK_WIFI:
                return getWifiIp(context);
            default:
                return getMobileIp();
        }
    }

    /**
     *
     * @param context
     * @return
     */
    public static String getUserAgent(@Nullable Context context) {
        String userAgent = System.getProperty("http.agent");
        if (TextUtils.isEmpty(userAgent) && context != null)
            userAgent = WebSettings.getDefaultUserAgent(context);
        return userAgent;
    }

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
                    .append(", wifiInfo:").append(getWifiInfo(context))
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

    public static String intIp2StrIp(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }


    public static String getMacByHost(String host) {
        try {
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(InetAddress.getByName(host));
            if (networkInterface == null) return "";
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

    public static boolean isNetConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

}
