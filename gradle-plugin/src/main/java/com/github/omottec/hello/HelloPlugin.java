package com.github.omottec.hello;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class HelloPlugin implements Plugin<Project> {
    @Override
    public void apply(Project target) {
        System.out.println("HelloPlugin apply...");
    }
}
