plugins {
    id("com.android.library")
    id("kotlin-multiplatform")
}

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    jvm("desktop")

    sourceSets {
        val commonMain by getting

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        create("iosMain") {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }

        val androidMain by getting
        val desktopMain by getting
        create("jvmMain") {
            dependsOn(commonMain)
            androidMain.dependsOn(this)
            desktopMain.dependsOn(this)
        }
    }
}
