package com.blockstream.compose.screens.onboarding.hardware.satochip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blockstream.common.models.devices.ScanSatochipViewModelAbstract
import com.blockstream.compose.screens.onboarding.hardware.satochip.components.PhraseSizeSelector
import com.blockstream.compose.screens.onboarding.hardware.satochip.components.SatoButton
import com.blockstream.compose.screens.onboarding.hardware.satochip.components.SatoInputFields
import com.blockstream.compose.theme.displayMedium

@Composable
fun RestoreSatoRecoveryScreen(
    viewModel: ScanSatochipViewModelAbstract,
    onClick: (List<String>) -> Unit,
) {
    val phraseSize = remember { mutableStateOf(12) }
    val satoGray = Color(0xFF1D1F2B)
    val satoGreen = Color(0xFF00B459)
    val inputValues = remember { mutableStateListOf<String>() }
    val scrollState = rememberScrollState()
    val scrollStateB = rememberScrollState()


    LaunchedEffect(phraseSize.value) {
        if (inputValues.size != phraseSize.value) {
            inputValues.clear()
            inputValues.addAll(List(phraseSize.value) { "" })
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                text = "Enter your recovery phrase",
                style = displayMedium,
                fontSize = 24.sp,
            )
        }
        Spacer(Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            PhraseSizeSelector(
                phraseSize = phraseSize,
                satoGray = satoGray,
                satoGreen = satoGreen
            )
        }
        SatoInputFields(
            modifier = Modifier.weight(1f).verticalScroll(scrollStateB),
            inputValues = inputValues,
            satoGray = satoGray,
            satoGreen = satoGreen
        )
        SatoButton(
            modifier = Modifier,
            item = "Continue",
            backgroundColor = if (inputValues.all { it.isNotEmpty() } && inputValues.size == phraseSize.value) satoGreen else satoGray.copy(
                0.9f
            ),
            onClick = {
                if (inputValues.all { it.isNotEmpty() } && inputValues.size == phraseSize.value) {
                    onClick(inputValues)
                }
            }
        )
    }
}