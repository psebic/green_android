package com.blockstream.common.extensions

import com.blockstream.common.CountlyBase
import com.blockstream.common.data.AppInfo
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.coroutineScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.withContext
import org.koin.mp.KoinPlatformTools


// Log and handle the exception. Prevent unhanded exception crash
suspend fun <T> tryCatch(block: suspend () -> T): T? = withContext(context = logException()) {
    block()
}

fun logException(
    countly: CountlyBase
): CoroutineExceptionHandler {
    return CoroutineExceptionHandler { _, exception ->
        if (KoinPlatformTools.defaultContext().get().get<AppInfo>().isDevelopmentOrDebug) {
            exception.printStackTrace()
        }

        countly.recordException(exception)
    }
}

fun logException(): CoroutineExceptionHandler {
    return CoroutineExceptionHandler { _, exception ->
        if (KoinPlatformTools.defaultContext().get().get<AppInfo>().isDevelopmentOrDebug) {
            exception.printStackTrace()
        }

        KoinPlatformTools.defaultContext().get().getOrNull<CountlyBase>()?.also {
            it.recordException(exception)
        }
    }
}

fun handleException(): CoroutineExceptionHandler {
    return CoroutineExceptionHandler { _, exception ->
        if (KoinPlatformTools.defaultContext().get().get<AppInfo>().isDevelopmentOrDebug) {
            exception.printStackTrace()
        }
    }
}

fun MutableStateFlow<Boolean>.toggle() {
    this.value = !value
}

public fun <T> Flow<T>.launchIn(viewModel: ViewModel) = launchIn(viewModel.viewModelScope.coroutineScope)

fun CoroutineScope.childScope() =
    CoroutineScope(coroutineContext + Job(coroutineContext[Job]))

fun CoroutineScope.cancelChildren(
    cause: CancellationException? = null
) = coroutineContext[Job]?.cancelChildren(cause)
