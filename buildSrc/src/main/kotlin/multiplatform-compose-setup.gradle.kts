plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("multiplatform-config")
}

kotlin {
    sourceSets {
        named("androidMain") {
            dependencies {
                api(compose.uiTooling)
                api(compose.preview)
            }
        }

        named("desktopMain") {
            dependencies {
                implementation(compose.desktop.common)
            }
        }

        named("jvmMain") {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                api(compose.ui)
            }
        }
    }
}
