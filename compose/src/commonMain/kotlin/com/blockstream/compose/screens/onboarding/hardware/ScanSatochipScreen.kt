package com.blockstream.compose.screens.onboarding.hardware

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import blockstream_green.common.generated.resources.Res
import blockstream_green.common.generated.resources.hw_matrix_bg
import blockstream_green.common.generated.resources.nfc_view
import blockstream_green.common.generated.resources.phone_icon
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import com.blockstream.common.models.devices.ScanSatochipViewModel
import com.blockstream.common.models.devices.ScanSatochipViewModelAbstract
import com.blockstream.compose.components.GreenButton
import com.blockstream.compose.components.GreenButtonColor
import com.blockstream.compose.components.GreenButtonSize
import com.blockstream.compose.components.GreenButtonType
import com.blockstream.compose.screens.onboarding.hardware.satochip.BeforeBackupSatochipScreen
import com.blockstream.compose.screens.onboarding.hardware.satochip.EnableNFCSatochipScreen
import com.blockstream.compose.screens.onboarding.hardware.satochip.RecoverySatochipPhraseScreen
import com.blockstream.compose.screens.onboarding.hardware.satochip.RestoreSatoRecoveryScreen
import com.blockstream.compose.screens.onboarding.hardware.satochip.SatoRecoveryPhraseScreen
import com.blockstream.compose.screens.onboarding.hardware.satochip.SetAPinSatochipScreen
import com.blockstream.compose.screens.onboarding.hardware.satochip.SetupSatochipScreen
import com.blockstream.compose.screens.onboarding.hardware.satochip.VerifyAPinSatochipScreen
import com.blockstream.compose.screens.onboarding.hardware.satochip.WelcomeToSatoWalletScreen
import com.blockstream.compose.theme.displayMedium
import com.blockstream.compose.theme.whiteMedium
import com.blockstream.compose.utils.AppBar
import com.blockstream.compose.utils.HandleSideEffect
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.painterResource

object ScanSatochipScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = koinScreenModel<ScanSatochipViewModel>()

        val navData by viewModel.navData.collectAsStateWithLifecycle()

        AppBar(navData)
        Navigation(viewModel = viewModel)

//        ScanSatochipScreen(viewModel = viewModel)
    }
}

@Serializable
object ScanSatoScreen
@Serializable
object SetupSatoScreen
@Serializable
object EnableNFCSatoScreen
@Serializable
object BeforeBackupSatoScreen
@Serializable
object SatoRecoveryScreen
@Serializable
object SetAPinSatoScreen
@Serializable
object RestoreSatochipRecoveryScreen
@Serializable
object WelcomeToYourWalletSatoScreen
@Serializable
data class SatoRecoveryPhraseScreen(
    val mnemonicList: List<String>,
)
@Serializable
data class VerifyAPinSatoScreen(
    val previousPhrase: String,
)

