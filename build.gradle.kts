import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.5.30-M1"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "dev.thelecrafter.plugins"
version = "1.0.0-Pre1"

repositories {
    mavenCentral()
    maven("https://libraries.minecraft.net")
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")
    maven("https://mvn.intellectualsites.com/content/groups/public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
    compileOnly("com.mojang:authlib:1.5.25")
    implementation("org.reflections:reflections:0.9.11")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.21")
    implementation(kotlin("stdlib-jdk8"))
    compileOnly("com.fastasyncworldedit:FAWE-Bukkit:1.17-47") { isTransitive = false }
    compileOnly("com.fastasyncworldedit:FAWE-Core:1.17-47")
}

tasks.shadowJar {
    minimize()
    dependsOn(tasks["relocateShadowJar"])
}

tasks.create<com.github.jengelman.gradle.plugins.shadow.tasks.ConfigureShadowRelocation>("relocateShadowJar") {
    target = tasks["shadowJar"] as com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
    prefix = "masksofeternity"
}

tasks.processResources {
    inputs.property("version", project.version)
    filesMatching("plugin.yml") {
        expand("version" to project.version)
    }
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "16"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "16"
}