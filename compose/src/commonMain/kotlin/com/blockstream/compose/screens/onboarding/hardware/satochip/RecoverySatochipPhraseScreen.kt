package com.blockstream.compose.screens.onboarding.hardware.satochip

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import blockstream_green.common.generated.resources.Res
import blockstream_green.common.generated.resources.sato_house
import com.blockstream.common.models.devices.ScanSatochipViewModelAbstract
import com.blockstream.compose.screens.onboarding.hardware.satochip.components.MnemonicChoiceButtonField
import com.blockstream.compose.screens.onboarding.hardware.satochip.components.MnemonicWordField
import com.blockstream.compose.screens.onboarding.hardware.satochip.components.StepCircles
import org.jetbrains.compose.resources.painterResource
import kotlin.random.Random

@Composable
fun RecoverySatochipPhraseScreen(
    viewModel: ScanSatochipViewModelAbstract,
    mnemonicList: List<String>,
    onClick: () -> Unit,
) {
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
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MnemonicTest(
                mnemonicList = mnemonicList,
                onClick = {
                    onClick()
                }
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(16.dp))
            Image(
                painter = painterResource(Res.drawable.sato_house),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier
                    .size(24.dp)
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Make sure to be in a private and safe space",
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}






@Composable
fun MnemonicTest(
    mnemonicList: List<String>,
    onClick: () -> Unit
) {
    var counter by remember { mutableStateOf(4) }
    var randomIndex by remember { mutableStateOf(Random.nextInt(0, mnemonicList.size - 3)) }
    var omittedWordIndex by remember { mutableStateOf(Random.nextInt(0, 3)) }

    val indices = List(3) { randomIndex + it }
    val formattedList = indices.map { index -> mnemonicList[index] }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            val colors = List(4) { index ->
                if (index == 4 - counter) Color.White else Color.Gray
            }
            StepCircles(colors)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            formattedList.forEachIndexed { listIndex, word ->
                val actualIndex = indices[listIndex]
                if (listIndex == omittedWordIndex) {
                    MnemonicWordField(
                        index = actualIndex,
                        word = null
                    )
                } else {
                    MnemonicWordField(
                        index = actualIndex,
                        word = word
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        MnemonicChoiceButtonField(
            fullList = mnemonicList,
            omittedWord = formattedList[omittedWordIndex],
            onClick = { word ->
                if (word == formattedList[omittedWordIndex]) {
                    if (counter > 1) {
                        counter--
                        randomIndex = Random.nextInt(0, mnemonicList.size - 3)
                        omittedWordIndex = Random.nextInt(0, 3)
                    } else {
                        onClick()
                    }
                }
            }
        )
    }
}














