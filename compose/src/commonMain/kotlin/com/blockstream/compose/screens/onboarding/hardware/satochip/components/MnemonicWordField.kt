package com.blockstream.compose.screens.onboarding.hardware.satochip.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MnemonicWordField(
    modifier: Modifier = Modifier,
    index: Int,
    word: String?,
) {
    val item = buildAnnotatedString {
        pushStyle(SpanStyle(color = Color.Green))
        append("${index + 1} ")
        pop()
        pushStyle(SpanStyle(color = Color.White))
        append(word?: "_____")
        pop()
    }
    Text(
        text = item,
        fontSize = 18.sp,
        modifier = modifier.padding(vertical = 4.dp),
        textAlign = TextAlign.Center
    )
}