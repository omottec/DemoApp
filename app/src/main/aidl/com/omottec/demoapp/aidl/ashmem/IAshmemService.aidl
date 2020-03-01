package com.omottec.demoapp.aidl.ashmem;

interface IAshmemService {
    int readInt();
    void writeInt(int val);
}