// ISecurityCenter.aidl
package com.omottec.demoapp.aidl;

// Declare any non-default types here with import statements

interface ISecurityCenter {
    String encrypt(String plainText);
    String decrypt(String cipherText);
}
