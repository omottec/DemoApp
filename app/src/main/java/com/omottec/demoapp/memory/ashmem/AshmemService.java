package com.omottec.demoapp.memory.ashmem;

import android.os.MemoryFile;
import android.os.RemoteException;

import com.omottec.demoapp.Tag;
import com.omottec.demoapp.aidl.ashmem.IAshmemService;
import com.omottec.demoapp.utils.Logger;

import java.io.IOException;

public class AshmemService extends IAshmemService.Stub {
    private MemoryFile mMemoryFile;

    public AshmemService() {
        try {
            mMemoryFile = new MemoryFile("Ashmem", 4);
        } catch (IOException e) {
            Logger.e(Tag.ASHMEM, e);
        }
    }

    @Override
    public int readInt() throws RemoteException {
        if (mMemoryFile == null) return -1;
        byte[] buffer = new byte[4];
        try {
            mMemoryFile.readBytes(buffer, 0, 0, 4);
            int value = (buffer[0] << 24) | (buffer[1] << 16) | (buffer[2] << 8) | buffer[3];
            Logger.i(Tag.ASHMEM, "read int " + value + " from memory file");
            return value;
        } catch (IOException e) {
            Logger.e(Tag.ASHMEM, e);
        }
        return -1;
    }

    @Override
    public void writeInt(int val) throws RemoteException {
        if (mMemoryFile == null) return;
        byte[] buffer = new byte[4];
        buffer[0] = (byte) (val >>> 24);
        buffer[1] = (byte) ((val >>> 16) & 0xFF);
        buffer[2] = (byte) ((val >>> 8) & 0xFF);
        buffer[3] = (byte) (val & 0xFF);
        try {
            mMemoryFile.writeBytes(buffer, 0, 0, 4);
            Logger.i(Tag.ASHMEM, "write int " + val + " to memory file");
        } catch (IOException e) {
            Logger.e(Tag.ASHMEM, e);
        }
    }
}
