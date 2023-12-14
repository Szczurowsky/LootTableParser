plugins {
    id("java")
    id("maven-publish")
}

group = "pl.szczurowsky"
version = "1.0.1"

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
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("de.tr7zw:item-nbt-api:2.12.2")
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains:annotations:24.1.0")

    testImplementation(platform("org.junit:junit-bom:5.10.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
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

publishing {
    publications {
        create<MavenPublication>("library") {

            from(components.getByName("java"))

            pom.withXml {
                val repositories = asNode().appendNode("repositories")
                project.repositories.findAll(closureOf<Any> {
                    if (this is MavenArtifactRepository && this.url.toString().startsWith("https")) {
                        val repository = repositories.appendNode("repository")
                        repository.appendNode("id", this.url.toString().replace("https://", "").replace("/", "-").replace(".", "-").trim())
                        repository.appendNode("url", this.url.toString().trim())
                    }
                })
            }
        }
    }
    repositories {
        maven {
            name = "szczurowsky"
            url = uri("https://repo.szczurowsky.pl/${if (version.toString().endsWith("-SNAPSHOT")) "snapshots" else "releases"}")
            credentials {
                username = findProperty(name + "Username")?.let { it as String } ?: System.getenv(name.toUpperCase() + "_USERNAME")
                password = findProperty(name + "Password")?.let { it as String } ?: System.getenv(name.toUpperCase() + "_PASSWORD")
            }
        }
    }
}