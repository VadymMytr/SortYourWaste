package vadym.my.wastesorting.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow

/**
 * Screen ViewModel that contains screen [reducer], processes all ui [EVENT]s and updates the screen [STATE].
 * @see BaseScreenState
 * @see BaseUiEvent
 */
abstract class BaseVM<STATE : BaseScreenState, EVENT : BaseUiEvent> : ViewModel() {

    /**
     * Current screen reducer that will reduce the ui [EVENT]s and update the screen [STATE].
     * @see screenState
     * @see onEvent
     */
    protected abstract val reducer: BaseReducer<STATE, EVENT>

    /**
     * Current screen [STATE] flow. Stores all required UI parameters that should be displayed on the screen.
     */
    val screenState: StateFlow<BaseScreenState> get() = reducer.screenState

    /**
     * Passes given [event] to process it via screen [reducer].
     * @param event the ui [EVENT] that should be reduced
     * @see [BaseReducer.reduce]
     */
    protected fun onEvent(event: EVENT) {
        reducer.reduce(event, viewModelScope)
    }
}
