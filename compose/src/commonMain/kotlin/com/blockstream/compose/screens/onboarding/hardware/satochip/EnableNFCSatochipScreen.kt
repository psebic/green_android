package com.blockstream.compose.screens.onboarding.hardware.satochip

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import blockstream_green.common.generated.resources.Res
import blockstream_green.common.generated.resources.hw_matrix_bg
import blockstream_green.common.generated.resources.nfc_view
import com.blockstream.common.models.devices.ScanSatochipViewModelAbstract
import com.blockstream.compose.components.GreenButton
import com.blockstream.compose.components.GreenButtonColor
import com.blockstream.compose.components.GreenButtonSize
import com.blockstream.compose.components.GreenButtonType
import com.blockstream.compose.theme.displayMedium
import com.blockstream.compose.theme.whiteMedium
import com.blockstream.compose.utils.HandleSideEffect
import org.jetbrains.compose.resources.painterResource

@Composable
fun EnableNFCSatochipScreen(
    viewModel: ScanSatochipViewModelAbstract,
    showNfcDialog: MutableState<Boolean>
) {
    HandleSideEffect(viewModel = viewModel)
    val SatoBackground = Color(0xFF1D1F2B)
    val scrollState = rememberScrollState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
            .background(
                color = SatoBackground.copy(0.9f),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp)
                ) {
                    Image(
                        painter = painterResource(Res.drawable.hw_matrix_bg),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .height(300.dp)
                            .fillMaxWidth()
                    )

                    Image(
                        painter = painterResource(Res.drawable.nfc_view),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(64.dp)
                            .align(Alignment.Center)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Enable NFC from Android settings to continue",
                        style = displayMedium,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "Green uses NFC for communication with hardware wallets",
                        textAlign = TextAlign.Center,
                        fontSize = 15.sp,
                        color = whiteMedium
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GreenButton(
                    "Cancel",
                    modifier = Modifier.fillMaxWidth(),
                    size = GreenButtonSize.BIG,
                    type = GreenButtonType.OUTLINE,
                    color = GreenButtonColor.WHITE
                ) {
                }
            }
        }
    }
}