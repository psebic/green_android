package com.blockstream.green.ui.devices

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import com.blockstream.common.models.GreenViewModel
import com.blockstream.common.models.devices.JadeGuideViewModel
import com.blockstream.common.models.devices.ScanSatochipViewModel
import com.blockstream.common.navigation.NavigateDestinations
import com.blockstream.common.sideeffects.SideEffect
import com.blockstream.common.sideeffects.SideEffects
import com.blockstream.compose.AppFragmentBridge
import com.blockstream.compose.screens.onboarding.hardware.Navigation
import com.blockstream.compose.screens.onboarding.hardware.ScanSatochipScreen
import com.blockstream.green.R
import com.blockstream.green.databinding.ComposeViewBinding
import com.blockstream.green.ui.AppFragment
//import com.blockstream.green.ui.onboarding.UseHardwareDeviceFragmentDirections

class ScanSatochipFragment : AppFragment<ComposeViewBinding>(
    layout = R.layout.compose_view,
    menuRes = 0
) {

    val viewModel: ScanSatochipViewModel by viewModels()

    override fun getGreenViewModel(): GreenViewModel = viewModel

    override val useCompose: Boolean = true

    override suspend fun handleSideEffect(sideEffect: SideEffect) {
        super.handleSideEffect(sideEffect)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeView.apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                AppFragmentBridge {
                    Navigation(
                        viewModel = viewModel
                    )
//                    ScanSatochipScreen(
//                        viewModel = viewModel
//                    )
                }
            }
        }
    }
}