package vadym.my.wastesorting.di.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import vadym.my.wastesorting.data.camera.FilesStorage
import vadym.my.wastesorting.data.camera.FilesStorageImpl
import vadym.my.wastesorting.data.prefs.SharedPrefs
import vadym.my.wastesorting.data.prefs.SharedPrefsImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class StorageModule {

    @Binds
    abstract fun bindFileStorage(filesStorageImpl: FilesStorageImpl): FilesStorage

    @Binds
    abstract fun bindSharedPrefs(sharedPrefsImpl: SharedPrefsImpl): SharedPrefs
}
