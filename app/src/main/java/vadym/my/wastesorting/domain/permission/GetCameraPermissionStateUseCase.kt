package vadym.my.wastesorting.domain.permission

import dagger.Reusable
import javax.inject.Inject
import vadym.my.wastesorting.data.permissions.PermissionsChecker
import vadym.my.wastesorting.data.permissions.RuntimePermissionState
import vadym.my.wastesorting.domain.base.BaseNoParamsUseCase

@Reusable
class GetCameraPermissionStateUseCase @Inject constructor(private val permissionsChecker: PermissionsChecker) :
    BaseNoParamsUseCase<RuntimePermissionState.Camera>() {

    override suspend fun execute(parameters: Unit) = permissionsChecker.getCameraPermissionState()
}
