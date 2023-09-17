package vadym.my.wastesorting.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

/**
 * Casts [SharedFlow] into the [MutableSharedFlow] and emits the given [value] in the given [scope].
 * @throws IllegalStateException if the flow can't be cast to the [MutableSharedFlow].
 */
fun <T> SharedFlow<T>.emit(value: T, scope: CoroutineScope) {
    (this as? MutableSharedFlow)?.emit(value, scope) ?: throw IllegalStateException("This SharedFlow isn't a MutableSharedFlow.")
}

/**
 * Emits the given [value] in the given [scope].
 */
fun <T> MutableSharedFlow<T>.emit(value: T, scope: CoroutineScope) {
    scope.launch {
        emit(value)
    }
}
