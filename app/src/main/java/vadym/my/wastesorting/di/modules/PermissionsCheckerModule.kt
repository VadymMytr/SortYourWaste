package vadym.my.wastesorting.di.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import vadym.my.wastesorting.data.permissions.PermissionsChecker
import vadym.my.wastesorting.data.permissions.PermissionsCheckerImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class PermissionsCheckerModule {

    @Binds
    abstract fun bindPermissionChecker(permissionsCheckerImpl: PermissionsCheckerImpl): PermissionsChecker
}
