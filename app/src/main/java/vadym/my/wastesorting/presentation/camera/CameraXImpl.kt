package vadym.my.wastesorting.presentation.camera

import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.lifecycleScope
import java.util.concurrent.Executor
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import vadym.my.wastesorting.domain.camera.CreateNewPhotoFileUseCase
import vadym.my.wastesorting.presentation.camera.model.CameraScreen
import vadym.my.wastesorting.presentation.camera.model.MakePhotoResult
import vadym.my.wastesorting.utils.emit
import vadym.my.wastesorting.utils.letBoth

class CameraXImpl @Inject constructor(private val createNewPhotoFileUseCase: CreateNewPhotoFileUseCase) : CameraX {
    override val cameraResultFlow = MutableSharedFlow<MakePhotoResult>()

    override fun initialize(cameraScreen: CameraScreen) {
        this.cameraScreen = cameraScreen
        this.cameraController = createCameraController(cameraScreen)

        initializeCameraProviders(cameraScreen)
    }

    override suspend fun makePhoto() {
        val photoFile = createNewPhotoFileUseCase()
        val photoCaptureCallback = createCaptureCallback(photoFile.path)
        val fileOutputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        letBoth(cameraExecutor, imageCapture) { executor: Executor, capture: ImageCapture ->
            capture.takePicture(fileOutputOptions, executor, photoCaptureCallback)
        }
    }

    override fun destroy() {
        cameraController?.unbind()
        cameraProvider?.unbindAll()
        cameraScreen = null
        cameraController = null
        cameraProvider = null
        cameraExecutor = null
        imageCapture = null
    }

    private var cameraScreen: CameraScreen? = null
    private var cameraController: LifecycleCameraController? = null
    private var cameraProvider: ProcessCameraProvider? = null
    private var cameraExecutor: Executor? = null
    private var imageCapture: ImageCapture? = null

    private val screenLifecycleOwner get() = cameraScreen?.lifecycleOwner
    private val screenLifecycleScope get() = screenLifecycleOwner?.lifecycleScope

    /**
     * Creates the [LifecycleCameraController] from the [CameraScreen.previewView] context.
     * Updates the [CameraScreen.previewView] controller by created [LifecycleCameraController].
     * Binds controller to the [CameraScreen.lifecycleOwner] lifecycle.
     */
    private fun createCameraController(cameraScreen: CameraScreen): LifecycleCameraController {
        return LifecycleCameraController(cameraScreen.previewView.context).also {
            cameraScreen.previewView.controller = it
            it.bindToLifecycle(cameraScreen.lifecycleOwner)
        }
    }

    /**
     * Creates main camera providers that helps to make photo and display the camera preview.
     *
     * Instantiates [ProcessCameraProvider] from the [CameraScreen.previewView]'s context. Creates the [cameraProvider], [cameraExecutor]
     * and [imageCapture] and saves them into this class properties. Binds the [cameraProvider] to the [CameraScreen.lifecycleOwner].
     */
    private fun initializeCameraProviders(cameraScreen: CameraScreen) {
        val previewView = cameraScreen.previewView
        val cameraProviderFuture = ProcessCameraProvider.getInstance(previewView.context)

        fun createCameraProviders() {
            this.cameraProvider = cameraProviderFuture.get()

            previewView.doOnPreDraw {
                this.imageCapture = ImageCapture.Builder().setTargetResolution(Size(previewView.width, previewView.height)).build()

                try {
                    val cameraPreview = Preview.Builder().build().apply {
                        setSurfaceProvider(previewView.surfaceProvider)
                    }

                    cameraProvider?.unbindAll()
                    cameraProvider?.bindToLifecycle(
                        cameraScreen.lifecycleOwner,
                        CameraSelector.DEFAULT_BACK_CAMERA,
                        cameraPreview,
                        imageCapture
                    )
                } catch (exc: Exception) {
                    exc.printStackTrace()
                }
            }
        }

        this.cameraExecutor = ContextCompat.getMainExecutor(previewView.context).also {
            cameraProviderFuture.addListener(::createCameraProviders, it)
        }
    }

    /**
     * Creates the camera capture callback and emit the [cameraResultFlow] in [screenLifecycleScope] every time the capture is successful or failed.
     */
    private fun createCaptureCallback(path: String): ImageCapture.OnImageSavedCallback {
        fun MakePhotoResult.post() {
            val scope = screenLifecycleScope ?: return
            cameraResultFlow.emit(this, scope)
        }

        return object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                MakePhotoResult.Success(path).post()
            }

            override fun onError(exception: ImageCaptureException) {
                MakePhotoResult.Failure.post()
            }
        }
    }
}
