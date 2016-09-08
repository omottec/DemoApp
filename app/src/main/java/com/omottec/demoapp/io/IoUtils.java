package com.omottec.demoapp.io;

import android.os.Environment;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by qinbingbing on 4/1/16.
 */
public final class IoUtils {
    private IoUtils() {}

    public static void close(Closeable... closeables) {
        if (closeables == null || closeables.length == 0) return;
        for (Closeable closeable : closeables)
            if (closeable != null)
                try {
                    closeable.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
    }

    public static void write2ExternalStorage() {
        File externalStorageDir = Environment.getExternalStorageDirectory();
        File destFile = new File(externalStorageDir, "dest");
        OutputStream os = null;
        try {
            os = new FileOutputStream(destFile);
            os.write("omottec".getBytes());
            os.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
