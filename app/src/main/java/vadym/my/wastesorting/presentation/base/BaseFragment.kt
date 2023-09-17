package vadym.my.wastesorting.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withResumed
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import factory.BindingFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

abstract class BaseFragment<VIEW_MODEL : BaseVM, VIEW_BINDING : ViewBinding> : Fragment() {

    abstract val viewModel: VIEW_MODEL
    abstract val observe: VIEW_MODEL.() -> Unit

    private var binding: VIEW_BINDING? = null
    open val viewBinding: VIEW_BINDING get() = binding ?: throw Throwable("ViewBinding must not be null")

    private val navController by lazy {
        findNavController()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = BindingFactory.getBinding<VIEW_BINDING>(this, null)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        with(viewModel) {
            navigationEvent.observeWhenResumed(::navigate)
            observe()
        }
    }

    private fun navigate(directions: NavDirections) {
        navController.navigate(directions)
    }

    protected inline fun <T> Flow<T>.observeInLifecycle(crossinline observer: (T) -> Unit) {
        this.onEach { observer(it) }.launchIn(lifecycleScope)
    }

    protected inline fun <T> Flow<T>.observeWhenResumed(crossinline observer: (T) -> Unit) {
        lifecycleScope.launch { withResumed { observeInLifecycle(observer) } }
    }
}
