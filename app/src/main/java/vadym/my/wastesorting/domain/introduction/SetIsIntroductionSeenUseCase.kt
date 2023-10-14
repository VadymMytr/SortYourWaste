package vadym.my.wastesorting.domain.introduction

import dagger.Reusable
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import vadym.my.wastesorting.data.prefs.SharedPrefs
import vadym.my.wastesorting.domain.base.BaseUseCase

@Reusable
class SetIsIntroductionSeenUseCase @Inject constructor(private val sharedPrefs: SharedPrefs) : BaseUseCase<Boolean, Unit>() {

    override val executionDispatcher = Dispatchers.IO

    override suspend fun execute(parameters: Boolean) {
        sharedPrefs.isIntroductionSeen = parameters
    }
}
