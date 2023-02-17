plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    android()
    jvm("desktop")
    sourceSets {
        val androidMain by named("androidMain") {
            dependencies {
                api(compose.uiTooling)
                api(compose.preview)
            }
        }
        val commonMain by named("commonMain") {
            dependencies {
            }
        }
        val desktopMain by named("desktopMain") {
            dependencies {
                implementation(compose.desktop.common)
            }
        }

        @Suppress("UnusedPrivateMember")
        val jvmMain by creating {
            dependsOn(commonMain)
            androidMain.dependsOn(this)
            desktopMain.dependsOn(this)
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                api(compose.ui)
            }
        }
    }
}
