package com.blockstream.common.data

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.blockstream.common.gdk.data.Network
import com.russhwolf.settings.Settings

fun Settings.putStringOrRemove(key: String, value: String?) {
    if (value == null) {
        this.remove(key)
    } else {
        putString(key, value)
    }
}

@Parcelize
data class ApplicationSettings constructor(
    val enhancedPrivacy: Boolean = false,
    val screenLockInSeconds: Int = ScreenLockSetting.LOCK_IMMEDIATELY.seconds,
    val testnet: Boolean = false,
    val proxyUrl: String? = null,
    val tor: Boolean = false,
    val electrumNode: Boolean = false,
    val spv: Boolean = false,
    val multiServerValidation: Boolean = false,
    val analytics: Boolean = false,
    val experimentalFeatures: Boolean = false,

    val hideAmounts: Boolean = false,

    val personalBitcoinElectrumServer: String? = null,
    val personalLiquidElectrumServer: String? = null,
    val personalTestnetElectrumServer: String? = null,
    val personalTestnetLiquidElectrumServer: String? = null,

    val spvBitcoinElectrumServer: String? = null,
    val spvLiquidElectrumServer: String? = null,
    val spvTestnetElectrumServer: String? = null,
    val spvTestnetLiquidElectrumServer: String? = null,
) : Parcelable {

    fun getPersonalElectrumServer(network: Network) = when {
            Network.isBitcoinMainnet(network.id) -> {
                personalBitcoinElectrumServer
            }
            Network.isLiquidMainnet(network.id) -> {
                personalLiquidElectrumServer
            }
            Network.isLiquidTestnet(network.id) -> {
                personalTestnetLiquidElectrumServer
            }
            else -> {
                personalTestnetElectrumServer
            }
        }

    fun getSpvElectrumServer(network: Network) = when {
        Network.isBitcoinMainnet(network.id) -> {
            spvBitcoinElectrumServer
        }
        Network.isLiquidMainnet(network.id) -> {
            spvLiquidElectrumServer
        }
        Network.isLiquidTestnet(network.id) -> {
            spvTestnetLiquidElectrumServer
        }
        else -> {
            spvTestnetElectrumServer
        }
    }

    companion object {
        private const val ENHANCED_PRIVACY = "enhancedPrivacy"
        private const val SCREEN_LOCK_IN_SECONDS = "screenLockInSeconds"
        private const val TESTNET = "testnet"
        private const val PROXY_URL = "proxyURL"
        private const val TOR = "tor"
        private const val ELECTRUM_NODE = "electrumNode"
        private const val SPV = "spv"
        private const val MULTI_SERVER_VALIDATION = "multiServerValidation"
        private const val ANALYTICS = "analytics"
        private const val EXPERIMENTAL_FEATURES = "experimental_features"
        private const val HIDE_AMOUNTS = "hideAmounts"

        private const val PERSONAL_BITCOIN_ELECTRUM_SERVER = "personalBitcoinElectrumServer"
        private const val PERSONAL_LIQUID_ELECTRUM_SERVER = "personalLiquidElectrumServer"
        private const val PERSONAL_TESTNET_ELECTRUM_SERVER = "personalTestnetElectrumServer"
        private const val PERSONAL_TESTNET_LIQUID_ELECTRUM_SERVER = "personalTestnetLiquidElectrumServer"

        private const val SPV_BITCOIN_ELECTRUM_SERVER = "spvBitcoinElectrumServer"
        private const val SPV_LIQUID_ELECTRUM_SERVER = "spvLiquidElectrumServer"
        private const val SPV_TESTNET_ELECTRUM_SERVER = "spvTestnetElectrumServer"
        private const val SPV_TESTNET_LIQUID_ELECTRUM_SERVER = "spvTestnetLiquidElectrumServer"

        fun fromSettings(settings: Settings): ApplicationSettings {
            try{
                return ApplicationSettings(
                    enhancedPrivacy = settings.getBoolean(ENHANCED_PRIVACY, false),
                    screenLockInSeconds = settings.getInt(SCREEN_LOCK_IN_SECONDS, 0),
                    testnet = settings.getBoolean(TESTNET, false),
                    proxyUrl = settings.getStringOrNull(PROXY_URL),
                    tor = settings.getBoolean(TOR, false),
                    electrumNode = settings.getBoolean(ELECTRUM_NODE, false),
                    spv = settings.getBoolean(SPV, false),
                    multiServerValidation = settings.getBoolean(MULTI_SERVER_VALIDATION, false),
                    analytics = settings.getBoolean(ANALYTICS, false),
                    experimentalFeatures = settings.getBoolean(EXPERIMENTAL_FEATURES, false),

                    hideAmounts = settings.getBoolean(HIDE_AMOUNTS, false),

                    personalBitcoinElectrumServer = settings.getStringOrNull(
                        PERSONAL_BITCOIN_ELECTRUM_SERVER
                    ),
                    personalLiquidElectrumServer = settings.getStringOrNull(
                        PERSONAL_LIQUID_ELECTRUM_SERVER
                    ),
                    personalTestnetElectrumServer = settings.getStringOrNull(
                        PERSONAL_TESTNET_ELECTRUM_SERVER
                    ),
                    personalTestnetLiquidElectrumServer = settings.getStringOrNull(
                        PERSONAL_TESTNET_LIQUID_ELECTRUM_SERVER
                    ),

                    spvBitcoinElectrumServer = settings.getStringOrNull(SPV_BITCOIN_ELECTRUM_SERVER),
                    spvLiquidElectrumServer = settings.getStringOrNull(SPV_LIQUID_ELECTRUM_SERVER),
                    spvTestnetElectrumServer = settings.getStringOrNull(SPV_TESTNET_ELECTRUM_SERVER),
                    spvTestnetLiquidElectrumServer = settings.getStringOrNull(
                        SPV_TESTNET_LIQUID_ELECTRUM_SERVER
                    ),
                )
            }catch (e: Exception){
                return ApplicationSettings()
            }
        }

        fun saveSettings(appSettings: ApplicationSettings, settings: Settings) {
            settings.also {
                it.putBoolean(ENHANCED_PRIVACY, appSettings.enhancedPrivacy)
                it.putInt(SCREEN_LOCK_IN_SECONDS, appSettings.screenLockInSeconds)
                it.putBoolean(TESTNET, appSettings.testnet)
                it.putStringOrRemove(PROXY_URL, appSettings.proxyUrl)
                it.putBoolean(TOR, appSettings.tor)
                it.putBoolean(ELECTRUM_NODE, appSettings.electrumNode)
                it.putBoolean(SPV, appSettings.spv)
                it.putBoolean(MULTI_SERVER_VALIDATION, appSettings.multiServerValidation)
                it.putBoolean(ANALYTICS, appSettings.analytics)
                it.putBoolean(EXPERIMENTAL_FEATURES, appSettings.experimentalFeatures)
                it.putBoolean(HIDE_AMOUNTS, appSettings.hideAmounts)

                it.putStringOrRemove(PERSONAL_BITCOIN_ELECTRUM_SERVER, appSettings.personalBitcoinElectrumServer)
                it.putStringOrRemove(PERSONAL_LIQUID_ELECTRUM_SERVER, appSettings.personalLiquidElectrumServer)
                it.putStringOrRemove(PERSONAL_TESTNET_ELECTRUM_SERVER, appSettings.personalTestnetElectrumServer)
                it.putStringOrRemove(PERSONAL_TESTNET_LIQUID_ELECTRUM_SERVER, appSettings.personalTestnetLiquidElectrumServer)

                it.putStringOrRemove(SPV_BITCOIN_ELECTRUM_SERVER, appSettings.spvBitcoinElectrumServer)
                it.putStringOrRemove(SPV_LIQUID_ELECTRUM_SERVER, appSettings.spvLiquidElectrumServer)
                it.putStringOrRemove(SPV_TESTNET_ELECTRUM_SERVER, appSettings.spvTestnetElectrumServer)
                it.putStringOrRemove(SPV_TESTNET_LIQUID_ELECTRUM_SERVER, appSettings.spvTestnetLiquidElectrumServer)
            }
        }
    }
}

enum class ScreenLockSetting constructor(val seconds: Int){
    // Keep same order with getStringList
    LOCK_IMMEDIATELY(0),
    LOCK_AFTER_60(60);

    companion object {
        fun bySeconds(seconds: Int) = when(seconds){
            60 -> LOCK_AFTER_60
            else -> LOCK_IMMEDIATELY
        }

        fun byPosition(position: Int): ScreenLockSetting{
            return entries[position]
        }

        fun getStringList(): List<String>{
            return listOf("id_lock_immediately", "id_lock_after_1_minute")
        }
    }
}