
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.BOOLEAN
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING

plugins {
    id("android-setup")
    id("multiplatform-compose-setup")
    id("com.squareup.sqldelight")
    id("com.google.firebase.crashlytics")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("com.codingfeline.buildkonfig")
    id("dev.icerock.mobile.multiplatform-resources")
}

android {
    buildTypes {
        create("staging") {
            initWith(getByName("release"))
        }
    }
    namespace = "com.finance_tracker.finance_tracker.common"
}

kotlin {

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by named("commonMain") {
            dependencies {
                api(libs.bundles.odyssey)
                api(libs.napier)
                api(libs.koin.core)

                implementation(libs.bundles.kviewmodel)
                implementation(libs.serialization)
                implementation(libs.sqldelight.coroutines)
                implementation(libs.bundles.ktor)
                implementation(libs.bundles.settings)
                implementation(libs.uuid)
                implementation(libs.datetime)
                implementation(libs.mokoResources.core)
            }
        }
        named("desktopMain") {
            dependencies {
                implementation(libs.sqldelight.jvm)
                implementation(libs.datepicker.desktop)
                implementation(libs.ktor.jvm)
                implementation(libs.slf4j)
                implementation(libs.json)
                implementation(libs.amplitude.java)
            }
        }
        named("androidMain") {
            dependencies {
                implementation(libs.viewModel)
                implementation(libs.bundles.koin.android)
                implementation(libs.sqldelight.android)
                implementation(libs.ktor.android)
                implementation(libs.amplitude.android)

                implementation(project.dependencies.platform(libs.firebase.bom))
                implementation(libs.firebase.crashlytics)
                implementation(libs.lottie)
            }
        }

        @Suppress("UnusedPrivateMember")
        val jvmMain by getting {
            dependencies {
                implementation(libs.koalaplot)
                implementation(libs.paging)
                implementation(libs.mokoResources.compose)
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting

        @Suppress("UnusedPrivateMember")
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
    }
}

sqldelight {
    database("AppDatabase") {
        packageName = "com.finance_tracker.finance_tracker"
        schemaOutputDirectory = file("src/commonMain/sqldelight/com/finance_tracker/finance_tracker/schemas")
        verifyMigrations = true
    }
}

buildkonfig {
    packageName = "com.finance_tracker.finance_tracker"

    defaultConfigs {
        buildConfigField(STRING, "appVersion", rootProject.extra["appVersion"] as String)
    }

    targetConfigs {
        create("desktop") {
            buildConfigField(BOOLEAN, "isDebug", "false")
        }
    }

    targetConfigs("debug") {
        create("desktop") {
            buildConfigField(BOOLEAN, "isDebug", "true")
        }
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "com.finance_tracker.finance_tracker"
    multiplatformResourcesClassName = "MR"
}