package vadym.my.wastesorting.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import vadym.my.wastesorting.data.camera.FilesStorage
import vadym.my.wastesorting.data.camera.FilesStorageImpl

@Module
@InstallIn(SingletonComponent::class)
class FileStorageModule {

    @Provides
    @Singleton
    fun provideFilesStorage(@ApplicationContext context: Context): FilesStorage = FilesStorageImpl(context)
}
