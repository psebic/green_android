package com.blockstream.compose.screens.onboarding.hardware.satochip.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.blockstream.compose.components.GreenButton
import com.blockstream.compose.components.GreenButtonColor
import com.blockstream.compose.components.GreenButtonSize
import com.blockstream.compose.components.GreenButtonType
import kotlin.random.Random

@Composable
fun MnemonicChoiceButtonField(
    fullList: List<String>,
    omittedWord: String,
    onClick: (String) -> Unit
) {
    val randomIndex = Random.nextInt(0, fullList.size - 3)
    val indices = List(3) { randomIndex + it }
    val formattedList = indices.map { index ->
        fullList[index]
    }
    val rightWordPlace = Random.nextInt(0, 3)



    Spacer(Modifier.height(12.dp))
    formattedList.forEachIndexed { index, item ->
        if (rightWordPlace == index) {
            GreenButton(
                text = omittedWord,
                modifier = Modifier.fillMaxWidth(),
                size = GreenButtonSize.BIG,
                type = GreenButtonType.OUTLINE,
                color = GreenButtonColor.WHITE
            ) {
                onClick(omittedWord)
            }
            Spacer(Modifier.height(12.dp))
        }
        GreenButton(
            text = item,
            modifier = Modifier.fillMaxWidth(),
            size = GreenButtonSize.BIG,
            type = GreenButtonType.OUTLINE,
            color = GreenButtonColor.WHITE
        ) {
            onClick(item)
        }
        Spacer(Modifier.height(12.dp))
    }
}