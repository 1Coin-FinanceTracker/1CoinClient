// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript{
    repositories {
        google()
    }
    dependencies {
        classpath("com.google.gms:google-services:4.3.14")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.2")
        classpath("com.squareup.sqldelight:gradle-plugin:1.5.3")
    }
}

plugins {
    id("com.android.application") version "7.3.0" apply false
    id("com.android.library") version "7.3.0" apply false
    id("org.jetbrains.kotlin.android") version "1.7.10" apply false
    id("io.gitlab.arturbosch.detekt") version "1.21.0"
}