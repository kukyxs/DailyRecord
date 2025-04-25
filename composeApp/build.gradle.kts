import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.compose.reload.ComposeHotRun
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.swiftklib)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.hotreload)
}

compose {
    resources {
        packageOfResClass = "com.kuky.dailyrecord.composeapp.generated.resources"
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

composeCompiler {
    featureFlags.add(ComposeFeatureFlag.OptimizeNonSkippingGroups)
}

ksp {
    arg("KOIN_DEFAULT_MODULE", "false")
}

//noinspection WrongGradleMethod
kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            linkerOpts.add("-lsqlite3")
        }
    }

    cocoapods {
        version = "1.0"
        group = "com.kuky.dailyrecord"
    }

    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.coil.okhttp)
        }

        commonMain {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(libs.androidx.lifecycle.viewmodel)
                implementation(libs.androidx.lifecycle.runtime.compose)
                implementation(libs.kotlinx.datetime)
                implementation(libs.datastore)
                implementation(libs.room.runtime)
                implementation(libs.sqlite.bundled)

                implementation(libs.bundles.koin)
                implementation(libs.bundles.voyager)
                implementation(libs.bundles.coil)
            }
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }

//    sourceSets.named("commonMain").configure {
//        kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
//    }

    dependencies {
        ksp(libs.room.compiler)
        ksp(libs.koin.annotation.compiler)
        add("kspCommonMainMetadata", libs.koin.annotation.compiler)

        listOf(
            "kspAndroid",
            "kspIosSimulatorArm64",
            "kspIosX64",
            "kspIosArm64",
            "kspDesktop"
        ).forEach {
            add(it, libs.room.compiler)
            add(it, libs.koin.annotation.compiler)
        }
    }
}

android {
    namespace = "com.kuky.dailyrecord"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.kuky.dailyrecord"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "com.kuky.dailyrecord.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.kuky.dailyrecord"
            packageVersion = "1.0.0"
        }
    }
}

project.tasks.withType(KotlinCompilationTask::class.java).configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}

project.afterEvaluate {
    tasks.named("kspKotlinIosSimulatorArm64") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
    tasks.named("kspKotlinIosX64") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
    tasks.named("kspKotlinIosArm64") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
    tasks.named("kspDebugKotlinAndroid") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
    tasks.named("kspKotlinDesktop") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}

tasks.register<ComposeHotRun>("runHot") {
    mainClass.set("com.kuky.dailyrecord.MainKt")
}