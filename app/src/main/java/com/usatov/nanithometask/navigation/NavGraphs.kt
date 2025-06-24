package com.usatov.nanithometask.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.usatov.nanithometask.core.navigation.NavRoutes
import com.usatov.nanithometask.feature.birthday.compose.BirthdayScreen
import com.usatov.nanithometask.feature.connect.compose.ConnectScreen

fun NavGraphBuilder.connectGraph(onDone: () -> Unit) {
    composable(NavRoutes.CONNECT) { ConnectScreen(onDone = onDone) }
}

fun NavGraphBuilder.birthdayGraph(onBack: () -> Unit) {
    composable(NavRoutes.BIRTHDAY) { BirthdayScreen(onBack = onBack) }
}