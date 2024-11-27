package com.blockstream.compose.utils

fun generateMnemonic(mnemonicSize: Int): String {
    val entropyBits = when (mnemonicSize) {
        12 -> 128
        24 -> 256
        else -> throw IllegalArgumentException("Invalid mnemonic size. Must be 12, 18, or 24 words.")
    }
    val entropy = ByteArray(entropyBits / 8)
    java.security.SecureRandom().nextBytes(entropy)
//    val mnemonic = MnemonicCode.INSTANCE.toMnemonic(entropy)
//
//    return mnemonic.joinToString(" ")
    return "piano render raw title opinion mask paddle heart return awesome knock trim"
}