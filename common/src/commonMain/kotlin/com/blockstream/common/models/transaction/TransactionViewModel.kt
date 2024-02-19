package com.blockstream.common.models.transaction

import com.blockstream.common.BTC_POLICY_ASSET
import com.blockstream.common.SATOSHI_UNIT
import com.blockstream.common.data.Denomination
import com.blockstream.common.data.GreenWallet
import com.blockstream.common.data.NavData
import com.blockstream.common.events.Event
import com.blockstream.common.extensions.assetTickerOrNull
import com.blockstream.common.extensions.isNotBlank
import com.blockstream.common.extensions.previewAccountAsset
import com.blockstream.common.extensions.previewWallet
import com.blockstream.common.gdk.data.AccountAsset
import com.blockstream.common.gdk.data.Transaction
import com.blockstream.common.gdk.params.TransactionParams
import com.blockstream.common.looks.AmountAssetLook
import com.blockstream.common.looks.transaction.Completed
import com.blockstream.common.looks.transaction.Confirmed
import com.blockstream.common.looks.transaction.Failed
import com.blockstream.common.looks.transaction.TransactionStatus
import com.blockstream.common.looks.transaction.Unconfirmed
import com.blockstream.common.models.GreenViewModel
import com.blockstream.common.navigation.NavigateDestinations
import com.blockstream.common.sideeffects.SideEffect
import com.blockstream.common.sideeffects.SideEffects
import com.blockstream.common.utils.Loggable
import com.blockstream.common.utils.feeRateWithUnit
import com.blockstream.common.utils.toAmountLook
import com.blockstream.common.utils.toAmountLookOrNa
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.Clock
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject

abstract class TransactionViewModelAbstract(
    accountAssetOrNull: AccountAsset? = null,
    greenWallet: GreenWallet
) : GreenViewModel(
    greenWalletOrNull = greenWallet,
    accountAssetOrNull = accountAssetOrNull
) {
    override fun screenName(): String = "TransactionDetails"

    @NativeCoroutinesState
    abstract val transaction: StateFlow<Transaction>

    @NativeCoroutinesState
    abstract val status: StateFlow<TransactionStatus>

    @NativeCoroutinesState
    abstract val type: StateFlow<Transaction.Type>

    @NativeCoroutinesState
    abstract val createdAtTs: StateFlow<Long>

    @NativeCoroutinesState
    abstract val spv: StateFlow<Transaction.SPVResult>

    @NativeCoroutinesState
    abstract val amounts: StateFlow<List<AmountAssetLook>>

    @NativeCoroutinesState
    abstract val transactionId: StateFlow<String?>

    @NativeCoroutinesState
    abstract val fee: StateFlow<String?>

    @NativeCoroutinesState
    abstract val feeRate: StateFlow<String?>

    @NativeCoroutinesState
    abstract val canReplaceByFee: StateFlow<Boolean>

    @NativeCoroutinesState
    abstract val address: StateFlow<String?>

    @NativeCoroutinesState
    abstract val note: StateFlow<String?>

    @NativeCoroutinesState
    abstract val hasMoreDetails: StateFlow<Boolean>
}

