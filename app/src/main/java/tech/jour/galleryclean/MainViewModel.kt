package tech.jour.galleryclean

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tech.jour.galleryclean.entry.Group
import tech.jour.galleryclean.entry.Photo

class MainViewModel : ViewModel() {

    val photos = MutableLiveData<List<Photo>>(emptyList())
    val groups = MutableLiveData<List<Group>>(emptyList())

    fun setPhotos(list: List<Photo>) {
        photos.postValue(list)
    }

    fun setGroups(list: List<Group>) {
        groups.postValue(list)
    }

}
