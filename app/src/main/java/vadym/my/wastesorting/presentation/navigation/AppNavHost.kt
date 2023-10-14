package vadym.my.wastesorting.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import vadym.my.wastesorting.presentation.introduction.IntroductionScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavigationDestinations.INTRODUCTION,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        addNavigationGraph()
    }
}

private fun NavGraphBuilder.addNavigationGraph() {
    composable(NavigationDestinations.INTRODUCTION) {
        IntroductionScreen(navigateHome = {
        })
    }

    composable(NavigationDestinations.HOME) {
    }
}
