plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false

    // klibs - core
    alias(klibs.plugins.klibs.gradle.detekt) apply false
    alias(klibs.plugins.klibs.gradle.detekt.compose) apply false
    alias(klibs.plugins.klibs.gradle.dokka.root) apply false
    alias(klibs.plugins.klibs.gradle.dokka.module) apply false
    alias(klibs.plugins.klibs.gradle.java.core) apply false
    alias(klibs.plugins.klibs.gradle.stub.javadoc) apply false
    alias(klibs.plugins.klibs.gradle.publication) apply false
    alias(klibs.plugins.klibs.gradle.rootinfo) apply false
    // klibs - minecraft
    alias(klibs.plugins.klibs.gradle.minecraft.empty) apply false
    alias(klibs.plugins.klibs.gradle.minecraft.multiplatform) apply false
}

apply(plugin = "ru.astrainteractive.gradleplugin.dokka.root")
apply(plugin = "ru.astrainteractive.gradleplugin.detekt")
apply(plugin = "ru.astrainteractive.gradleplugin.root.info")

subprojects.forEach {
    it.apply(plugin = "ru.astrainteractive.gradleplugin.dokka.module")
    if (it.name != "bukkit" && it.name != "benchmarks") {
        it.apply(plugin = "ru.astrainteractive.gradleplugin.publication")
    }
    it.plugins.withId("org.jetbrains.kotlin.jvm") {
        it.apply(plugin = "ru.astrainteractive.gradleplugin.java.core")
    }
}
