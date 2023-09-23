package vadym.my.wastesorting.presentation.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import vadym.my.wastesorting.utils.emit

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
     * Reduces the [event] and performs required actions with it. Then updates [screenState] with passed [scope].
     * @param event event that should be reduced
     * @param scope the scope in which [screenState] should be updated. Usually should be the viewModelScope
     */
    fun reduce(event: EVENT, scope: CoroutineScope) {
        reduce(screenState.value, event, scope)
    }

    /**
     * Reduces the [event] and performs required actions with it. Then updates [screenState] with passed [scope].
     * @param state current screen state from which the state should be copied
     * @param event event that should be reduced
     * @param scope the scope in which [screenState] should be updated. Usually should be the viewModelScope
     */
    protected abstract fun reduce(state: STATE, event: EVENT, scope: CoroutineScope)

    /**
     * Emits the [screenState] update in the required [scope].
     * @param state new screen state that should be updated
     * @param scope the scope in which [screenState] will be emitted
     */
    protected fun setState(state: STATE, scope: CoroutineScope) {
        screenState.emit(state, scope)
    }
}
