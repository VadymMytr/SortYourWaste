package vadym.my.wastesorting.presentation.main

import androidx.activity.viewModels
import com.kobanister.viewbindingannotations.annotation.BindActivity
import dagger.hilt.android.AndroidEntryPoint
import vadym.my.wastesorting.databinding.ActivityMainBinding
import vadym.my.wastesorting.presentation.base.BaseActivity

@BindActivity
@AndroidEntryPoint
class MainActivity : BaseActivity<MainVM, ActivityMainBinding>() {
    override val viewModel: MainVM by viewModels()
    override val observe: MainVM.() -> Unit = {}
}