class TransactionViewModel(transaction: Transaction, greenWallet: GreenWallet) :
    TransactionViewModelAbstract(accountAssetOrNull = transaction.account.accountAsset, greenWallet = greenWallet) {

    class LocalEvents {
        class SetNote(val note: String) : Event
        object ViewInBlockExplorer : Event
        class ShareTransaction(val liquidShareType: LiquidShareType? = null) : Event
        object BumpFee : Event
        object RecoverFunds : Event
    }

    class LocalSideEffects {
         object SelectLiquidShareTransaction: SideEffect
    }

    enum class LiquidShareType {
        CONFIDENTIAL_TRANSACTION, NON_CONFIDENTIAL_TRANSACTION, UNBLINDING_DATA;
    }

    val _transaction = MutableStateFlow(transaction)
    override val transaction: StateFlow<Transaction> = _transaction.asStateFlow()

    val _status: MutableStateFlow<TransactionStatus> = MutableStateFlow(Failed())
    override val status: StateFlow<TransactionStatus> = _status.asStateFlow()

    override val type: StateFlow<Transaction.Type> = MutableStateFlow(transaction.txType)

    override val createdAtTs: StateFlow<Long> = MutableStateFlow(transaction.createdAtTs)

    val _spv: MutableStateFlow<Transaction.SPVResult> = MutableStateFlow(Transaction.SPVResult.Disabled)
    override val spv: StateFlow<Transaction.SPVResult> = _spv.asStateFlow()

    val _amounts: MutableStateFlow<List<AmountAssetLook>> = MutableStateFlow(listOf())
    override val amounts: StateFlow<List<AmountAssetLook>> = _amounts.asStateFlow()

    val _transactionId: MutableStateFlow<String?> = MutableStateFlow(null)
    override val transactionId: StateFlow<String?> = _transactionId.asStateFlow()

    val _fee: MutableStateFlow<String?> = MutableStateFlow(null)
    override val fee: StateFlow<String?> = _fee.asStateFlow()

    val _feeRate: MutableStateFlow<String?> = MutableStateFlow(null)
    override val feeRate: StateFlow<String?> = _feeRate.asStateFlow()

    val _canReplaceByFee: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val canReplaceByFee: StateFlow<Boolean> = _canReplaceByFee.asStateFlow()

    val _address: MutableStateFlow<String?> = MutableStateFlow(null)
    override val address: StateFlow<String?> = _address.asStateFlow()

    val _note: MutableStateFlow<String?> = MutableStateFlow(null)
    override val note: StateFlow<String?> = _note.asStateFlow()

    val _hasMoreDetails: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val hasMoreDetails: StateFlow<Boolean> = _hasMoreDetails.asStateFlow()

    init {
        logger.d { "Transaction $transaction" }

        if(session.isConnected) {
            _navData.value = NavData(
                title = "id_transaction_details",
                subtitle = account.name
            )

            combine(
                session.walletTransactions,
                session.accountTransactions(transaction.account),
                session.block(transaction.account.network)
            ) { walletTransactions, accountTransactions, _ ->
                // Be sure to find the correct tx not just by hash but also with the correct type (cross-account transactions)
                walletTransactions.find { it.txHash == transaction.txHash && it.txType == transaction.txType }
                    ?: accountTransactions.find { it.txHash == transaction.txHash }
            }.filterNotNull().onEach {
                _transaction.value = it
            }.launchIn(viewModelScope.coroutineScope)
        }

        _transaction.onEach {
            updateData()
        }.launchIn(viewModelScope.coroutineScope)

        bootstrap()
    }

    override fun handleEvent(event: Event) {
        super.handleEvent(event)

        if (event is LocalEvents.SetNote) {
            setNote(event.note)
        } else if (event is LocalEvents.ViewInBlockExplorer) {
            val blinder = if(account.isLiquid) "#blinded=${_transaction.value.getUnblindedString()}" else ""
            postSideEffect(SideEffects.OpenBrowser("${account.network.explorerUrl}${_transaction.value.txHash}$blinder"))
        } else if (event is LocalEvents.ShareTransaction) {
            if (event.liquidShareType == LiquidShareType.UNBLINDING_DATA) {
                postSideEffect(
                    SideEffects.Share(
                        text = _transaction.value.getUnblindedData().toJson()
                    )
                )
            } else if (account.isBitcoinOrLightning || event.liquidShareType != null) {
                val blinder =
                    if (event.liquidShareType == LiquidShareType.NON_CONFIDENTIAL_TRANSACTION) "#blinded=${_transaction.value.getUnblindedString()}" else ""
                postSideEffect(SideEffects.Share(text = "${account.network.explorerUrl}${_transaction.value.txHash}$blinder"))
                countly.shareTransaction(session = session, account = account, isShare = true)
            } else {
                postSideEffect(LocalSideEffects.SelectLiquidShareTransaction)
            }

        } else if (event is LocalEvents.BumpFee) {
            bumpFee()
        } else if (event is LocalEvents.RecoverFunds) {
            postSideEffect(
                SideEffects.NavigateTo(
                    NavigateDestinations.RecoverFunds(
                        greenWallet = greenWallet,
                        satoshi = transaction.value.satoshiPolicyAsset,
                        address = transaction.value.onChainAddress
                    )
                )
            )
        }
    }

    private suspend fun updateData(){
        val transaction = _transaction.value
        logger.d { "UpdateData with tx: $transaction" }

        val confirmations = transaction.getConfirmations(session)
        _status.value = when {
            transaction.isRefundableSwap -> {
                Failed()
            }
            confirmations == 0L -> {
                Unconfirmed(transaction.network.confirmationsRequired)
            }

            confirmations < transaction.network.confirmationsRequired -> {
                Confirmed(confirmations = confirmations.toInt(), transaction.network.confirmationsRequired)
            }

            confirmations >= transaction.network.confirmationsRequired -> {
                Completed
            }

            else -> {
                Failed()
            }
        }

        _spv.value = transaction.spv

        _amounts.value = transaction.utxoViews.map {
            AmountAssetLook(
                amount = it.satoshi.toAmountLookOrNa(
                    session = session,
                    assetId = it.assetId,
                    withUnit = false,
                    withDirection = true,
                    withMinimumDigits = true
                ),
                ticker = it.assetId.assetTickerOrNull(session) ?: it.assetId?.substring(0 until 6) ?: "",
                fiat = it.satoshi.toAmountLook(
                    session = session,
                    assetId = it.assetId,
                    withUnit = true,
                    withDirection = true,
                    denomination = Denomination.fiat(session)
                ), assetId = it.assetId ?: transaction.network.policyAsset
            )
        }

        _transactionId.value = transaction.txHash.takeIf { !account.isLightning }

        _fee.value = when {
            transaction.txType == Transaction.Type.IN && confirmations > 0L -> null
            else -> {
                "${transaction.fee.toAmountLook(
                    session = session,
                    assetId = transaction.account.network.policyAssetOrNull,
                    withUnit = true,
                    denomination = Denomination.byUnit(SATOSHI_UNIT)
                )} ${(if(transaction.fee > 0) "≈ ${transaction.fee.toAmountLook(
                    session = session,
                    assetId = transaction.account.network.policyAssetOrNull,
                    withUnit = true,
                    denomination = Denomination.fiat(session)
                )}" else "")}"
            }
        }

        _feeRate.value = transaction.feeRate.takeIf { _fee.value != null && !transaction.account.isLightning && it > 0}?.feeRateWithUnit()

        val utxoViews = transaction.utxoViews
        _address.value = when {
            utxoViews.size == 1 && (transaction.txType == Transaction.Type.IN || transaction.txType == Transaction.Type.OUT) -> {
                utxoViews.firstOrNull()?.address
            }
            else -> null
        }

        _canReplaceByFee.value = transaction.canRBF && !transaction.isIn && !session.isWatchOnly

        _note.value = transaction.memo.takeIf { it.isNotBlank()}

        _hasMoreDetails.value = transaction.details(session).isNotEmpty()
    }

    private fun setNote(note: String){
        doAsync({
            session.setTransactionMemo(transaction = _transaction.value, memo = note)
        }, onSuccess = {
            // _memo is updated from event
        })
    }

    private fun bumpFee(){
        doAsync({
            val transactions = session.getTransactions(
                transaction.value.account,
                TransactionParams(
                    subaccount = transaction.value.account.pointer,
                    confirmations = 0
                )
            )

            transactions
                .transactions
                .indexOfFirst { it.txHash == transaction.value.txHash } // Find the index of the transaction
                .takeIf { it >= 0 }?.let { index ->
                    transactions.jsonElement?.jsonObject?.get("transactions")?.jsonArray?.getOrNull(
                        index
                    )
                }?.let {
                    Json.encodeToString(it)
                } ?: throw Exception("Couldn't find the transaction")
        }, onSuccess = {
            postSideEffect(
                SideEffects.NavigateTo(
                    NavigateDestinations.Send(
                        greenWallet = greenWallet,
                        accountAsset = accountAsset.value!!,
                        bumpTransaction = it
                    )
                )
            )
        })
    }

    companion object: Loggable()
}

