package com.omottec.demoapp.fresco;

import com.facebook.common.memory.MemoryTrimType;
import com.facebook.common.memory.MemoryTrimmable;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.utils.Logger;

/**
 * Created by qinbingbing on 11/02/2018.
 */

public class MyMemoryTrimmable implements MemoryTrimmable {
    @Override
    public void trim(MemoryTrimType trimType) {
        Logger.d(Tag.FRESCO, "MemoryTrimmable trim: " + trimType.name());
    }
}
