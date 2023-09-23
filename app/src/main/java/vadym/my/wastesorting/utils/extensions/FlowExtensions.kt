package vadym.my.wastesorting.utils.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Casts [StateFlow] into the [MutableStateFlow] and emits the given [value].
 * @throws IllegalStateException if the flow can't be cast to the [MutableStateFlow].
 */
suspend fun <T> StateFlow<T>.emit(value: T) {
    (this as? MutableStateFlow)?.emit(value) ?: throw IllegalStateException("This StateFlow isn't a MutableStateFlow.")
}

/**
 * Casts [StateFlow] into the [MutableStateFlow] and emits the given [value] in the given [scope].
 * @throws IllegalStateException if the flow can't be cast to the [MutableStateFlow].
 */
fun <T> StateFlow<T>.emit(value: T, scope: CoroutineScope) {
    (this as? MutableStateFlow)?.emit(value, scope) ?: throw IllegalStateException("This StateFlow isn't a MutableStateFlow.")
}

/**
 * Emits the given [value] in the given [scope].
 */
fun <T> MutableStateFlow<T>.emit(value: T, scope: CoroutineScope) {
    scope.launch {
        emit(value)
    }
}
