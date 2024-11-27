package com.blockstream.compose.screens.onboarding.hardware.satochip.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import blockstream_green.common.generated.resources.Res
import blockstream_green.common.generated.resources.nfc_view
import org.jetbrains.compose.resources.painterResource

@Composable
fun ScrollableMnemonicList(
    text: String
) {
    val mnemonicArray = text.split(" ")
    val scrollState = rememberScrollState()


//    Column(modifier = Modifier.weight(1f).padding(16.dp)) {
//        mnemonicArray.forEachIndexed { index, word ->
//            val item = buildAnnotatedString {
//                append("${index + 1} ")
//                pushStyle(SpanStyle(color = Color.Green))
//                pop()
//                pushStyle(SpanStyle(color = Color.White))
//                append(word)
//                pop()
//            }
//            Text(
//                text = item,
//                modifier = Modifier.padding(vertical = 4.dp)
//            )
//        }
//    }

}