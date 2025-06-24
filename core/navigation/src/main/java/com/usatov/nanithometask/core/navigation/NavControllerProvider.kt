package com.usatov.nanithometask.core.navigation

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

val LocalNavController = staticCompositionLocalOf<NavHostController> {
    error("NavController not provided – wrap composable into CompositionLocalProvider")
}