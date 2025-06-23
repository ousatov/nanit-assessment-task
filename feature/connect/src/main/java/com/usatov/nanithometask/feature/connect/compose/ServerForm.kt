package com.usatov.nanithometask.feature.connect.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.usatov.nanithometask.feature.connect.R
import com.usatov.nanithometask.feature.connect.ui.NanitColors

private const val MAX_IP_LENGTH = 15
private const val MAX_PORT_LENGTH = 5

private const val MAX_PORT_VALUE = 65535
private const val MIN_PORT_VALUE = 1

private val ipPartialRegex = Regex("""^(\d{1,3}(\.\d{0,3}){0,3})?$""")

@Composable
fun ServerForm(
    ip: String,
    port: String,
    onIp: (String) -> Unit,
    onPort: (String) -> Unit,
    modifier: Modifier = Modifier,
    focusManager: FocusManager = LocalFocusManager.current,
) {
    val borderColor = NanitColors.Purple.copy(alpha = 0.35f)

    Column(
        modifier
            .fillMaxWidth()
            .background(NanitColors.Card, RoundedCornerShape(16.dp))
            .padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Enter server details",
            color = NanitColors.Purple,
            style = MaterialTheme.typography.titleMedium
        )

        OutlinedTextField(
            value = TextFieldValue(ip, TextRange(ip.length)),
            onValueChange = checkAndFormatEnteredIp(ip, onIp),
            label = { Text(stringResource(R.string.ip_address_hint)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            singleLine = true,
            shape = RoundedCornerShape(Dimens.cornerRadiusServerForm),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = borderColor,
                unfocusedBorderColor = borderColor,
                focusedLabelColor = borderColor,
                unfocusedLabelColor = borderColor
            )
        )

        OutlinedTextField(
            value = port,
            onValueChange = checkEnteredPort(onPort),
            label = { Text(stringResource(R.string.port_hint)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            singleLine = true,
            shape = RoundedCornerShape(Dimens.cornerRadiusServerForm),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = borderColor,
                unfocusedBorderColor = borderColor,
                focusedLabelColor = borderColor,
                unfocusedLabelColor = borderColor
            )
        )
    }
}


private fun checkEnteredPort(onPort: (String) -> Unit) = { raw: String ->
    val digits = raw.filter { it.isDigit() }
    val limited = digits.take(MAX_PORT_LENGTH)

    val accept = when {
        limited.isEmpty() -> true
        limited.length < MAX_PORT_LENGTH -> true
        else -> {
            val num = limited.toInt()
            num in MIN_PORT_VALUE..MAX_PORT_VALUE
        }
    }

    if (accept) {
        onPort(limited)
    }
}


private fun checkAndFormatEnteredIp(
    ip: String,
    onIp: (String) -> Unit
) = { tfv: TextFieldValue ->
    var raw = tfv.text

    if (ip.endsWith('.') && ip.length - raw.length == 1) {
        raw = raw.dropLast(1)
    }

    val sb = StringBuilder()
    var segLen = 0
    var dotCount = 0

    raw.forEach { ch ->
        when {
            ch.isDigit() && segLen < 3 -> {
                sb.append(ch)
                segLen++
            }

            ch == '.' && segLen > 0 && dotCount < 3 -> {
                sb.append('.')
                dotCount++; segLen = 0
            }
        }
    }

    if (segLen == 3 && dotCount < 3) {
        sb.append('.')
    }

    val formatted = sb.toString()
    if (ipPartialRegex.matches(formatted)) {
        onIp(formatted)
    }
}
