package vadym.my.wastesorting.data.permissions

import android.os.Build.VERSION_CODES.TIRAMISU
import androidx.annotation.RequiresApi

/**
 * Used to check that required runtime permissions are granted
 */
interface PermissionsChecker {

    /**
     * @return the state of the camera runtime permission with it's grant result
     */
    fun getCameraPermissionState(): RuntimePermissionState.Camera

    /**
     * Note: requires the [TIRAMISU] Android API at least.
     * @return the state of the notifications runtime permission with it's grant result
     */
    @RequiresApi(TIRAMISU)
    fun getNotificationPermissionState(): RuntimePermissionState.Notifications
}
