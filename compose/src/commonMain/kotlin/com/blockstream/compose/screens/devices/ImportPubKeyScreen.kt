package com.blockstream.compose.screens.devices

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import blockstream_green.common.generated.resources.Res
import blockstream_green.common.generated.resources.hw_matrix_bg
import blockstream_green.common.generated.resources.id_continue
import blockstream_green.common.generated.resources.id_import_pubkey
import blockstream_green.common.generated.resources.id_learn_more
import blockstream_green.common.generated.resources.id_scan_your_xpub_on_jade
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import com.blockstream.common.devices.DeviceBrand
import com.blockstream.common.models.devices.ImportPubKeyViewModel
import com.blockstream.common.models.devices.ImportPubKeyViewModelAbstract
import com.blockstream.compose.components.GreenButton
import com.blockstream.compose.components.GreenButtonSize
import com.blockstream.compose.components.GreenButtonType
import com.blockstream.compose.components.GreenColumn
import com.blockstream.compose.extensions.icon
import com.blockstream.compose.screens.jade.JadeQRScreen
import com.blockstream.compose.theme.bodyLarge
import com.blockstream.compose.theme.titleMedium
import com.blockstream.compose.utils.AppBar
import com.blockstream.compose.utils.HandleSideEffect
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.core.parameter.parametersOf


object ImportPubKeyScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = koinScreenModel<ImportPubKeyViewModel> {
            parametersOf(DeviceBrand.Blockstream)
        }

        val navData by viewModel.navData.collectAsStateWithLifecycle()

        AppBar(navData)

        ImportPubKeyScreen(viewModel = viewModel)
    }
}

@Composable
fun ImportPubKeyScreen(
    viewModel: ImportPubKeyViewModelAbstract
) {
    JadeQRScreen.getResult {
        viewModel.postEvent(
            ImportPubKeyViewModel.LocalEvents.ImportPubKey(
                pubKey = it
            )
        )
    }

    HandleSideEffect(viewModel = viewModel)

    val deviceBrand = viewModel.deviceBrand
    val onProgress by viewModel.onProgress.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                Image(
                    painter = painterResource(Res.drawable.hw_matrix_bg),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.align(Alignment.Center)
                )

                Image(
                    painter = painterResource(deviceBrand.icon()),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            GreenColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
            ) {
                Text(text = stringResource(Res.string.id_import_pubkey), style = titleMedium, textAlign = TextAlign.Center)
                Text(text = stringResource(Res.string.id_scan_your_xpub_on_jade), style = bodyLarge, textAlign = TextAlign.Center)
            }
        }

        GreenColumn(space = 8, modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Bottom) {

            AnimatedVisibility(visible = !onProgress) {

                GreenColumn {
                    GreenButton(
                        text = stringResource(Res.string.id_continue),
                        size = GreenButtonSize.BIG,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            viewModel.postEvent(ImportPubKeyViewModel.LocalEvents.ScanXpub)
                        }
                    )

                    GreenButton(
                        text = stringResource(Res.string.id_learn_more),
                        type = GreenButtonType.TEXT,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                             viewModel.postEvent(ImportPubKeyViewModel.LocalEvents.LearnMore)
                        }
                    )
                }
            }
        }
    }
}