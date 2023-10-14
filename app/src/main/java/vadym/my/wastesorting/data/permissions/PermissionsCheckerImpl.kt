package vadym.my.wastesorting.data.permissions

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build.VERSION_CODES.TIRAMISU
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PermissionsCheckerImpl @Inject constructor(@ApplicationContext private val context: Context) : PermissionsChecker {

    override fun getCameraPermissionState(): RuntimePermissionState.Camera {
        val code = android.Manifest.permission.CAMERA
        val isGranted = isPermissionGranted(code = code)

        return RuntimePermissionState.Camera(code, isGranted)
    }

    @RequiresApi(TIRAMISU)
    override fun getNotificationPermissionState(): RuntimePermissionState.Notifications {
        val code = android.Manifest.permission.POST_NOTIFICATIONS
        val isGranted = isPermissionGranted(code = code)

        return RuntimePermissionState.Notifications(code, isGranted)
    }

    private fun isPermissionGranted(code: String): Boolean {
        return ContextCompat.checkSelfPermission(context, code) == PackageManager.PERMISSION_GRANTED
    }
}
