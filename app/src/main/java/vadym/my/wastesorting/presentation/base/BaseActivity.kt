package vadym.my.wastesorting.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import factory.BindingFactory

abstract class BaseActivity<VIEW_MODEL : BaseVM, VIEW_BINDING : ViewBinding> : AppCompatActivity() {

    private var binding: VIEW_BINDING? = null
    open val viewBinding: VIEW_BINDING get() = binding ?: throw Throwable("ViewBinding must not be null")

    abstract val viewModel: VIEW_MODEL
    abstract val observe: VIEW_MODEL.() -> Unit

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
        viewModel.observe()
    }
}
