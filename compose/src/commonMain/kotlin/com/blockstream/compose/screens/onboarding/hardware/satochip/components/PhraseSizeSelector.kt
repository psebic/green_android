package com.blockstream.compose.screens.onboarding.hardware.satochip.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PhraseSizeSelector(
    phraseSize: MutableState<Int>,
    satoGray: Color,
    satoGreen: Color

){
    Row(
        modifier = Modifier
            .background(
                color = satoGray.copy(0.9f)
            )
            .padding(1.dp)
    ) {
        Text(
            modifier = Modifier
                .background(
                    color = if (phraseSize.value == 12) satoGreen else satoGray.copy(0.9f),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(16.dp)
                .clickable {
                    phraseSize.value = 12
                },
            text = "12",
            color = if (phraseSize.value == 12) Color.White else satoGreen
        )
        Text(
            modifier = Modifier
                .background(
                    color = if (phraseSize.value == 24) satoGreen else satoGray.copy(0.9f),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(16.dp)
                .clickable {
                    phraseSize.value = 24
                },
            text = "24",
            color = if (phraseSize.value == 24) Color.White else satoGreen
        )
        Text(
            modifier = Modifier
                .background(
                    color = if (phraseSize.value == 27) satoGreen else satoGray.copy(0.9f),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(16.dp)
                .clickable {
                    phraseSize.value = 27
                },
            text = "27",
            color = if (phraseSize.value == 27) Color.White else satoGreen
        )
    }
}