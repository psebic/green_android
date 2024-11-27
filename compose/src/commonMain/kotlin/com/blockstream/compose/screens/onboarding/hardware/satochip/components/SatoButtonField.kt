package com.blockstream.compose.screens.onboarding.hardware.satochip.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import blockstream_green.common.generated.resources.Res
import blockstream_green.common.generated.resources.delete_last
import org.jetbrains.compose.resources.painterResource

@Composable
fun SatoButtonField(
    modifier: Modifier = Modifier,
    item: String,
    isImage: Boolean = false,
    fontsize: TextUnit = 32.sp,
    onClick: (String) -> Unit,
) {
    if (isImage) {
        Image(
            painter = painterResource(Res.drawable.delete_last),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = modifier
                .height(24.dp)
                .clickable{
                    onClick(item)
                }
        )
    } else {
        Text(
            modifier = modifier
                .clickable{
                    onClick(item)
                }
                .padding(24.dp),
            text = item,
            fontSize = fontsize,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}