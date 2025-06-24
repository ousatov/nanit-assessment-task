package com.usatov.nanithometask.feature.connect.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.usatov.nanithometask.core.navigation.LocalNavController
import com.usatov.nanithometask.core.navigation.NavRoutes
import com.usatov.nanithometask.feature.connect.ConnectEvent
import com.usatov.nanithometask.feature.connect.ConnectUiState
import com.usatov.nanithometask.feature.connect.ConnectViewModel
import com.usatov.nanithometask.feature.connect.NavCmd
import com.usatov.nanithometask.feature.connect.R
import com.usatov.nanithometask.feature.connect.ui.NanitColors
import kotlinx.coroutines.flow.collectLatest


@Composable
fun ConnectScreen(
    onDone: () -> Unit,
    modifier: Modifier = Modifier,
    vm: ConnectViewModel = hiltViewModel()
) {
    val ui by vm.state.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current
    val nav = LocalNavController.current

    LaunchedEffect(Unit) {
        vm.navigation.collectLatest { cmd ->
            if (cmd is NavCmd.ToBirthday) {
                nav.navigate(NavRoutes.BIRTHDAY)
            }
        }
    }
    Column(
        modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        StatusBarSpacer(color = NanitColors.Purple)

        Column(
            Modifier
                .fillMaxSize()
                .padding(Dimens.padding_1x),
            verticalArrangement = Arrangement.spacedBy(Dimens.padding_1x)
        ) {
            Headline()

            val statusResId by remember(ui.status) {
                derivedStateOf {
                    when (ui.status) {
                        ConnectUiState.Status.Connecting -> R.string.status_connecting
                        ConnectUiState.Status.Connected -> R.string.status_connected
                        else -> R.string.status_idle
                    }
                }
            }
            val statusText = when (ui.status) {
                ConnectUiState.Status.Error -> ui.errorMessage
                    ?: stringResource(R.string.status_error)

                else -> stringResource(statusResId)
            }

            val statusColor = if (ui.status == ConnectUiState.Status.Error) {
                NanitColors.Error
            } else {
                NanitColors.Purple
            }

            Text(text = statusText, color = statusColor)

            ServerForm(
                ip = ui.ip,
                port = ui.port,
                onIp = { vm.onEvent(ConnectEvent.IpChanged(it)) },
                onPort = { vm.onEvent(ConnectEvent.PortChanged(it)) },
                focusManager = focusManager,
                modifier = Modifier.fillMaxWidth()
            )

            NanitButton(
                text = stringResource(R.string.btn_connect),
                onClick = { vm.onEvent(ConnectEvent.ClickConnect) },
                loading = ui.status == ConnectUiState.Status.Connecting,
                enabled = ui.status != ConnectUiState.Status.Connecting
            )

            NanitButton(
                text = stringResource(R.string.btn_done),
                onClick = { vm.onEvent(ConnectEvent.ClickDone) },
                enabled = ui.status == ConnectUiState.Status.Connected,
            )
        }
    }
}

@Composable
fun StatusBarSpacer(color: Color) {
    val density = LocalDensity.current
    val heightPx = WindowInsets.statusBars.getTop(density)
    val heightDp: Dp = with(density) { heightPx.toDp() }
    Spacer(
        Modifier
            .fillMaxWidth()
            .height(heightDp)
            .background(color)
    )
}