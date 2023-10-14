package vadym.my.wastesorting.presentation.introduction

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import vadym.my.wastesorting.data.permissions.RuntimePermissionState
import vadym.my.wastesorting.domain.introduction.GetIsIntroductionSeenUseCase
import vadym.my.wastesorting.domain.introduction.SetIsIntroductionSeenUseCase
import vadym.my.wastesorting.domain.permission.GetAllRequiredPermissionsStatesUseCase
import vadym.my.wastesorting.presentation.base.BaseVM

@HiltViewModel
class IntroductionViewModel @Inject constructor(
    private val getAllRequiredPermissionsStatesUseCase: GetAllRequiredPermissionsStatesUseCase,
    private val getIsIntroductionSeenUseCase: GetIsIntroductionSeenUseCase,
    private val setIsIntroductionSeenUseCase: SetIsIntroductionSeenUseCase,
    private val permissionInfoMapper: PermissionInfoMapper,
) : BaseVM<IntroductionState, IntroductionUiEvent, IntroductionSideEffect>() {

    override val initialState = IntroductionState.Initial

    override suspend fun processUiEvent(state: IntroductionState, event: IntroductionUiEvent) {
        when (event) {
            IntroductionUiEvent.NextButtonClick -> handleNextButtonClick(state)
            IntroductionUiEvent.SkipButtonClick, IntroductionUiEvent.PermissionRequestFinished -> handleSkipButtonClick(state)
        }
    }

    init {
        viewModelScope.launch {
            setupIntroductionScreens(
                isIntroductionSeen = getIsIntroductionSeenUseCase(),
                permissionsStates = getAllRequiredPermissionsStatesUseCase(),
            )
        }
    }

    /**
     * Updates the [currentScreenState] with introduction or permissions screens if it's required to display them. Otherwise will [finishFlow].
     */
    private suspend fun setupIntroductionScreens(isIntroductionSeen: Boolean, permissionsStates: List<RuntimePermissionState>) {
        // Only non-granted permissions should be displayed in the introduction flow.
        val notGrantedPermissions = permissionsStates.filterNot { it.isGranted }
        val introductionScreensList = getIntroductionScreens(isIntroductionSeen, notGrantedPermissions)

        // If the user already seen the introduction screen and all permissions were granted, should pass to the home screen instead of
        // navigating via introduction flow.
        val shouldFinishFlow = introductionScreensList.isEmpty()
        if (shouldFinishFlow) {
            finishFlow()
            return
        }

        // Otherwise should add all introductions screens models to the state to be displayed at the UI.
        updateScreenState { currentState ->
            currentState.copy(introductionScreens = introductionScreensList)
        }
    }

    /**
     * @return all screens that should be displayed in the introduction flow (including the first introduction screen and all non-granted permissions screens).
     */
    private fun getIntroductionScreens(
        isIntroductionSeen: Boolean,
        permissionsStates: List<RuntimePermissionState>,
    ): List<IntroductionScreenInfo> = mutableListOf<IntroductionScreenInfo>().apply {
        // Firstly adding the introduction screen if it wasn't seen yet.
        if (!isIntroductionSeen) add(IntroductionScreenInfo.Introduction)

        // Then should be displayed all required permissions screens.
        addAll(permissionsStates.map(permissionInfoMapper::map))
    }

    private suspend fun handleNextButtonClick(state: IntroductionState) {
        val isIntroductionScreen = state.currentScreenInfo is IntroductionScreenInfo.Introduction

        // If the current screen is introduction, should navigate to the permission flow and mark that introduction screen was already seen.
        // Otherwise request the permission.
        if (isIntroductionScreen) {
            setIsIntroductionSeenUseCase(parameters = true)
            navigateToTheNextIntroductionScreen()
        } else {
            val permissionCode = (state.currentScreenInfo as? IntroductionScreenInfo.Permission)?.code ?: return
            sendSideEffect(IntroductionSideEffect.RequestPermission(permissionCode))
        }
    }

    private suspend fun handleSkipButtonClick(state: IntroductionState) {
        if (state.isTheLastScreen) {
            finishFlow()
        } else {
            navigateToTheNextIntroductionScreen()
        }
    }

    /**
     * Updates the [currentScreenState] via incrementing the [IntroductionState.currentScreenIndex] param by one.
     * This will lead to opening the next [IntroductionScreenInfo] on the UI.
     */
    private suspend fun navigateToTheNextIntroductionScreen() {
        updateScreenState { it.copy(currentScreenIndex = it.currentScreenIndex + 1) }
    }

    /**
     * @return current screen info that was received from [IntroductionState.introductionScreens] list by [IntroductionState.currentScreenIndex]
     */
    private val IntroductionState.currentScreenInfo: IntroductionScreenInfo? get() = introductionScreens.getOrNull(currentScreenIndex)

    /**
     * @return true if the currently displayed screen is the last screen. false otherwise.
     */
    private val IntroductionState.isTheLastScreen: Boolean get() = this.currentScreenIndex == this.introductionScreens.lastIndex

    /**
     * Finishes the introduction flow by navigating to the home screen.
     */
    private suspend fun finishFlow() {
        sendSideEffect(IntroductionSideEffect.NavigateHome)
    }
}
