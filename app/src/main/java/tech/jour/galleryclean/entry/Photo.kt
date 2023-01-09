package tech.jour.galleryclean.entry

data class Photo(
    var id: Long = 0,
    var path: String? = null,
    var name: String? = null,
    var mimetype: String? = null,
    var size: Long = 0,
    var finger: Long = 0,
    var fingerPrint: String? = null
)