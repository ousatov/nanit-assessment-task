import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.usatov.nanithometask"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.usatov.nanithometask"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
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