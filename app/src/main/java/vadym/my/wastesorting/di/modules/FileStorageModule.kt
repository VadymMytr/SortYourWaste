package vadym.my.wastesorting.di.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import vadym.my.wastesorting.data.camera.FilesStorage
import vadym.my.wastesorting.data.camera.FilesStorageImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class FileStorageModule {

    @Binds
    abstract fun bindFileStorage(filesStorageImpl: FilesStorageImpl): FilesStorage
}
