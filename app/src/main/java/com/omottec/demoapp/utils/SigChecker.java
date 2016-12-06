package com.omottec.demoapp.utils;

import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by qinbingbing on 4/20/16.
 */
public final class SigChecker {
    private static final String PUBLIC_KEY =
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC2yF1O45xFOJ9vAziLMC4VRcne\n" +
                    "u4ht7vtI5sDffgEjXJE3pIjOkBdsYUothx9lrL3NF1iFmKeTCJ5UZETMF0Jmt4Uc\n" +
                    "A1KSeejx9eE0cDcNt8lQSmu7eMICrtkp0VhWiRPaAkPR8g0TAx+hcsCssLW83t5y\n" +
                    "AmCHXXfBaZ9SFKufdQIDAQAB";

    private RSAPublicKey mPublicKey;

    public SigChecker() {
        loadPublicKey(PUBLIC_KEY);
    }

    private void loadPublicKey(String publicKey) {
        byte[] bytes = Base64.decode(publicKey, Base64.DEFAULT);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
            mPublicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    public boolean checkSig(String response) {
        String sigPattern = "\"sig\":\"(.*?)\"";
        Matcher matcher = Pattern.compile(sigPattern).matcher(response);
        if (!matcher.find()) return false;
        String sig = matcher.group(1);

        String httpPattern = "\"httpdomains\":(.*?])";
        matcher = Pattern.compile(httpPattern).matcher(response);
        if (!matcher.find()) return false;
        String httpDomains = matcher.group(1);

        String pushPattern = "\"pushDomains\":(.*?])";
        matcher = Pattern.compile(pushPattern).matcher(response);
        if (!matcher.find()) return false;
        String pushDomains = matcher.group(1);

        if (httpDomains.isEmpty() || pushDomains.isEmpty() || sig.isEmpty()) return false;
        String md5 = getMd5(httpDomains + pushDomains);
        if (md5 == null) return false;
        byte[] decryptBytes = decrypt(mPublicKey, Base64.decode(sig, Base64.DEFAULT));
        if (decryptBytes == null) return false;
        String decryptedMd5 = new String(decryptBytes);
        return md5.equals(decryptedMd5);
    }

    public static byte[] decrypt(PublicKey publicKey, byte[] cipherData) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return cipher.doFinal(cipherData);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getMd5(String src) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(src.getBytes());
            byte[] bytes = md.digest();
            return bytesToHexStr(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String bytesToHexStr(byte[] bytes){
        char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes)
            sb.append(hexChars[b >>> 4 & 0xf]).append(hexChars[b & 0xf]);
        return sb.toString();
    }


    public PublicKey getPublicKey() {
        return mPublicKey;
    }

}
