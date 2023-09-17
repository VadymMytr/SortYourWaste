package vadym.my.wastesorting.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

abstract class BaseVM : ViewModel() {
    val navigationEvent: SharedFlow<NavDirections> = MutableSharedFlow()

    protected fun navigate(directions: NavDirections) {
        navigationEvent.emitInViewModelScope(directions)
    }

    protected fun <T> SharedFlow<T>.emitInViewModelScope(value: T) {
        viewModelScope.launch {
            emit(value)
        }
    }

    private suspend fun <T> SharedFlow<T>.emit(value: T) {
        (this as? MutableSharedFlow)?.emit(value) ?: throw IllegalStateException("This SharedFlow isn't a MutableSharedFlow.")
    }
}
