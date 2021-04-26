package com.github.omottec.hello;

import com.android.annotations.NonNull;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class HelloPlugin implements Plugin<Project> {
    @Override
    public void apply(@NonNull Project target) {
        System.out.println("HelloPlugin apply " + target);
    }
}
