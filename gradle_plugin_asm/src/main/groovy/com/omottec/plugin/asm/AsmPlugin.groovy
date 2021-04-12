package com.omottec.plugin.asm

import com.android.build.gradle.AppExtension
import org.gradle.api.Project
import org.gradle.api.Plugin

class AsmPlugin implements Plugin<Project> {

    @Override
    void apply(Project target) {
        println("=================================================================")
        println("AsmPlugin apply $target")
        def extension = target.extensions.getByType(AppExtension)
        extension.registerTransform(new AsmTransform())
        println("=================================================================")
    }
}