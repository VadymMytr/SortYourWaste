package vadym.my.wastesorting.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import vadym.my.wastesorting.presentation.navigation.AppNavHost
import vadym.my.wastesorting.presentation.theme.AppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val systemUiController = rememberSystemUiController()
                val primaryColor = MaterialTheme.colorScheme.primary
                SideEffect {
                    systemUiController.setStatusBarColor(color = primaryColor, darkIcons = false)
                }

                AppNavHost()
            }
        }
    }
}
