package com.blockstream.compose.screens.onboarding.hardware.satochip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blockstream.common.models.devices.ScanSatochipViewModelAbstract
import com.blockstream.compose.screens.onboarding.hardware.satochip.components.NumberPadField
import com.blockstream.compose.screens.onboarding.hardware.satochip.components.SatoButton
import com.blockstream.compose.screens.onboarding.hardware.satochip.components.StepCircles
import com.blockstream.compose.theme.displayMedium
import com.blockstream.compose.theme.whiteMedium

@Composable
fun SetAPinSatochipScreen(
    viewModel: ScanSatochipViewModelAbstract,
    onClick: (List<String>) -> Unit,
    onCancel: () -> Unit,
    onContinue: (String) -> Unit,
) {
    var pinNumber by remember { mutableStateOf("") }
    val SatoGray = Color(0xFF1D1F2B)
    val SatoGreen = Color(0xFF00B459)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                text = "Set a PIN",
                style = displayMedium,
                fontSize = 24.sp,
            )
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "You'll need your PIN to log in to your wallet. This PIN secures the wallet on this device only.",
                fontSize = 14.sp,
                color = whiteMedium
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                val pinNumberLength = pinNumber.length
                val colors = List(6) { index ->
                    if (index < pinNumberLength) Color.White else Color.Gray
                }
                StepCircles(colors)
            }
            NumberPadField(
                modifier = Modifier.weight(1f),
                onClick = { item ->
                    if (pinNumber.length < 6) {
                        pinNumber += item
                    }
                },
                onCancel = {
                    pinNumber = ""
                },
                onDelete = {
                    if (pinNumber.isNotEmpty()) {
                        pinNumber = pinNumber.dropLast(1)
                    }
                },
            )
            SatoButton(
                item = "Continue",
                backgroundColor = if (pinNumber.length == 6) SatoGreen else SatoGray.copy(0.9f),
                 onClick = {
                    if (pinNumber.length == 6) {
                        onContinue(pinNumber)
                    }
                }
            )
        }
    }
}