package com.usatov.nanithometask.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.usatov.nanithometask.core.navigation.LocalNavController
import com.usatov.nanithometask.core.navigation.NavRoutes

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(
            navController = navController,
            startDestination = NavRoutes.CONNECT
        ) {
            connectGraph(onDone = {
                navController.navigate(NavRoutes.BIRTHDAY) {
                    launchSingleTop = true
                }
            })
            birthdayGraph(onBack = { navController.popBackStack() })
        }
    }
}