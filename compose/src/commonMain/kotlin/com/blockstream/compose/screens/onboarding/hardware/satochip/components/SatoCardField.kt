package com.blockstream.compose.screens.onboarding.hardware.satochip.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import blockstream_green.common.generated.resources.Res
import blockstream_green.common.generated.resources.nfc_view
import com.blockstream.compose.theme.displayMedium
import org.jetbrains.compose.resources.painterResource


@Composable
fun SatoCardField(
    image: Painter = painterResource(Res.drawable.nfc_view),
    title: String,
    text: String
) {
    val SatoBackground = Color(0xFF1D1F2B)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = SatoBackground.copy(0.9f)
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(16.dp))
        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier
                .size(24.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = title,
            style = displayMedium,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = text,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
    }
}