class TransactionViewModelPreview(status : TransactionStatus) : TransactionViewModelAbstract(
    accountAssetOrNull = previewAccountAsset(),
    greenWallet = previewWallet(isHardware = false)
) {
    override val transaction: StateFlow<Transaction> = MutableStateFlow(Transaction.LoadingTransaction)

    override val status: StateFlow<TransactionStatus> = MutableStateFlow(status)
    override val type: StateFlow<Transaction.Type> = MutableStateFlow(Transaction.Type.IN)
    override val createdAtTs: StateFlow<Long> = MutableStateFlow(Clock.System.now().toEpochMilliseconds() * 1000L)
    override val spv: StateFlow<Transaction.SPVResult> = MutableStateFlow(Transaction.SPVResult.Disabled)
    override val amounts: StateFlow<List<AmountAssetLook>> =
        MutableStateFlow(listOf(AmountAssetLook("121.91080032", assetId = BTC_POLICY_ASSET ,ticker = "BTC", fiat = "32.1231 EUR")))
    override val transactionId: StateFlow<String> = MutableStateFlow("tx_id")
    override val fee: StateFlow<String> = MutableStateFlow("56.960 sats")
    override val feeRate: StateFlow<String> = MutableStateFlow("8.34 sats / vbyte")
    override val canReplaceByFee: StateFlow<Boolean> = MutableStateFlow(true)
    override val address: StateFlow<String?> = MutableStateFlow("bc1qaqtq80759n35gk6ftc57vh7du83nwvt5lgkznu")
    override val note: StateFlow<String?> =
        MutableStateFlow("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")
    override val hasMoreDetails: StateFlow<Boolean> = MutableStateFlow(true)

    companion object {
        fun previewUnconfirmed() = TransactionViewModelPreview(Unconfirmed())
        fun previewConfirmed() = TransactionViewModelPreview(Confirmed(confirmations = 3, confirmationsRequired = 6))
        fun previewCompleted() = TransactionViewModelPreview(Completed)
        fun previewFailed() = TransactionViewModelPreview(Failed())
    }
}