@Composable
fun Navigation(
    viewModel: ScanSatochipViewModelAbstract,
) {
    val navController = rememberNavController()
    val showNfcDialog = remember { mutableStateOf(false) } // for NfcDialog
    if (showNfcDialog.value) {
        NfcDialog(
            openDialogCustom = showNfcDialog,
        )
    }

    NavHost(
        navController = navController,
        startDestination = ScanSatoScreen
    ) {
        composable<ScanSatoScreen> {
            if (showNfcDialog.value) {
                navController.navigate(SetupSatoScreen)
            }
            ScanSatochipScreen(
                viewModel = viewModel,
                showNfcDialog = showNfcDialog
            )
        }
        composable<SetupSatoScreen> {
            SetupSatochipScreen(
                viewModel = viewModel,
                onClick = {
                    navController.navigate(BeforeBackupSatoScreen)
                },
                onRestore = {
                    navController.navigate(RestoreSatochipRecoveryScreen)
                }
            )
        }
        composable<EnableNFCSatoScreen> {
            EnableNFCSatochipScreen(
                viewModel = viewModel,
                showNfcDialog = showNfcDialog
            )
        }
        composable<BeforeBackupSatoScreen> {
            BeforeBackupSatochipScreen(
                viewModel = viewModel,
                onClick = {
                    navController.navigate(SatoRecoveryScreen)
                }
            )
        }
        composable<SatoRecoveryScreen> {
            SatoRecoveryPhraseScreen(
                viewModel = viewModel,
                onClick = { list ->
                    navController.navigate(
                        SatoRecoveryPhraseScreen(
                            mnemonicList = list,
                        )
                    )
                }
            )
        }
        composable<SatoRecoveryPhraseScreen> {
            val args = it.toRoute<SatoRecoveryPhraseScreen>()
            RecoverySatochipPhraseScreen(
                viewModel = viewModel,
                mnemonicList = args.mnemonicList,
                onClick = {
                    navController.navigate(SetAPinSatoScreen)
                }
            )
        }
        composable<SetAPinSatoScreen> {
            SetAPinSatochipScreen(
                viewModel = viewModel,
                onClick = {},
                onCancel = {
                    navController.navigateUp()
                },
                onContinue = { pinNumber ->
                    navController.navigate(
                        VerifyAPinSatoScreen(
                            previousPhrase = pinNumber
                        )
                    )
                }
            )
        }
        composable<RestoreSatochipRecoveryScreen> {
            RestoreSatoRecoveryScreen(
                viewModel = viewModel,
                onClick = { mnemonicPhrase ->
                    navController.navigate(
                        VerifyAPinSatoScreen(
                            previousPhrase = ""
                        )
                    )
                }
            )
        }
        composable<VerifyAPinSatoScreen> {
            val args = it.toRoute<VerifyAPinSatoScreen>()

            if (showNfcDialog.value) {
                navController.navigate(WelcomeToYourWalletSatoScreen)
            }
            VerifyAPinSatochipScreen(
                viewModel = viewModel,
                onClick = {},
                onCancel = {
                    navController.navigateUp()
                },
                onContinue = { pinNumber ->
                    if (args.previousPhrase == pinNumber || args.previousPhrase.isEmpty()) {
                        showNfcDialog.value = !showNfcDialog.value
                    }
                }
            )
        }
        composable<WelcomeToYourWalletSatoScreen> {
            WelcomeToSatoWalletScreen(
                viewModel = viewModel,
                onClick = {}
            )
        }
    }
}

@Composable
fun ScanSatochipScreen(
    viewModel: ScanSatochipViewModelAbstract,
    showNfcDialog: MutableState<Boolean>
) {
    HandleSideEffect(viewModel = viewModel)
    // NFC DIALOG

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
                        text = "Scan your NFC hardware wallet to continue",
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
                    "Scan my Satochip",
                    modifier = Modifier.fillMaxWidth(),
                    size = GreenButtonSize.BIG,
                ) {
                    showNfcDialog.value = !showNfcDialog.value
                }
                Spacer(Modifier.height(18.dp))

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
@Composable
fun NfcDialog(
    openDialogCustom: MutableState<Boolean>,
) {
    BottomDrawer(
        showSheet = openDialogCustom
    ) {
        DrawerScreen(
            closeSheet = {
                openDialogCustom.value = !openDialogCustom.value
            },
            closeDrawerButton = true
        )
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
        skipPartiallyExpanded = false
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
@Composable
fun DrawerScreen(
    closeSheet: () -> Unit,
    closeDrawerButton: Boolean = false,
    title: Int? = null,
    message: Int? = null,
    image: Int? = null,
    triesLeft: Int? = null
) {
    Column(
        modifier = Modifier
            .height(400.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Ready to Scan",
            style = TextStyle(
                color = Color.Gray,
                fontSize = 26.sp
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.phone_icon),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Hold your Satochip near Phone",
            fontSize = 14.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        if(closeDrawerButton) {
            DialogSatoButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = closeSheet,
                buttonColor = Color(0xFFC6C6C6),
                textColor = Color.Black,
                shape = RoundedCornerShape(20)
            )
        }
    }
}

@Composable
fun DialogSatoButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    buttonColor: Color = Color(0xFF3B2055),
    textColor: Color = Color.White,
    horizontalPadding: Dp = 16.dp,
    shape: RoundedCornerShape = RoundedCornerShape(50)
) {
    Button(
        onClick = {
            onClick()
        },
        modifier = modifier
            .padding(
                vertical = 10.dp,
                horizontal = horizontalPadding
            )
            .height(40.dp),
        shape = shape,
        colors = ButtonColors(
            contentColor = textColor,
            containerColor = buttonColor,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Cancel",
                style = TextStyle(
                    color = textColor,
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}