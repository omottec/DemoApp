package com.omottec.demoapp.app.fresco;

import com.facebook.common.util.ByteConstants;

/**
 * Created by omottec on 29/11/2017.
 */

public class ConfigConstants {
    private static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();

    public static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 4;
    public static final int MAX_DISK_CACHE_SIZE = 128 * ByteConstants.MB;
}
