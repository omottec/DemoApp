package com.omottec.plugin

import org.gradle.api.Project
import org.gradle.api.Plugin

public class DemoPlugin implements Plugin<Project> {

    @Override
    void apply(Project target) {
        println("=================================================================")
        println("this is a demo plugin")
        println("=================================================================")
    }
}