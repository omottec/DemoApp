package com.omottec.demoapp.hook;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.omottec.demoapp.Tag;
import com.omottec.logger.Logger;

public class ProxyResources extends Resources {
    private Resources mAppResource;

    public ProxyResources(Resources resources) {
        super(resources.getAssets(), resources.getDisplayMetrics(), resources.getConfiguration());
        mAppResource = resources;
    }

    @NonNull
    @Override
    public CharSequence getText(int id) throws NotFoundException {
        logRes(id);
        return super.getText(id);
    }

    @Override
    public Drawable getDrawable(int id, @Nullable Theme theme) throws NotFoundException {
        logRes(id);
        return super.getDrawable(id, theme);
    }

    @Override
    public int getColor(int id, @Nullable Theme theme) throws NotFoundException {
        logRes(id);
        return super.getColor(id, theme);
    }

    private void logRes(int id) {
        String resourceName = mAppResource.getResourceName(id);
        String resourcePackageName = mAppResource.getResourcePackageName(id);
        String resourceTypeName = mAppResource.getResourceTypeName(id);
        String resourceEntryName = mAppResource.getResourceEntryName(id);
        Logger.i(Tag.REPLACE_RES,
            new StringBuilder("resourceName:").append(resourceName)
                .append(", resourcePackageName:").append(resourcePackageName)
                .append(", resourceTypeName:").append(resourceTypeName)
                .append(", resourceEntryName:").append(resourceEntryName)
                .toString());
    }



}
