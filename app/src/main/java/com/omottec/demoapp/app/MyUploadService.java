package com.omottec.demoapp.app;


import com.facebook.profilo.config.SystemControlConfig;
import com.facebook.profilo.core.BackgroundUploadService;

import java.io.File;
import java.util.List;

public class MyUploadService implements BackgroundUploadService {
    @Override
    public void uploadInBackground(List<File> list, BackgroundUploadListener backgroundUploadListener) {

    }

    @Override
    public void uploadInBackgroundSkipChecks(List<File> list, BackgroundUploadListener backgroundUploadListener) {

    }

    @Override
    public boolean canUpload() {
        return false;
    }

    @Override
    public void updateConstraints(SystemControlConfig systemControlConfig) {

    }
}
