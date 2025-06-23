package com.usatov.nanithometask.feature.connect.compose

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.usatov.nanithometask.feature.connect.R
import com.usatov.nanithometask.feature.connect.ui.NanitColors

@Composable
fun Headline(
    modifier: Modifier = Modifier,
    text: String = stringResource(R.string.hello_nanit_title)
) {
    Text(
        text = text,
        fontSize = Dimens.titleFontSize,
        fontWeight = FontWeight.Medium,
        color = NanitColors.Purple,
        modifier = modifier
    )
}