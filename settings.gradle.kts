pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "NanitHomeTask"

include(
    ":app",
    ":core:common", ":core:network", ":core:db", ":core:di", ":core:ui", ":core:navigation",
    ":domain:connect-api", ":domain:birthday-api",
    ":data:connect", ":data:birthday",
    ":feature:connect", ":feature:birthday",
)
