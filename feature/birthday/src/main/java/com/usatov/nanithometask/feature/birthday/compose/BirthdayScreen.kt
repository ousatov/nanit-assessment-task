package com.usatov.nanithometask.feature.birthday.compose

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.usatov.nanithometask.domain.birthday.BirthdayTheme
import com.usatov.nanithometask.feature.birthday.BirthdayViewModel
import com.usatov.nanithometask.feature.birthday.R

@Composable
fun BirthdayScreen(
    onBack: () -> Unit,
    vm: BirthdayViewModel = hiltViewModel()
) {
    val ui by vm.uiState.collectAsStateWithLifecycle()
    ui ?: return
    val birthday = ui!!

    val bgResId by remember(birthday.theme) {
        mutableIntStateOf(birthday.theme.bgResId())
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(birthday.theme.tintRes()))
    ) {
        val (birthdayHeader, btnBack, circleLayer, nanitLogo, overlay) = createRefs()
        val avatarPadding = Dimens.horizontalPadding
        val themeColorDark = colorResource(birthday.theme.tintResDark())

        StatusBarOverlay(
            alpha = Dimens.statusBarAlpha,
            color = Color.Black,
            modifier = Modifier
                .constrainAs(overlay) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
        )

        BackButton(
            modifier = Modifier
                .constrainAs(btnBack) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .offset(x = Dimens.backHorizontalMargin, y = Dimens.backVerticalMargin),
            onClick = onBack
        )

        BirthdayHeader(
            birthday,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(birthdayHeader) {
                    top.linkTo(parent.top, margin = Dimens.headerTopMargin)
                    bottom.linkTo(circleLayer.top, margin = Dimens.headerBottomMargin)
                }
        )

        AvatarWithCamera(
            avatarRes = birthday.theme.babyAvatarResId(),
            avatarDesc = stringResource(R.string.baby_avatar_desc),
            avatarMaxSize = Dimens.avatarMaxSize,
            themeColor = themeColorDark,
            onCameraClick = { /* todo */ },
            modifier = Modifier
                .padding(start = avatarPadding, end = avatarPadding)
                .constrainAs(circleLayer) {
                    top.linkTo(parent.top, margin = Dimens.avatarOffset)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        )

        BottomPinnedBg(bgResId = bgResId)

        Image(
            painter = painterResource(R.drawable.logo_nanit),
            contentDescription = stringResource(R.string.nanit_logo_desc),
            modifier = Modifier.constrainAs(nanitLogo) {
                top.linkTo(circleLayer.bottom, margin = Dimens.nanitLogoTopMargin)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
    }
}


@DrawableRes
private fun BirthdayTheme.bgResId() = when (this) {
    BirthdayTheme.FOX -> R.drawable.bg_fox
    BirthdayTheme.ELEPHANT -> R.drawable.bg_elephant
    BirthdayTheme.PELICAN -> R.drawable.bg_pelican
}

@DrawableRes
private fun BirthdayTheme.babyAvatarResId() = when (this) {
    BirthdayTheme.FOX -> R.drawable.baby_circle_fox
    BirthdayTheme.ELEPHANT -> R.drawable.baby_circle_elephant
    BirthdayTheme.PELICAN -> R.drawable.baby_circle_pelican
}

@ColorRes
fun BirthdayTheme.tintRes() = when (this) {
    BirthdayTheme.FOX -> R.color.bg_fox
    BirthdayTheme.ELEPHANT -> R.color.bg_elephant
    BirthdayTheme.PELICAN -> R.color.bg_pelican
}

@ColorRes
fun BirthdayTheme.tintResDark() = when (this) {
    BirthdayTheme.FOX -> R.color.bg_fox_dark
    BirthdayTheme.ELEPHANT -> R.color.bg_elephant_dark
    BirthdayTheme.PELICAN -> R.color.bg_pelican_dark
}