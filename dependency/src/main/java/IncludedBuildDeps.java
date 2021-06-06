import org.gradle.api.Plugin;
import org.gradle.api.Project;

// https://docs.gradle.org/current/userguide/composite_builds.html
public final class IncludedBuildDeps implements Plugin<Project> {
    @Override
    public void apply(Project target) {

    }

    public static final String LOGGER = "com.github.omottec:logger:0.1.2";

    public static final String MULTIDEX = "androidx.multidex:multidex:2.0.0";
    public static final String APPCOMPAT = "androidx.appcompat:appcompat:1.0.0";
    public static final String CARDVIEW = "androidx.cardview:cardview:1.0.0";
    public static final String LEGACY_SUPPORT_V4 = "androidx.legacy:legacy-support-v4:1.0.0";
    public static final String ANNOTATION = "androidx.annotation:annotation:1.0.0";

    public static final String MATERIAL = "com.google.android.material:material:1.0.0";
    public static final String GSON = "com.google.code.gson:gson:2.8.0";

    public static final String MCXIAOKE_VOLLEY = "com.mcxiaoke.volley:library:1.0.18";
    public static final String ANDROID_VOLLEY = "com.android.volley:volley:1.0.0";

    public static final String OKHTTP3 = "com.squareup.okhttp3:okhttp:3.12.0";

    public static final String RETROFIT2_RETROFIT = "com.squareup.retrofit2:retrofit:2.3.0";
    public static final String RETROFIT2_CONVERTER = "com.squareup.retrofit2:converter-gson:2.3.0";
    public static final String RETROFIT2_ADAPTER = "com.squareup.retrofit2:adapter-rxjava:2.3.0";

    public static final String FRESCO_FRESCO = "com.facebook.fresco:fresco:1.2.0";
    public static final String FRESCO_IMAGEPIPELINE = "com.facebook.fresco:imagepipeline-okhttp:0.5.0";

    public static final String GLIDE_AAR = "com.github.bumptech.glide:glide:4.11.0";
    public static final String GLIDE_ANNOTATION_PROCESSOR = "com.github.bumptech.glide:compiler:4.11.0";

    public static final String RXJAVA2 = "io.reactivex.rxjava2:rxjava:2.0.7";
    public static final String RXLIFECYCLE2 = "com.trello.rxlifecycle2:rxlifecycle:2.0.1";
    public static final String RXLIFECYCLE2_ANDROID = "com.trello.rxlifecycle2:rxlifecycle-android:2.1.0";
    public static final String RXLIFECYCLE2_ANDROID_LIFECYCLE = "com.trello.rxlifecycle2:rxlifecycle-android-lifecycle:2.1.0";
    public static final String RXLIFECYCLE2_COMPONENTS = "com.trello.rxlifecycle2:rxlifecycle-components:2.1.0";

    public static final String RXJAVA = "io.reactivex:rxjava:1.2.9";
    public static final String RXANDROID = "io.reactivex:rxandroid:1.2.1";
    public static final String RXLIFECYCLE = "com.trello:rxlifecycle:0.3.1";
    public static final String RXLIFECYCLE_COMPONENTS = "com.trello:rxlifecycle-components:0.3.1";
    public static final String RXPERMISSIONS = "com.tbruyelle.rxpermissions:rxpermissions:0.9.4@aar";


    public static final String LEAKCANARY_ANDROID = "com.squareup.leakcanary:leakcanary-android:1.5";
    public static final String LEAKCANARY_ANDROID_NO_OP = "com.squareup.leakcanary:leakcanary-android-no-op:1.5";

    public static final String AUTO_SERVICE = "com.google.auto.service:auto-service:1.0-rc7";

    public static final String EPIC = "me.weishu:epic:0.11.0";

    public static final String JAVAPOET = "com.squareup:javapoet:1.12.0";

    public static final String ASM_AAR = "org.ow2.asm:asm:7.0";
    public static final String ASM_COMMON_AAR = "org.ow2.asm:asm-commons:7.0";

    public static final String JAVASSIST = "javassist:javassist:3.12.0.GA";
}
