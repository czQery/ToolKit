group = "cz.qery"
version = "5.2"

kotlin {
    jvmToolchain(21)
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

plugins {
    id("java-library")
    id("com.gradleup.shadow") version "8.3.0"
    id("io.papermc.paperweight.userdev") version "1.7.7"
    kotlin("jvm") version "2.3.0"
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.lunarclient.dev")
    maven("https://api.modrinth.com/maven")
}

dependencies {
    paperweight.paperDevBundle("1.21.1-R0.1-SNAPSHOT") {
        attributes {
            attribute(LibraryElements.LIBRARY_ELEMENTS_ATTRIBUTE, objects.named(LibraryElements.JAR))
        }
    }

    compileOnly("com.lunarclient:apollo-api:1.2.1")
    compileOnly("maven.modrinth:pl3xmap:1.21.5-527")

    implementation("org.bstats:bstats-bukkit:3.1.0")
    implementation("com.google.code.gson:gson:2.13.2")
    implementation(kotlin("stdlib-jdk8"))
}

tasks {
    jar {
        manifest {
            attributes["paperweight-mappings-namespace"] = "mojang"
        }
    }

    shadowJar {
        archiveBaseName.set("ToolKit")
        archiveClassifier.set("")

        manifest {
            attributes["paperweight-mappings-namespace"] = "mojang"
        }

        relocate("org.bstats", "cz.qery.toolkit")
        minimize()
    }

    build {
        dependsOn(shadowJar)
    }

    processResources {
        val props = mapOf("version" to version)
        inputs.properties(props)
        filteringCharset = "UTF-8"

        filesMatching("plugin.yml") {
            expand(props)
        }
    }
}