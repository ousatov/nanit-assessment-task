package com.usatov.nanithometask.feature.birthday.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.usatov.nanithometask.feature.birthday.R
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CameraButton(
    onClick: () -> Unit,
    themeColor: Color,
    avatarSize: Dp,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    val radiusPx = with(density) { (avatarSize / 2f).toPx() }

    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(Dimens.iconCameraSize)
            .graphicsLayer {
                translationX =
                    radiusPx * cos(Math.toRadians(Dimens.iconCameraPositionAngle)).toFloat()
                translationY =
                    -radiusPx * sin(Math.toRadians(Dimens.iconCameraPositionAngle)).toFloat()
            }
    ) {
        Box(contentAlignment = Alignment.Center) {
            Canvas(Modifier.fillMaxSize()) {
                drawCircle(color = themeColor)
            }
            Icon(
                painter = painterResource(R.drawable.ic_camera),
                contentDescription = stringResource(R.string.change_photo_desc),
                tint = Color.White,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}