package com.usatov.nanithometask.feature.birthday.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun StatusBarOverlay(
    modifier: Modifier = Modifier,
    alpha: Float = 0.18f,
    color: Color
) {
    val density = LocalDensity.current

    val heightPx = WindowInsets.statusBars.getTop(density)
    val heightDp: Dp = with(density) { heightPx.toDp() }

    Box(
        modifier
            .fillMaxWidth()
            .height(heightDp)
            .background(color.copy(alpha = alpha))
    )
}