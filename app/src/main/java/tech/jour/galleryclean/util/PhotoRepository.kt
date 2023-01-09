package tech.jour.galleryclean.util

import android.content.Context
import android.provider.MediaStore
import com.orhanobut.logger.Logger
import tech.jour.galleryclean.entry.Photo

/**
 * Created by gavin on 2017/3/27.
 */
object PhotoRepository {
    private val STORE_IMAGES = arrayOf(
        MediaStore.Images.Media._ID,
        MediaStore.Images.Media.DATA,
        MediaStore.Images.Media.DISPLAY_NAME,
        MediaStore.Images.ImageColumns.MIME_TYPE,
        MediaStore.Images.Media.SIZE
    )

    fun getPhoto(context: Context): List<Photo> {
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val contentResolver = context.contentResolver
        val sortOrder = MediaStore.Images.Media.DATE_TAKEN + " desc"
        val cursor = contentResolver.query(uri, STORE_IMAGES, null, null, sortOrder)
        val result: MutableList<Photo> = ArrayList()
        while (cursor != null && cursor.moveToNext()) {
            val photo = Photo()
            photo.id = cursor.getLong(0)
            photo.path = cursor.getString(1)
            photo.name = cursor.getString(2)
            photo.mimetype = cursor.getString(3)
            photo.size = cursor.getLong(4)
            result.add(photo)
        }
        cursor?.close()
        Logger.d("getPhoto: size=" + result.size)
        return result
    }
}