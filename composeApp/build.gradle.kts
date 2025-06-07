import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.23"
}

kotlin {
    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(compose.materialIconsExtended)

            implementation("io.ktor:ktor-client-core")
            implementation("io.ktor:ktor-client-cio")
            implementation("io.ktor:ktor-client-content-negotiation")
            implementation("io.ktor:ktor-serialization-kotlinx-json")
            implementation("io.ktor:ktor-utils")

            implementation("io.ktor:ktor-client-core:2.3.11")
            implementation("io.ktor:ktor-client-cio:2.3.11")
            implementation("org.jetbrains.skiko:skiko-awt-runtime-windows-x64:0.7.61")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)

            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
            implementation("org.slf4j:slf4j-simple:2.0.12")
        }
    }
}

configurations.all {
    resolutionStrategy {
        force("io.ktor:ktor-client-core:2.3.13")
        force("io.ktor:ktor-client-cio:2.3.13")
        force("io.ktor:ktor-client-content-negotiation:2.3.13")
        force("io.ktor:ktor-serialization-kotlinx-json:2.3.13")
        force("io.ktor:ktor-utils:2.3.13")
    }
}

compose.desktop {
    application {
        mainClass = "org.dvulist.booksearch.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.dvulist.booksearch"
            packageVersion = "1.0.0"
        }
    }
}