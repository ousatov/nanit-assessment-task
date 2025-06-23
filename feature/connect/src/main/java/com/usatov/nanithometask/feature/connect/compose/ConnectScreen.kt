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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.usatov.nanithometask.feature.connect.ConnectEvent
import com.usatov.nanithometask.feature.connect.ConnectUiState
import com.usatov.nanithometask.feature.connect.ConnectViewModel
import com.usatov.nanithometask.feature.connect.R
import com.usatov.nanithometask.feature.connect.ui.NanitColors

@Composable
fun ConnectScreen(
    modifier: Modifier = Modifier,
    vm: ConnectViewModel = hiltViewModel(),
) {
    val ui by vm.state.collectAsState()
    val focusManager = LocalFocusManager.current

    Column(
        modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        StatusBarSpacer(color = NanitColors.Purple)
        Column(
            modifier
                .fillMaxSize()
                .padding(Dimens.padding_1x),
            verticalArrangement = Arrangement.spacedBy(Dimens.padding_1x)
        ) {
            Headline()

            Text(
                text = when (ui.status) {
                    ConnectUiState.Status.Connecting -> stringResource(R.string.status_connecting)
                    ConnectUiState.Status.Connected -> stringResource(R.string.status_connected)
                    ConnectUiState.Status.Error -> stringResource(R.string.status_error)
                    else -> ""
                },
                color = if (ui.status == ConnectUiState.Status.Error) NanitColors.Error else NanitColors.Purple
            )

            ServerForm(
                ip = ui.ip,
                port = ui.port,
                onIp = { vm.onEvent(ConnectEvent.IpChanged(it)) },
                onPort = { vm.onEvent(ConnectEvent.PortChanged(it)) },
                focusManager = focusManager
            )

            ConnectButton(
                status = ui.status,
                onClick = { vm.onEvent(ConnectEvent.ClickConnect) }
            )
        }
    }
}

@Composable
fun StatusBarSpacer(color: Color) {
    val density = LocalDensity.current
    val heightPx = WindowInsets.statusBars.getTop(density)
    val heightDp = with(density) { heightPx.toDp() }
    Spacer(
        Modifier
            .fillMaxWidth()
            .height(heightDp)
            .background(color)
    )
}