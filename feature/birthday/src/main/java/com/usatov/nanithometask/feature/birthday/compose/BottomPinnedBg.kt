package com.usatov.nanithometask.feature.birthday.compose

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.isSpecified
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

@Composable
fun BottomPinnedBg(
    @DrawableRes bgResId: Int,
    modifier: Modifier = Modifier
) {
    val painter = painterResource(bgResId)

    val ratio = remember(painter) {
        val size = painter.intrinsicSize
        if (size.isSpecified && size.height > 0) {
            size.width / size.height
        } else {
            1f
        }
    }

    Box(modifier.fillMaxSize()) {
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ratio)
                .align(Alignment.BottomCenter)
        )
    }
}