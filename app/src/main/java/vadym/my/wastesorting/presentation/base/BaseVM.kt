package vadym.my.wastesorting.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import vadym.my.wastesorting.utils.emit

abstract class BaseVM : ViewModel() {
    val navigationEvent: SharedFlow<NavDirections> = MutableSharedFlow()

    protected fun navigate(directions: NavDirections) {
        navigationEvent.emitInViewModelScope(directions)
    }

    protected fun <T> SharedFlow<T>.emitInViewModelScope(value: T) {
        this.emit(value, viewModelScope)
    }
}
