import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform


plugins {
    id("com.android.library")
    id("kotlin-parcelize")
}

android {
    compileSdk = 33

    defaultConfig {
        minSdk = 26
        targetSdk = 33
    }

    sourceSets {
        named("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            if (System.getenv("CI") != null) {
                res.srcDirs("src/commonMain/resources")
            } else {
                val os = DefaultNativePlatform.getCurrentOperatingSystem()
                // "Crutch" due to a bug in Gradle up to version 8.0.0 not inclusive.
                // Without this, MokoResources throws an "Unresolved reference:" error
                when {
                    os.isMacOsX -> {
                        res.srcDirs(
                            "src/commonMain/resources",
                            File(buildDir, "generated/moko/androidMain/res")
                        )
                        assets.srcDir(
                            File(buildDir, "generated/moko/androidMain/assets")
                        )
                    }
                    os.isWindows -> {
                        res.srcDirs("src/androidMain/res", "src/commonMain/resources")
                    }
                }
            }
        }
    }
}