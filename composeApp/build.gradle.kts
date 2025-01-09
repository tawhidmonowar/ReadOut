import com.codingfeline.buildkonfig.compiler.FieldSpec
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.buildkonfig)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    id("com.vanniktech.maven.publish") version "0.29.0"
    id("com.google.osdetector") version "1.7.3"
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_22)
        }
    }

    jvm("desktop")

    room {
        schemaDirectory("$projectDir/schemas")
    }

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
            implementation(libs.ktor.client.android)
            implementation(libs.bundles.exoplayer)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.material3AdaptiveNavigationSuite)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.lifecycle.viewmodel)

            implementation(libs.navigation.compose)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.window.size)

            api(libs.datastore.preferences)
            api(libs.datastore)
            api(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            implementation(libs.bundles.ktor)
            implementation(libs.bundles.coil)

            implementation(libs.androidx.room.runtime)
            implementation(libs.sqlite.bundled)

        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            val fxSuffix = when (osdetector.classifier) {
                "linux-x86_64" -> "linux"
                "linux-aarch_64" -> "linux-aarch64"
                "windows-x86_64" -> "win"
                "osx-x86_64" -> "mac"
                "osx-aarch_64" -> "mac-aarch64"
                else -> throw IllegalStateException("Unknown OS: ${osdetector.classifier}")
            }
            implementation("org.openjfx:javafx-base:19:${fxSuffix}")
            implementation("org.openjfx:javafx-graphics:19:${fxSuffix}")
            implementation("org.openjfx:javafx-controls:19:${fxSuffix}")
            implementation("org.openjfx:javafx-swing:19:${fxSuffix}")
            implementation("org.openjfx:javafx-web:19:${fxSuffix}")
            implementation("org.openjfx:javafx-media:19:${fxSuffix}")
        }

        dependencies {
            ksp(libs.androidx.room.compiler)
        }
    }
}

android {
    namespace = "org.tawhid.readout"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.tawhid.readout"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_22
        targetCompatibility = JavaVersion.VERSION_22
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "org.tawhid.readout.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.tawhid.readout"
            packageVersion = "1.0.0"
        }
    }
}

buildkonfig {
    packageName = "org.tawhid.readout"
    val localProperties =
        Properties().apply {
            val propsFile = rootProject.file("local.properties")
            if (propsFile.exists()) {
                load(propsFile.inputStream())
            }
        }
    defaultConfigs {
        buildConfigField(
            FieldSpec.Type.STRING,
            "GEMINI_API_KEY", localProperties["GEMINI_API_KEY"]?.toString() ?: "",
        )
        buildConfigField(
            FieldSpec.Type.STRING,
            "CLOUD_TEXT_TO_SPEECH_API_KEY", localProperties["CLOUD_TEXT_TO_SPEECH_API_KEY"]?.toString() ?: "",
        )
    }
}

task("testClasses") {}
tasks.withType<JavaExec> {
    jvmArgs = listOf(
        "--add-modules",
        "javafx.controls,javafx.fxml",
        "--add-opens",
        "javafx.graphics/javafx.scene=ALL-UNNAMED",
        "--add-opens",
        "javafx.graphics/com.sun.javafx.scene=ALL-UNNAMED",
        "--add-opens",
        "javafx.graphics/com.sun.javafx.stage=ALL-UNNAMED"
    )
}