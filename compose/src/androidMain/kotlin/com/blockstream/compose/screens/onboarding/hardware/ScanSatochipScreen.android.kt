package com.blockstream.compose.screens.onboarding.hardware

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.blockstream.common.models.devices.ScanSatochipViewModelPreview
import com.blockstream.compose.GreenAndroidPreview
import com.blockstream.compose.screens.onboarding.hardware.ScanSatochipScreen

@Composable
@Preview
fun ScanSatochipScreenPreview() {
    GreenAndroidPreview {
//        ScanSatochipScreen(viewModel = ScanSatochipViewModelPreview.preview())
        Navigation(viewModel = ScanSatochipViewModelPreview.preview())
    }
}