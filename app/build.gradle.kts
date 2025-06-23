import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.usatov.nanithometask"
    compileSdk = libs.versions.compileSdk.get().toInt()
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }

    defaultConfig {
        applicationId = "com.usatov.nanithometask"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = getVersionCode()
        versionName = getVersionName()

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
    implementation(project(":feature:connect"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(platform(libs.compose.bom))

    implementation(libs.compose.ui)
    implementation(libs.compose.material3)
    implementation(libs.activity.compose)
    implementation(libs.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
}

data class Version(val major: Int, val minor: Int, val bugfix: Int, val build: Int)

fun getVersionConfig(): Version {
    val props = Properties()
    val file = File(rootDir, "version.properties")
    props.load(file.inputStream())

    return Version(
        major = props["VERSION_MAJOR"].toString().toInt(),
        minor = props["VERSION_MINOR"].toString().toInt(),
        bugfix = props["VERSION_BUGFIX"].toString().toInt(),
        build = props["VERSION_BUILD"].toString().toInt()
    )
}

fun getVersionCode(): Int {
    val v = getVersionConfig()
    return v.major * 100_000 + v.minor * 10_000 + v.bugfix * 1_000 + v.build
}

fun getVersionName(): String {
    val v = getVersionConfig()
    return "${v.major}.${v.minor}.${v.bugfix}.${String.format("%03d", v.build)}"
}