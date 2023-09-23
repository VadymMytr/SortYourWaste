package vadym.my.wastesorting.data.camera

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class FilesStorageImpl @Inject constructor(@ApplicationContext private val context: Context) : FilesStorage {

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

    private val cacheFileDir get() = File(context.cacheDir, STORAGE_DIR).apply { if (!exists()) mkdirs() }

    private companion object {
        const val STORAGE_DIR = "WasteSortingFiles"
        const val IMAGE_FILE_EXTENSION = ".jpeg"
    }
}
