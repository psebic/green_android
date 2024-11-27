package com.blockstream.compose.screens.onboarding.hardware.satochip

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import blockstream_green.common.generated.resources.Res
import blockstream_green.common.generated.resources.nfc_view
import blockstream_green.common.generated.resources.sato_house
import com.blockstream.common.models.devices.ScanSatochipViewModelAbstract
import com.blockstream.compose.components.GreenButton
import com.blockstream.compose.components.GreenButtonSize
import com.blockstream.compose.screens.onboarding.hardware.satochip.components.MnemonicWordField
import com.blockstream.compose.theme.displayMedium
import org.jetbrains.compose.resources.painterResource

@Composable
fun SatoRecoveryPhraseScreen(
    viewModel: ScanSatochipViewModelAbstract,
    onClick: (List<String>) -> Unit,
) {
//    val scrollState = rememberScrollState()
    val title = buildAnnotatedString {
        append("Write down your")
        pushStyle(SpanStyle(color = Color.White))
        append("red ")
        pop()
        pushStyle(SpanStyle(color = Color.Green))
        append(" recovery \n phrase")
        pop()
        pushStyle(SpanStyle(color = Color.White))
        append(" in the")
        pop()
        pushStyle(SpanStyle(color = Color.Green))
        append(" correct order")
        pop()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = title,
                style = displayMedium,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(12.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "Store it somewhere safe.",
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
            val mnemonicPlaceholder = "piano render raw title opinion mask paddle heart return awesome knock trim"
            val mnemonicArray = mnemonicPlaceholder.split(" ")
            val scrollState = rememberScrollState()


            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
                    .verticalScroll(scrollState)
            ) {
                mnemonicArray.forEachIndexed { index, word ->
                    Spacer(Modifier.height(24.dp))
                    MnemonicWordField(
                        modifier = Modifier.fillMaxWidth(),
                        index = index,
                        word = word
                    )
                }
            }
            GreenButton(
                "Next",
                modifier = Modifier.fillMaxWidth(),
                size = GreenButtonSize.BIG,
            ) {
                onClick(mnemonicArray)
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
                Text(
                    text = "Make sure to be in a private and safe space",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
