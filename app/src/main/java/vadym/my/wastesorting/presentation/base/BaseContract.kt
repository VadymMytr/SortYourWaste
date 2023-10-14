package vadym.my.wastesorting.presentation.base

/**
 * Extends every MVI screen state that stores all information about the screen
 */
interface BaseScreenState

/**
 * Extends every UI event that should be proceed
 * @see BaseVM.processUiEvent
 */
interface BaseUiEvent

/**
 * Extends every UI change that should be fired as one-time event.
 */
interface BaseSideEffect
