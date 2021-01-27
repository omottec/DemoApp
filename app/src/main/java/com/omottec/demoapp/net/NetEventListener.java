package com.omottec.demoapp.net;


import com.omottec.logger.Logger;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.EventListener;
import okhttp3.Handshake;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by qinbingbing on 20/12/2018.
 */

public class NetEventListener extends EventListener {
    public static final String TAG = "NetEventListener";

    @Override
    public void callStart(Call call) {
        Logger.d(TAG, "callStart call:" + call);
    }

    @Override
    public void dnsStart(Call call, String domainName) {
        Logger.d(TAG, "dnsStart call:" + call + ", domainName:" + domainName);
    }

    @Override
    public void dnsEnd(Call call, String domainName, List<InetAddress> inetAddressList) {
        Logger.d(TAG, "dnsEnd call:" + call
                + ", domainName:" + domainName
                + ", inetAddressList:" + Arrays.toString(inetAddressList.toArray()));
    }

    @Override
    public void connectStart(Call call, InetSocketAddress inetSocketAddress, Proxy proxy) {
        Logger.d(TAG, "connectStart call:" + call
                + ", inetSocketAddress:"  + inetSocketAddress
                + ", proxy:" + proxy);
    }

    @Override
    public void secureConnectStart(Call call) {
        Logger.d(TAG, "secureConnectStart call:" + call);
    }

    @Override
    public void secureConnectEnd(Call call, Handshake handshake) {
        Logger.d(TAG, "secureConnectEnd call:" + call + ", handshake:" + handshake);
    }

    @Override
    public void connectEnd(Call call, InetSocketAddress inetSocketAddress, Proxy proxy, Protocol protocol) {
        Logger.d(TAG, "connectEnd call:" + call
                + ", inetSocketAddress:" + inetSocketAddress
                + ", proxy:" + proxy
                + ", protocol:" + protocol);
    }

    @Override
    public void connectFailed(Call call, InetSocketAddress inetSocketAddress, Proxy proxy, Protocol protocol, IOException ioe) {
        Logger.d(TAG, "connectFailed call:" + call
                + ", inetSocketAddress:" + inetSocketAddress
                + ", proxy:" + proxy
                + ", protocol:" + protocol
                + ", ioe:" + ioe);
        super.connectFailed(call, inetSocketAddress, proxy, protocol, ioe);
    }

    @Override
    public void connectionAcquired(Call call, Connection connection) {
        Logger.d(TAG, "connectionAcquired call:" + call + ", connection:" + connection);
    }

    @Override
    public void connectionReleased(Call call, Connection connection) {
        Logger.d(TAG, "connectionReleased call:" + call + ", connection:" + connection);
    }

    @Override
    public void requestHeadersStart(Call call) {
        Logger.d(TAG, "requestHeadersStart call:" + call);
        super.requestHeadersStart(call);
    }

    @Override
    public void requestHeadersEnd(Call call, Request request) {
        Logger.d(TAG, "requestHeadersEnd call:" + call + ", request:" + request);
    }

    @Override
    public void requestBodyStart(Call call) {
        Logger.d(TAG, "requestBodyStart call:" + call);
    }

    @Override
    public void requestBodyEnd(Call call, long byteCount) {
        Logger.d(TAG, "requestBodyEnd call:" + call + ", byteCount:" + byteCount);
    }

    @Override
    public void responseHeadersStart(Call call) {
        Logger.d(TAG, "responseHeadersStart call:" + call);
    }

    @Override
    public void responseHeadersEnd(Call call, Response response) {
        Logger.d(TAG, "responseHeadersEnd call:" + call + ", response:" + response);
    }

    @Override
    public void responseBodyStart(Call call) {
        Logger.d(TAG, "responseBodyStart call:" + call);
    }

    @Override
    public void responseBodyEnd(Call call, long byteCount) {
        Logger.d(TAG, "responseBodyEnd call:" + call + ", byteCount:" + byteCount);
    }

    @Override
    public void callEnd(Call call) {
        Logger.d(TAG, "callEnd call:" + call);
    }

    @Override
    public void callFailed(Call call, IOException ioe) {
        Logger.d(TAG, "callFailed call:" + call + ", ioe:" + ioe);
    }
}
