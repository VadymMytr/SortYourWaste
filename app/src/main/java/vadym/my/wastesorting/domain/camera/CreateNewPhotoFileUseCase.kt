package vadym.my.wastesorting.domain.camera

import dagger.Reusable
import java.io.File
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import vadym.my.wastesorting.data.camera.FilesStorage
import vadym.my.wastesorting.domain.base.BaseNoParamsUseCase

@Reusable
class CreateNewPhotoFileUseCase @Inject constructor(private val filesStorage: FilesStorage) : BaseNoParamsUseCase<File>() {

    override val executionDispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun execute(parameters: Unit): File {
        return filesStorage.createCachedImageFile()
    }
}
