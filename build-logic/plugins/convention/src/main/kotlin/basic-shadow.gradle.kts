

plugins {
    java
    `java-library`
    id("org.jetbrains.kotlin.jvm")
    id("com.github.johnrengelman.shadow")
}
tasks.shadowJar {
    isReproducibleFileOrder = true
    mergeServiceFiles()
    dependsOn(configurations)
    archiveClassifier.set(null as String?)
    from(sourceSets.main.get().output)
    from(project.configurations.runtimeClasspath)
    minimize()
    archiveBaseName.set(libs.versions.project.name.get())
    destinationDirectory.set(File(libs.versions.destionation.spigot.get()))
}
