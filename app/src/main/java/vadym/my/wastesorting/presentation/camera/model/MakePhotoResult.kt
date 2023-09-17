package vadym.my.wastesorting.presentation.camera.model

/**
 * Result of the camera making photo process.
 * Is the photo taken successfully, [Success] instance should be created with the [Success.picturePath] to the taken photo file.
 * Otherwise [Failure] object should be used.
 */
sealed interface MakePhotoResult {
    data class Success(val picturePath: String) : MakePhotoResult
    object Failure : MakePhotoResult
}
