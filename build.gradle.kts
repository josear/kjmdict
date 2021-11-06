import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
    kotlin("plugin.serialization") version "1.5.31"
}

group = "jp.rodriguez.jmdict"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.pdvrieze.xmlutil:core:0.84.0-RC1")
    implementation("io.github.pdvrieze.xmlutil:serialization:0.84.0-RC1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")

    testImplementation("org.jetbrains.kotlin:kotlin-reflect:1.5.31")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.5.31")
}

tasks.test {
    useJUnitPlatform()
    jvmArgs = listOf("-Djdk.xml.entityExpansionLimit=0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "16"
}