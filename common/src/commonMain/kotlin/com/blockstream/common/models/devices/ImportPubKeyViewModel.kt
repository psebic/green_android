package com.blockstream.common.models.devices

import com.blockstream.common.Urls
import com.blockstream.common.data.WatchOnlyCredentials
import com.blockstream.common.devices.DeviceBrand
import com.blockstream.common.events.Event
import com.blockstream.common.events.Events
import com.blockstream.common.models.GreenViewModel
import com.blockstream.common.models.jade.JadeQrOperation
import com.blockstream.common.navigation.NavigateDestinations
import com.blockstream.common.utils.Loggable

abstract class ImportPubKeyViewModelAbstract(val deviceBrand: DeviceBrand) : GreenViewModel() {
    override fun screenName(): String = "ImportPubKey"

}

class ImportPubKeyViewModel constructor(deviceBrand: DeviceBrand) : ImportPubKeyViewModelAbstract(deviceBrand = deviceBrand) {

    class LocalEvents {
        object ScanXpub: Events.NavigateTo(NavigateDestinations.JadeQR(JadeQrOperation.ExportXpub))
        object LearnMore: Events.OpenBrowser(url = Urls.HELP_JADE_EXPORT_XPUB)
        data class ImportPubKey(val pubKey: String) : Event
    }

    init {
        bootstrap()
    }

    override suspend fun handleEvent(event: Event) {
        super.handleEvent(event)

        if(event is LocalEvents.ImportPubKey){
            createNewWatchOnlyWallet(
                network = session.networks.bitcoinElectrum,
                persistLoginCredentials = true,
                watchOnlyCredentials = WatchOnlyCredentials(coreDescriptors = listOf(event.pubKey)),
                withBiometrics = true,
                isHardware = true
            )
        }
    }


    companion object: Loggable()
}

class ImportPubKeyViewModelPreview : ImportPubKeyViewModelAbstract(deviceBrand = DeviceBrand.Blockstream) {

    init {
        onProgress.value = true
    }

    companion object {
        fun preview() =
            DeviceInfoViewModelPreview()
    }

}