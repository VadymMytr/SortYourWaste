package vadym.my.wastesorting.presentation.introduction

import javax.inject.Inject
import javax.inject.Singleton
import vadym.my.wastesorting.R
import vadym.my.wastesorting.data.permissions.RuntimePermissionState

/**
 * Mapper that allow to receive [IntroductionScreenInfo] from the given [RuntimePermissionState].
 */
@Singleton
class PermissionInfoMapper @Inject constructor() {

    /**
     * Maps the given [permissionState] to the [IntroductionScreenInfo].
     * @param permissionState state should be mapped into [IntroductionScreenInfo]
     * @return [IntroductionScreenInfo] with required icon, title, description and code based on the type of the permission.
     */
    fun map(permissionState: RuntimePermissionState): IntroductionScreenInfo {
        return when (permissionState) {
            is RuntimePermissionState.Camera -> IntroductionScreenInfo.Permission(
                iconDrawable = R.drawable.ic_permission_camera,
                title = R.string.camera_permission_title,
                description = R.string.camera_permission_description,
                code = permissionState.code,
            )

            is RuntimePermissionState.Notifications -> IntroductionScreenInfo.Permission(
                iconDrawable = R.drawable.ic_permission_notification,
                title = R.string.notifications_permission_title,
                description = R.string.notifications_permission_description,
                code = permissionState.code,
            )
        }
    }
}
