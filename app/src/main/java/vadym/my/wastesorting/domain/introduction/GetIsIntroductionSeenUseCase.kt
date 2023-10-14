package vadym.my.wastesorting.domain.introduction

import dagger.Reusable
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import vadym.my.wastesorting.data.prefs.SharedPrefs
import vadym.my.wastesorting.domain.base.BaseNoParamsUseCase

@Reusable
class GetIsIntroductionSeenUseCase @Inject constructor(private val sharedPrefs: SharedPrefs) : BaseNoParamsUseCase<Boolean>() {

    override val executionDispatcher = Dispatchers.IO

    override suspend fun execute(parameters: Unit): Boolean {
        return sharedPrefs.isIntroductionSeen
    }
}
