package vadym.my.wastesorting.data.camera

import android.content.Context
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FilesStorageImpl @Inject constructor(context: Context) : FilesStorage {

    override fun createCachedImageFile(): File {
        val fileName = System.currentTimeMillis().toString() + IMAGE_FILE_EXTENSION
        return File(cacheFileDir, fileName)
    }

    override fun clearCachedFiles() {
        try {
            cacheFileDir.listFiles()?.forEach { it.delete() }
        } catch (exc: Exception) {
            exc.printStackTrace()
        }
    }

    private val cacheFileDir = File(context.cacheDir, STORAGE_DIR).apply { if (!exists()) mkdirs() }

    private companion object {
        const val STORAGE_DIR = "WasteSortingFiles"
        const val IMAGE_FILE_EXTENSION = ".jpeg"
    }
}
