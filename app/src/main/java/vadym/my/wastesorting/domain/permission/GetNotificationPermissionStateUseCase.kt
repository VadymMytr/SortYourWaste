package vadym.my.wastesorting.domain.permission

import android.os.Build.VERSION_CODES.TIRAMISU
import androidx.annotation.RequiresApi
import dagger.Reusable
import javax.inject.Inject
import vadym.my.wastesorting.data.permissions.PermissionsChecker
import vadym.my.wastesorting.data.permissions.RuntimePermissionState
import vadym.my.wastesorting.domain.base.BaseNoParamsUseCase

@Reusable
@RequiresApi(TIRAMISU)
class GetNotificationPermissionStateUseCase @Inject constructor(private val permissionsChecker: PermissionsChecker) :
    BaseNoParamsUseCase<RuntimePermissionState.Notifications>() {

    override suspend fun execute(parameters: Unit) = permissionsChecker.getNotificationPermissionState()
}
