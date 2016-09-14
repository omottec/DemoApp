package com.omottec.demoapp.ipc.binderPool;

import android.os.RemoteException;

import com.omottec.demoapp.aidl.ISecurityCenter;

/**
 * Created by qinbingbing on 9/14/16.
 */
public class SecurityCenterImpl extends ISecurityCenter.Stub {
    public static final char SECRET_CHAR = '^';

    @Override
    public String encrypt(String plainText) throws RemoteException {
        char[] chars = plainText.toCharArray();
        int length = chars.length;
        for (int i = 0; i < length; i++) {
            chars[i] ^= SECRET_CHAR;
        }
        return new String(chars);
    }

    @Override
    public String decrypt(String cipherText) throws RemoteException {
        return encrypt(cipherText);
    }
}
