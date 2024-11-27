package com.blockstream.compose.screens.onboarding.hardware.satochip

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import blockstream_green.common.generated.resources.Res
import blockstream_green.common.generated.resources.hw_matrix_bg
import blockstream_green.common.generated.resources.nfc_view
import blockstream_green.common.generated.resources.setup_sato_image
import com.blockstream.common.models.devices.ScanSatochipViewModelAbstract
import com.blockstream.compose.components.GreenButton
import com.blockstream.compose.components.GreenButtonColor
import com.blockstream.compose.components.GreenButtonSize
import com.blockstream.compose.components.GreenButtonType
import com.blockstream.compose.theme.displayMedium
import com.blockstream.compose.theme.whiteMedium
import org.jetbrains.compose.resources.painterResource

@Composable
fun SetupSatochipScreen(
    viewModel: ScanSatochipViewModelAbstract,
    onClick: () -> Unit,
    onRestore: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(state = scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp)
        ) {
            Image(
                painter = painterResource(Res.drawable.setup_sato_image),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .align(Alignment.Center)
                    .height(300.dp)
                    .fillMaxWidth()
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Setup your Satochip hardware wallet",
                style = displayMedium,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Your keys secure your coins on the blockchain",
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                color = whiteMedium
            )
        }
        Spacer(Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GreenButton(
                "New Wallet ",
                modifier = Modifier.fillMaxWidth(),
                size = GreenButtonSize.BIG,
            ) {
                onClick()
            }
            Spacer(Modifier.height(18.dp))
            GreenButton(
                "Restore Wallet",
                modifier = Modifier.fillMaxWidth(),
                size = GreenButtonSize.BIG,
                type = GreenButtonType.OUTLINE,
                color = GreenButtonColor.WHITE
            ) {
                onRestore()
            }
        }
    }
}