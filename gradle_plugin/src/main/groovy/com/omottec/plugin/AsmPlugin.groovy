package com.omottec.plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Project
import org.gradle.api.Plugin

class AsmPlugin implements Plugin<Project> {

    @Override
    void apply(Project target) {
        println("=================================================================")
        println("this is asm plugin")
        def extension = target.extensions.getByType(AppExtension)
        extension.registerTransform(new AsmTransform())
        println("=================================================================")
    }
}