package vadym.my.wastesorting.presentation.onboarding

import javax.annotation.concurrent.Immutable
import vadym.my.wastesorting.presentation.base.BaseScreenState

@Immutable
data class IntroductionState(val fileName: String) : BaseScreenState {

    companion object {
        val Initial = IntroductionState(fileName = "NO_FILE")
    }
}
