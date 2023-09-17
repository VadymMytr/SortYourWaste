package vadym.my.wastesorting.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withResumed
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.viewbinding.ViewBinding
import factory.BindingFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import vadym.my.wastesorting.R

abstract class BaseActivity<VIEW_MODEL : BaseVM, VIEW_BINDING : ViewBinding> : AppCompatActivity() {

    abstract val viewModel: VIEW_MODEL
    abstract val observe: VIEW_MODEL.() -> Unit

    private var binding: VIEW_BINDING? = null
    open val viewBinding: VIEW_BINDING get() = binding ?: throw Throwable("ViewBinding must not be null")

    private val navController by lazy {
        findNavController(R.id.fragment_container_view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        provideViewBinding()
        observeViewModel()
    }

    private fun provideViewBinding() {
        this.binding = BindingFactory.getBinding<VIEW_BINDING>(this, null).also {
            setContentView(it.root)
        }
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
