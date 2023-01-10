package tech.jour.galleryclean.util

import tech.jour.galleryclean.entry.Group
import tech.jour.galleryclean.entry.Photo
import java.util.concurrent.ConcurrentLinkedQueue

class SameSizePhoto(private val photoList: List<Photo>) {
    fun find(): List<Group> {

        val groups = mutableListOf<Group>()

        val photos = photoList.toMutableList()

        val list = ConcurrentLinkedQueue(photoList)
        list.forEachIndexed { i, photo ->
            val temp = mutableListOf<Photo>()
            temp.add(photo)
            var j = i + 1
            while (j < photos.size) {
                val photo2 = photos[j]
                if (photo.size == photo2.size) {
                    temp.add(photo2)
                    photos.remove(photo2)
                    j--
                }
                j++
            }
            groups.add(Group(temp))
        }

        return groups
    }
}