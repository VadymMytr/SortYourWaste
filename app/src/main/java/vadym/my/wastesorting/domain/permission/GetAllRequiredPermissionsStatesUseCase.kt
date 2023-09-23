package vadym.my.wastesorting.domain.permission

import android.os.Build
import android.os.Build.VERSION_CODES.TIRAMISU
import dagger.Reusable
import javax.inject.Inject
import vadym.my.wastesorting.data.permissions.PermissionsChecker
import vadym.my.wastesorting.data.permissions.RuntimePermissionState
import vadym.my.wastesorting.domain.base.BaseNoParamsUseCase

@Reusable
class GetAllRequiredPermissionsStatesUseCase @Inject constructor(
    private val permissionsChecker: PermissionsChecker,
) : BaseNoParamsUseCase<List<RuntimePermissionState>>() {

    override suspend fun execute(parameters: Unit) = mutableListOf<RuntimePermissionState>().apply {
        add(permissionsChecker.getCameraPermissionState())

        // As notification permission is only for the TIRAMISU and later Android API version, should check for it to prevent from
        // checking the new permission on an old device
        if (Build.VERSION.SDK_INT >= TIRAMISU) {
            add(permissionsChecker.getNotificationPermissionState())
        }
    }
}
