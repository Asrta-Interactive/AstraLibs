plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("com.github.johnrengelman.shadow")
    id("org.jetbrains.dokka")
    id("convention.publication")
    id("convention.library")
}
dependencies {
    testImplementation("org.testng:testng:7.1.0")
    // Kotlin
    compileOnly(libs.kotlinGradlePlugin)
    // Coroutines
    compileOnly(libs.coroutines.coreJvm)
    compileOnly(libs.coroutines.core)
    // Serialization
    compileOnly(libs.kotlin.serialization)
    compileOnly(libs.kotlin.serializationJson)
    compileOnly(libs.kotlin.serializationKaml)
    // Test-Core
    testImplementation(kotlin("test-junit5"))
    testImplementation(platform(libs.junit.bom))
    testImplementation("org.junit.jupiter:junit-jupiter")
    // Test-libs
    testImplementation(libs.coroutines.core)
    testImplementation(libs.coroutines.coreJvm)
    testImplementation(libs.xerial.sqlite.jdbc)
    testImplementation("mysql:mysql-connector-java:8.0.32")
    implementation(project(":ktx-core"))
}