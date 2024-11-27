package com.blockstream.common.models.devices


import com.blockstream.common.models.GreenViewModel

abstract class ScanSatochipViewModelAbstract : GreenViewModel()

class ScanSatochipViewModel : ScanSatochipViewModelAbstract(
) {
    override fun screenName(): String = "JadeSetupGuide"

    init {
        bootstrap()
    }
}

class ScanSatochipViewModelPreview : ScanSatochipViewModelAbstract() {
    companion object {
        fun preview() = ScanSatochipViewModelPreview()
    }
}
