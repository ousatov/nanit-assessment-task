package com.usatov.nanithometask.feature.connect.compose


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.usatov.nanithometask.feature.connect.ConnectUiState
import com.usatov.nanithometask.feature.connect.ui.NanitColors

@Composable
fun ConnectButton(
    status: ConnectUiState.Status,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = status != ConnectUiState.Status.Connecting,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(containerColor = NanitColors.Purple),
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.buttonHeight)
    ) {
        if (status == ConnectUiState.Status.Connecting) {
            CircularProgressIndicator(
                strokeWidth = 2.dp,
                color = Color.White,
                modifier = Modifier.size(Dimens.progressSize)
            )
        } else {
            Text("Connect", color = Color.White)
        }
    }
}
