package com.omottec.demoapp;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;

public class BuildSrcPlugin implements Plugin<Project> {
    @Override
    public void apply(@NotNull Project target) {
        System.out.println("BuildSrcPlugin apply " + target);
    }
}
