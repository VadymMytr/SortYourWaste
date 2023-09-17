package vadym.my.wastesorting.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import vadym.my.wastesorting.domain.camera.CreateNewPhotoFileUseCase
import vadym.my.wastesorting.presentation.camera.CameraX
import vadym.my.wastesorting.presentation.camera.CameraXImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
class CameraModule {

    @Provides
    fun provideCameraX(createNewPhotoFileUseCase: CreateNewPhotoFileUseCase): CameraX = CameraXImpl(createNewPhotoFileUseCase)
}
