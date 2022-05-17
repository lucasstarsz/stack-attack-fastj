/*
 * FastJ Kotlin Template Build Script
 *
 * Thank you for choosing FastJ! This is a Gradle-based build script that will help you manage your
 * project with ease. */

plugins {
    java
    application
    id("org.beryx.jlink") version "2.25.0"
}

group = "tech.fastj"
version = "0.0.1"
description = "A stacking game made in FastJ."

application.mainClass.set("tech.fastj.stackattack.StackAttack")
application.mainModule.set("Stack.Attack.main")


/* When you add a dependency on another project (like FastJ), you need to add specify where the
 * dependencies are coming from!
 * FastJ is hosted on Maven Central and Jitpack.io, so we"ll add the jitpack.io dependency here. */
repositories.maven {
    setUrl("https://jitpack.io/")
}
repositories.mavenCentral()

/* The dependency for FastJ, the game engine this template depends on. */
dependencies.implementation("com.github.fastjengine:FastJ:edfadb65f1")
/* We'll stick with the simplest logging option for now -- you can change it however you need. */
dependencies.implementation("org.slf4j:slf4j-simple:2.0.0-alpha7")

java {
    modularity.inferModulePath.set(true)
}

/* To make Kotlin compile and run properly with Gradle, this adds your Kotlin code to the Java
 * source sets. */
sourceSets.main {
    java.srcDirs("src/main/java")
}

/* The Runtime plugin is used to configure the executables and other distributions for your
 * project. */
jlink {

    options.addAll(
        "--strip-debug",
        "--no-header-files",
        "--no-man-pages",
        "--compress", "1"
    )

    launcher {
        noConsole = false
    }

    forceMerge("slf4j-api", "slf4j-simple")

    jpackage {
        /* Use this to define the path of the icons for your project. */
        val iconPath = "project-resources/fastj_icon"
        val currentOs = org.gradle.internal.os.OperatingSystem.current()

        addExtraDependencies("slf4j")

        installerOptions.addAll(listOf(
            "--name",  "Stack Attack",
            "--description", project.description as String,
            "--vendor", project.group as String,
            "--app-version", project.version as String,
            "--license-file",  "$rootDir/LICENSE.md",
            "--copyright",  "Copyright (c) 2022 Andrew Dey",
            "--vendor",  "Andrew Dey"
        ))

        when {
            currentOs.isWindows -> {
                installerType = "msi"
                imageOptions = listOf("--icon", "${iconPath}.ico")
                installerOptions.addAll(listOf(
                    "--win-per-user-install",
                    "--win-dir-chooser",
                    "--win-shortcut"
                ))
            }
            currentOs.isLinux -> {
                installerType = "deb"
                imageOptions = listOf("--icon", "${iconPath}.png")
                installerOptions.add("--linux-shortcut")
            }
            currentOs.isMacOsX -> {
                installerType = "pkg"
                imageOptions = listOf("--icon", "${iconPath}.icns")
                installerOptions.addAll(listOf(
                    "--mac-package-name", project.name
                ))
            }
        }
    }
}

tasks.named("jpackageImage") {
    doLast {
        copy {
            from("audio").include("*.*")
            into("$buildDir/jpackage/Stack Attack/audio")
        }
        copy {
            from("img").include("*.*")
            into("$buildDir/jpackage/Stack Attack/img")
        }
        delete(fileTree("$buildDir/jpackage/Stack Attack/runtime") {
            include("release", "bin/api**.dll", "bin/Stack Attack**", "lib/jrt-fs.jar")
        })
    }
}

