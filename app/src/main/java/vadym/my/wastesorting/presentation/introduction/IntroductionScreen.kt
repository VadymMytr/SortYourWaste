package vadym.my.wastesorting.presentation.introduction

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import vadym.my.wastesorting.R
import vadym.my.wastesorting.presentation.theme.ModifierFullWidth
import vadym.my.wastesorting.presentation.theme.PrimaryButton
import vadym.my.wastesorting.presentation.theme.PrimaryText
import vadym.my.wastesorting.presentation.theme.PrimaryTextButton
import vadym.my.wastesorting.presentation.theme.TitleText
import vadym.my.wastesorting.presentation.theme.getColorPalette
import vadym.my.wastesorting.presentation.theme.imageIntroductionHeight
import vadym.my.wastesorting.presentation.theme.introPageIndicatorContainerHeight
import vadym.my.wastesorting.presentation.theme.introPageIndicatorCorner
import vadym.my.wastesorting.presentation.theme.introPageIndicatorItemHeight
import vadym.my.wastesorting.presentation.theme.introPageIndicatorItemWidth
import vadym.my.wastesorting.presentation.theme.padding1X
import vadym.my.wastesorting.presentation.theme.padding2X
import vadym.my.wastesorting.presentation.theme.padding3X
import vadym.my.wastesorting.utils.extensions.observeWithLifecycle

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IntroductionScreen(viewModel: IntroductionViewModel = hiltViewModel(), navigateHome: () -> Unit) {
    val state by viewModel.screenState.collectAsState()
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { viewModel.onUiEvent(IntroductionUiEvent.PermissionRequestFinished) },
    )

    viewModel.sideEffect.observeWithLifecycle { sideEffect ->
        when (sideEffect) {
            is IntroductionSideEffect.NavigateHome -> navigateHome()
            is IntroductionSideEffect.RequestPermission -> permissionLauncher.launch(arrayOf(sideEffect.code))
        }
    }

    val pagerState = rememberPagerState { state.screensAmount }

    LaunchedEffect(key1 = state.currentScreenIndex) {
        pagerState.animateScrollToPage(state.currentScreenIndex)
    }

    HorizontalPager(
        state = pagerState,
        userScrollEnabled = false,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = padding2X),
    ) { pageIndex ->
        val currentScreen = state.introductionScreens.getOrNull(pageIndex) ?: return@HorizontalPager

        Column {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
                IntroductionScreenContent(screenInfo = currentScreen)
            }

            IntroductionScreenButtons(
                screenInfo = currentScreen,
                onNextButtonClick = { viewModel.onUiEvent(IntroductionUiEvent.NextButtonClick) },
                onSkipButtonClick = { viewModel.onUiEvent(IntroductionUiEvent.SkipButtonClick) },
            )

            PageIndicator(pagesAmount = pagerState.pageCount, currentPosition = pageIndex)
        }
    }
}

@Composable
fun IntroductionScreenContent(screenInfo: IntroductionScreenInfo) {
    Image(
        painter = painterResource(id = screenInfo.iconDrawable),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(imageIntroductionHeight),
    )

    TitleText(stringRes = screenInfo.title)
    PrimaryText(stringRes = screenInfo.description, modifier = ModifierFullWidth.padding(top = padding3X))
}

@Composable
fun IntroductionScreenButtons(screenInfo: IntroductionScreenInfo, onNextButtonClick: () -> Unit, onSkipButtonClick: () -> Unit) {
    PrimaryButton(
        onClick = onNextButtonClick,
        stringRes = when (screenInfo) {
            is IntroductionScreenInfo.Introduction -> R.string.intro_go_btn
            is IntroductionScreenInfo.Permission -> R.string.permissions_allow_btn
        },
    )

    if (screenInfo is IntroductionScreenInfo.Permission) {
        PrimaryTextButton(onClick = onSkipButtonClick, stringRes = R.string.permissions_skip_btn)
    }
}

@Composable
fun PageIndicator(pagesAmount: Int, currentPosition: Int) {
    // Should display 2 or more indications only to be able to display the progress of introduction.
    // If only one screen should be displayed, we shouldn't display the page indicator with one item only that is selected.
    if (pagesAmount <= 1) return

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(top = padding2X)
            .fillMaxWidth()
            .height(introPageIndicatorContainerHeight),
    ) {
        repeat(pagesAmount) { iteration ->
            val colorPalette = getColorPalette()
            val indicatorFillColor = if (currentPosition == iteration) colorPalette.primary else colorPalette.onPrimary

            Box(
                modifier = Modifier
                    .padding(padding1X)
                    .width(introPageIndicatorItemWidth)
                    .height(introPageIndicatorItemHeight)
                    .clip(RoundedCornerShape(introPageIndicatorCorner))
                    .background(indicatorFillColor),
            )
        }
    }
}
