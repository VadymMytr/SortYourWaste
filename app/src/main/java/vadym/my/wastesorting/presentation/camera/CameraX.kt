package vadym.my.wastesorting.presentation.camera

import androidx.fragment.app.Fragment
import kotlinx.coroutines.flow.SharedFlow
import vadym.my.wastesorting.presentation.camera.model.CameraScreen
import vadym.my.wastesorting.presentation.camera.model.MakePhotoResult

interface CameraX {
    /**
     * Emits every time after [makePhoto] call if the camera is initialized (via [initialize] method). Post [MakePhotoResult] as value
     */
    val cameraResultFlow: SharedFlow<MakePhotoResult>

    /**
     * Creates all camera required providers and instances and attaches the [cameraScreen] to it. Is required to [initialize] the camera
     * before the [makePhoto] call (otherwise nothing will be happened)
     */
    fun initialize(cameraScreen: CameraScreen)

    /**
     * Makes the photo. The camera should be attached to the [CameraScreen] (via [initialize] method) before this call
     */
    suspend fun makePhoto()

    /**
     * Destroys all camera related instances. Should be called on [Fragment.onDestroyView] to prevent from memory leaks
     */
    fun destroy()
}
