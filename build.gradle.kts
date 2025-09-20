@file:Suppress("ImplicitThis", "UnstableApiUsage")

import net.neoforged.moddevgradle.dsl.RunModel
import org.slf4j.event.Level

plugins {
    id("net.neoforged.moddev") version "2.0.107"
    kotlin("jvm") version "2.2.0"
    id("org.jetbrains.dokka-javadoc") version "2.0.0"
    id("idea")
    `maven-publish`
    `java-library`
    signing
}

group = project.properties["mod_group_id"] as String
version = project.properties["mod_version"] as String

private fun getModId(): String = project.properties["mod_id"] as String
private fun RunModel.enableTestNamespaces(): Unit = systemProperty("neoforge.enabledGameTestNamespaces", getModId())
private fun mcVersion(): String = project.properties["minecraft_version"] as String

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}

base {
    archivesName = getModId()
}

repositories {
    mavenCentral()
    mavenLocal()
    maven {
        name = "JEI Maven"
        url = uri("https://maven.blamejared.com/")
    }
    maven {
        name = "JEI Backup Maven / ModMaven"
        url = uri("https://modmaven.dev")
    }
    maven {
        name = "CurseForge Maven"
        url = uri("https://www.cursemaven.com")
        content { includeGroup("curse.maven") }
    }
}

neoForge {
    version = project.properties["neo_version"] as String

    parchment {
        minecraftVersion = mcVersion()
        mappingsVersion = "2024.11.17"
    }

    runs {
        create("client") {
            client()
            enableTestNamespaces()
            devLogin = true
        }
        create("server") {
            server()
            programArgument("--nogui")
            enableTestNamespaces()
        }
        create("gameTestServer") {
            type = "gameTestServer"
            enableTestNamespaces()
        }
        create("data") {
            data()
            programArguments.addAll(
                "--mod", getModId(),
                "--all",
                "--output", file("src/generated/resources/").absolutePath,
                "--existing", file("src/main/resources/").absolutePath
            )
        }
        configureEach {
            // Logging data for a UserDev environment
            // "SCAN": For mods scan.
            // "REGISTRIES": For firing of registry events.
            // "REGISTRYDUMP": For getting the contents of all registries.
            // systemProperty 'forge.logging.markers', 'REGISTRIES'
            logLevel = Level.INFO
            additionalRuntimeClasspathConfiguration.dependencies.add(
                dependencies.create("org.jetbrains.kotlin:kotlin-stdlib:2.2.0-RC3") { isTransitive = false }
            )
            additionalRuntimeClasspathConfiguration.dependencies.add(
                dependencies.create("org.jetbrains.kotlin:kotlin-reflect:2.2.0-RC2") { isTransitive = false }
            )
            additionalRuntimeClasspathConfiguration.dependencies.add(
                dependencies.create("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.2.0-RC3") { isTransitive = false }
            )
            additionalRuntimeClasspathConfiguration.dependencies.add(
                dependencies.create("org.jetbrains.kotlin:kotlin-stdlib-jdk7:2.2.0-RC3") { isTransitive = false }
            )
        }
    }

    mods {
        create(getModId()) {
            sourceSet(sourceSets.main.get())
        }
    }
}

dependencies {
    // Mod Dependencies //
    jarJar(implementation("org.jetbrains.kotlin:kotlin-stdlib:2.2.0-RC3") {})
    jarJar(implementation("org.jetbrains.kotlin:kotlin-reflect:2.2.0-RC2") {})
    jarJar(implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.2.0-RC3") {})
    jarJar(implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:2.2.0-RC3") {})
    // Mod Compatibility //
    // Jade (WAILA)
    implementation("curse.maven:jade-324717:5976517")
    // Just Enough Items (JEI)
    val jeiVersion = "19.21.2.313"
    compileOnly("mezz.jei:jei-${mcVersion()}-neoforge-api:${jeiVersion}")
    runtimeOnly("mezz.jei:jei-${mcVersion()}-neoforge:${jeiVersion}")
    // WorldEdit
    runtimeOnly("curse.maven:worldedit-225608:5830452")
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        freeCompilerArgs.add("-Xcontext-parameters")
    }
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.register<ProcessResources>("generateModMetadata")
tasks.named<ProcessResources>("generateModMetadata") {
    val replaceProperties = mapOf(
        "minecraft_version" to "${project.properties["minecraft_version"]}",
        "minecraft_version_range" to "${project.properties["minecraft_version_range"]}",
        "neo_version" to "${project.properties["neo_version"]}",
        "neo_version_range" to "${project.properties["neo_version_range"]}",
        "loader_version_range" to "${project.properties["loader_version_range"]}",
        "mod_id" to "${project.properties["mod_id"]}",
        "mod_name" to "${project.properties["mod_name"]}",
        "mod_license" to "${project.properties["mod_license"]}",
        "mod_version" to "${project.properties["mod_version"]}",
        "mod_authors" to "${project.properties["mod_authors"]}",
        "mod_description" to "${project.properties["mod_description"]}",
    )
    inputs.properties(replaceProperties)
    expand(replaceProperties)
    from("src/main/templates/")
    into("build/generated/sources/modMetadata")
    duplicatesStrategy = DuplicatesStrategy.WARN
}

sourceSets.main.get().resources {
    srcDirs("src/generated/resources", tasks["generateModMetadata"])
}

neoForge.ideSyncTask(tasks["generateModMetadata"])