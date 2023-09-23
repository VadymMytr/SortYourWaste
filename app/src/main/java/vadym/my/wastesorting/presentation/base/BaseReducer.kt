package vadym.my.wastesorting.presentation.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import vadym.my.wastesorting.utils.extensions.emit

/**
 * Screen events reducer. Receives any ui [EVENT], process it and updates the screen [STATE].
 * @param initialState default state of the screen
 */
abstract class BaseReducer<STATE : BaseScreenState, EVENT : BaseUiEvent>(initialState: STATE) {

    /**
     * Flow that stores current [STATE] of the screen. Should be updated on each [reduce] call.
     */
    val screenState: StateFlow<STATE> = MutableStateFlow(initialState)

    /**
     * Reduces the [event] in the passed [scope]. Then emits the [screenState] change.
     * @param event event that should be reduced
     * @param scope the scope in which [screenState] should be updated. Usually should be the viewModelScope
     */
    fun reduce(event: EVENT, scope: CoroutineScope) {
        scope.launch {
            val updatedState = reduce(screenState.value, event)
            screenState.emit(updatedState)
        }
    }

    /**
     * Reduces the [event] and performs required actions with it.
     * @param state current screen state from which the state should be copied
     * @param event event that should be reduced
     */
    protected abstract suspend fun reduce(state: STATE, event: EVENT): STATE
}
