package com.usatov.nanithometask.feature.birthday.compose

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.usatov.nanithometask.domain.birthday.BirthdayTheme
import com.usatov.nanithometask.feature.birthday.BirthdayViewModel
import com.usatov.nanithometask.feature.birthday.R
import com.usatov.nanithometask.feature.birthday.UiBirthday

@Composable
fun BirthdayScreen(
    onBack: () -> Unit,
    vm: BirthdayViewModel = hiltViewModel()
) {
    val birthday by vm.uiState.collectAsStateWithLifecycle()


    val bgResId by remember(birthday?.theme) {
        mutableStateOf(birthday?.theme?.bgResId())
    }
    val bgTint by remember(birthday?.theme) {
        mutableStateOf(birthday?.theme?.tintRes())
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(
                color = bgTint?.let { colorResource(it) }
                    ?: MaterialTheme.colorScheme.background
            )
    ) {
        bgResId?.let { resId ->
            Image(
                painter = painterResource(resId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }

            birthday?.let { BirthdayCard(it) }
        }
    }
}

@Composable
private fun BirthdayCard(birthday: UiBirthday) {
    Text(
        text = buildString {
            append(birthday.name)
            append("\n")
            append(birthday.ageLabel)
        },
        style = MaterialTheme.typography.headlineSmall,
        color = Color.White
    )
}

@DrawableRes
private fun BirthdayTheme.bgResId(): Int = when (this) {
    BirthdayTheme.FOX -> R.drawable.bg_fox
    BirthdayTheme.ELEPHANT -> R.drawable.bg_elephant
    BirthdayTheme.PELICAN -> R.drawable.bg_pelican
}

@ColorRes
fun BirthdayTheme.tintRes() = when (this) {
    BirthdayTheme.FOX -> R.color.bg_fox
    BirthdayTheme.ELEPHANT -> R.color.bg_elephant
    BirthdayTheme.PELICAN -> R.color.bg_pelican
}
