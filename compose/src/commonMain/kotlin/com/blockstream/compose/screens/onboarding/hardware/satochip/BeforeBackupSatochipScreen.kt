package com.blockstream.compose.screens.onboarding.hardware.satochip

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import blockstream_green.common.generated.resources.Res
import blockstream_green.common.generated.resources.sato_house
import blockstream_green.common.generated.resources.sato_warning
import blockstream_green.common.generated.resources.secure_sato
import com.blockstream.common.models.devices.ScanSatochipViewModelAbstract
import com.blockstream.compose.components.GreenButton
import com.blockstream.compose.components.GreenButtonSize
import com.blockstream.compose.screens.onboarding.hardware.satochip.components.SatoCardField
import com.blockstream.compose.theme.displayMedium
import org.jetbrains.compose.resources.painterResource

@Composable
fun BeforeBackupSatochipScreen(
    viewModel: ScanSatochipViewModelAbstract,
    onClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val showMnemonicDialog = remember { mutableStateOf(false) } // for NfcDialog
    if (showMnemonicDialog.value) {
        MnemonicDialog(
            showMnemonicDialog,
            onClickTwelve = {onClick()},
            onClickTwentyFour = {onClick()},
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(state = scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            Modifier.fillMaxWidth()
        ) {
            SatoCardField(
                image = painterResource(Res.drawable.sato_house),
                title = "Safe Environment",
                text = "Make sure you are alone and no camera is recording you or the screen"
            )
            Spacer(modifier = Modifier.height(16.dp))
            SatoCardField(
                image = painterResource(Res.drawable.sato_warning),
                title = "Sensitive Information",
                text = "Whomever can access your recovery phrase, can steal your funds"
            )
            Spacer(modifier = Modifier.height(16.dp))
            SatoCardField(
                image = painterResource(Res.drawable.secure_sato),
                title = "Safely stored",
                text = "If you forget it or lose it, your funds are going to be lost as well."
            )
            Spacer(modifier = Modifier.height(16.dp))
        }


        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GreenButton(
                "Show Recovery Phrase",
                modifier = Modifier.fillMaxWidth(),
                size = GreenButtonSize.BIG,
            ) {
                showMnemonicDialog.value = !showMnemonicDialog.value
            }
        }
    }
}

@Composable
fun MnemonicDialog(
    openDialogCustom: MutableState<Boolean>,
    onClickTwelve: () -> Unit,
    onClickTwentyFour: () -> Unit
) {
    BottomDrawer(
        showSheet = openDialogCustom
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "New recovery phrase",
                style = displayMedium,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(1.dp).fillMaxWidth().background(color = Color.Gray))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable{
                        onClickTwelve()
                    },
                text = "12 words",
                color = Color.Black,
                fontSize = 16.sp,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(1.dp).fillMaxWidth().background(color = Color.Gray))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable{
                        onClickTwentyFour()
                    },
                text = "24 words",
                color = Color.Black,
                fontSize = 16.sp,
                textAlign = TextAlign.Start
            )
        }

    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    showSheet: MutableState<Boolean>,
    modifier: Modifier,
    content: @Composable () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    if (!showSheet.value) {
        return
    } else {
        ModalBottomSheet(
            modifier = modifier,
            containerColor = Color.White,
            sheetState = sheetState,
            onDismissRequest = {
                showSheet.value = !showSheet.value
            },
            shape = RoundedCornerShape(10.dp)
        ) {
            content()
        }
    }
}
@Composable
fun BottomDrawer(
    showSheet: MutableState<Boolean>,
    content: @Composable () -> Unit,
) {
    BottomSheet(showSheet = showSheet, modifier = Modifier) {
        Box(
            modifier = Modifier.padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

