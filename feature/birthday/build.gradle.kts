plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.usatov.nanithometask.feature.birthday"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig { minSdk = libs.versions.minSdk.get().toInt() }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin { jvmToolchain(17) }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
}

dependencies {

    implementation(project(":domain:birthday-api"))
    implementation(project(":data:birthday"))
    implementation(project(":core:di"))
    implementation(project(":core:navigation"))
    implementation(project(":core:common"))

    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.androidx.core.ktx)

    implementation(libs.hilt.android)
    implementation(libs.androidx.constraintlayout)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.nav.compose)

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.material3)
    implementation(libs.activity.compose)
    implementation(libs.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
}

