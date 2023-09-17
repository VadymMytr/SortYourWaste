package vadym.my.wastesorting.presentation.camera.model

import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner

/**
 * Represents the fragment that uses the camera.
 * @param lifecycleOwner fragment's lifecycle owner that should be attached to the camera
 * @param previewView fragment's view that will display the camera preview before user makes the photo
 */
class CameraScreen(val lifecycleOwner: LifecycleOwner, val previewView: PreviewView)
