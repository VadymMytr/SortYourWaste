package vadym.my.wastesorting.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import vadym.my.wastesorting.utils.extensions.emit

/**
 * Screen ViewModel that processes all ui [EVENT]s, updates the screen [STATE] and sends the one-time [SIDE_EFFECT] events.
 */
abstract class BaseVM<STATE : BaseScreenState, EVENT : BaseUiEvent, SIDE_EFFECT : BaseSideEffect> : ViewModel() {

    /**
     * Initial (default) [STATE] of the screen. Is used to display the placeholder data before screen was loaded or any action happened
     */
    protected abstract val initialState: STATE

    /**
     * Processes internally the action required by the given ui [event]. The methods like [updateScreenState] or [sendSideEffect] may be called from here.
     * @param state the current screen [STATE]
     * @param event the ui [EVENT] that should be proceed
     */
    protected abstract suspend fun processUiEvent(state: STATE, event: EVENT)

    /**
     * Flow that stores current screen [STATE] value with it's specific parameters.
     */
    val screenState: StateFlow<STATE> by lazy {
        MutableStateFlow(initialState)
    }

    /**
     * Flow that fires the [SIDE_EFFECT] each time it's required.
     */
    val sideEffect: Flow<SIDE_EFFECT> by lazy {
        sideEffectChannel.receiveAsFlow()
    }

    /**
     * Processes the required ui [event] and reacts correspondingly. Starts an update job that will be cancelled if the next [EVENT] will be received.
     * @param event the ui [EVENT] that should be proceed
     * @see processUiEvent
     * @see eventProcessingJob
     */
    fun onUiEvent(event: EVENT) {
        eventProcessingJob?.cancel()
        eventProcessingJob = viewModelScope.launch {
            processUiEvent(currentScreenState, event)
        }
    }

    /**
     * All [SIDE_EFFECT] events channel.
     */
    private val sideEffectChannel = Channel<SIDE_EFFECT>()

    /**
     * Current event processing job that is required to prevent from calling [processUiEvent] in the different threads cause it may
     * create some race conditions. Should be cancelled and started as new job on each time [onUiEvent] is called.
     */
    private var eventProcessingJob: Job? = null

    /**
     * The current [STATE] of the screen.
     */
    private val currentScreenState: STATE get() = screenState.value

    /**
     * Updates the [screenState] by passed [transformation]. Emits the [screenState] flow.
     * @param transformation required transformation from the old screen [STATE] to the new screen [STATE]
     */
    protected suspend fun updateScreenState(transformation: suspend (currentState: STATE) -> STATE) {
        val updatedState = transformation(currentScreenState)
        if (updatedState != currentScreenState) {
            screenState.emit(updatedState)
        }
    }

    /**
     * Sends the given [effect] via [sideEffectChannel].
     * @param effect the [SIDE_EFFECT] should be sent
     */
    protected suspend fun sendSideEffect(effect: SIDE_EFFECT) {
        sideEffectChannel.send(effect)
    }
}
