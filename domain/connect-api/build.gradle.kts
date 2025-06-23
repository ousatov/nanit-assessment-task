plugins {
    alias(libs.plugins.kotlin.jvm)
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    compileOnly(libs.javax.inject)
    implementation(libs.kotlinx.coroutines.core)
}