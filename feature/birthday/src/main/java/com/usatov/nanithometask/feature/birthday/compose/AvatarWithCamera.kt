package com.usatov.nanithometask.feature.birthday.compose

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp

@Composable
fun AvatarWithCamera(
    @DrawableRes avatarRes: Int,
    avatarDesc: String,
    avatarMaxSize: Dp,
    themeColor: Color,
    onCameraClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        val avatarSize = minOf(maxWidth, maxHeight, avatarMaxSize)

        Image(
            painter = painterResource(avatarRes),
            contentDescription = avatarDesc,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(avatarSize)
                .align(Alignment.Center)
        )

        CameraButton(
            onClick = onCameraClick,
            themeColor = themeColor,
            avatarSize = avatarSize,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}