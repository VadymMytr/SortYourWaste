package vadym.my.wastesorting.presentation.introduction

import javax.annotation.concurrent.Immutable
import vadym.my.wastesorting.presentation.base.BaseScreenState
import vadym.my.wastesorting.presentation.base.BaseSideEffect
import vadym.my.wastesorting.presentation.base.BaseUiEvent

sealed interface IntroductionUiEvent : BaseUiEvent {
    object PermissionRequestFinished : IntroductionUiEvent
    object NextButtonClick : IntroductionUiEvent
    object SkipButtonClick : IntroductionUiEvent
}

@Immutable
data class IntroductionState(
    val introductionScreens: List<IntroductionScreenInfo>,
    val currentScreenIndex: Int,
) : BaseScreenState {
    val screensAmount get() = introductionScreens.size

    companion object {
        val Initial = IntroductionState(introductionScreens = emptyList(), currentScreenIndex = 0)
    }
}

sealed interface IntroductionSideEffect : BaseSideEffect {
    class RequestPermission(val code: String) : IntroductionSideEffect
    object NavigateHome : IntroductionSideEffect
}
