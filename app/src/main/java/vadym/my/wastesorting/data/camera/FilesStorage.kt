package vadym.my.wastesorting.data.camera

import java.io.File

interface FilesStorage {

    /**
     * @return New [File] in which an image from camera should be saved in. Has ".jpeg" extension.
     */
    fun createCachedImageFile(): File

    /**
     * Clears all previously saved files to prevent the device from storing garbage.
     */
    fun clearCachedFiles()
}
