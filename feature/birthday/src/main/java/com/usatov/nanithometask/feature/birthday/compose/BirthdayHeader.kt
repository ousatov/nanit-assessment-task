package com.usatov.nanithometask.feature.birthday.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.usatov.nanithometask.feature.birthday.R
import com.usatov.nanithometask.feature.birthday.UiBirthday
import java.util.Locale

@Composable
fun BirthdayHeader(
    birthday: UiBirthday,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.horizontalPadding)
    ) {
        val (headline, windL, windR, monthImg, ageLabel) = createRefs()

        Text(
            text = birthday.nameLabel.uppercase(Locale.getDefault()),
            style = MaterialTheme.typography.labelLarge,
            color = Color.Black,
            fontSize = Dimens.headerFontSize,
            textAlign = TextAlign.Center,
            modifier = Modifier.constrainAs(headline) {
                top.linkTo(parent.top, margin = Dimens.nameLabelMargin)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )

        Image(
            painter = painterResource(R.drawable.deco_wind),
            contentDescription = null,
            modifier = Modifier
                .size(width = Dimens.iconWindWidth, height = Dimens.iconWindHeight)
                .constrainAs(windL) {
                    end.linkTo(monthImg.start, margin = Dimens.windMargin)
                    top.linkTo(monthImg.top)
                    bottom.linkTo(monthImg.bottom)
                }
        )

        Image(
            painter = painterResource(R.drawable.deco_wind),
            contentDescription = null,
            modifier = Modifier
                .size(width = Dimens.iconWindWidth, height = Dimens.iconWindHeight)
                .scale(scaleX = -1f, scaleY = 1f)
                .constrainAs(windR) {
                    start.linkTo(monthImg.end, margin = Dimens.windMargin)
                    top.linkTo(monthImg.top)
                    bottom.linkTo(monthImg.bottom)
                }
        )

        Image(
            painter = painterResource(birthday.ageResource),
            contentDescription = birthday.ageLabel,
            modifier = Modifier.constrainAs(monthImg) {
                top.linkTo(headline.bottom, margin = Dimens.ageDigitMargin)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Text(
            text = birthday.ageLabel.uppercase(Locale.getDefault()),
            style = MaterialTheme.typography.labelLarge,
            color = Color.Black,
            fontSize = Dimens.headerFontSize,
            textAlign = TextAlign.Center,
            modifier = Modifier.constrainAs(ageLabel) {
                top.linkTo(monthImg.bottom, margin = Dimens.ageLabelMargin)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )
    }
}