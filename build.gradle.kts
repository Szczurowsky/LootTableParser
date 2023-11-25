plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "pl.szczurowsky"
version = "1.0"

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.szczurowsky.pl/releases")
    }
    maven {
        name = "CodeMC"
        url = uri("https://repo.codemc.io/repository/maven-public/")
    }
}

dependencies {
    implementation("com.google.code.gson:gson:2.8.8")
    implementation("de.tr7zw:item-nbt-api:2.12.1")
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains:annotations:24.0.0")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>{
    relocate("de.tr7zw.changeme.nbtapi", "pl.szczurowsky.loottableparser.lib.nbtapi")
    relocate("com.google.gson", "pl.szczurowsky.loottableparser.lib.gson")
}


tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.getByName<Jar>("jar") {
    dependsOn("test")
}

java {
    withSourcesJar()
    withJavadocJar()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}