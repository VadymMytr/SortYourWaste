package vadym.my.wastesorting.data.permissions

sealed class RuntimePermissionState(val code: String, val isGranted: Boolean) {
    class Camera(code: String, isGranted: Boolean) : RuntimePermissionState(code, isGranted)
    class Notifications(code: String, isGranted: Boolean) : RuntimePermissionState(code, isGranted)
}
