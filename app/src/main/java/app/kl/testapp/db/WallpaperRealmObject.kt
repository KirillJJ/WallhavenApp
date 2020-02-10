package app.kl.testapp.db

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class WallpaperRealmObject(
    @PrimaryKey
    var id: String? = null,
    var uri: String? = null,
    var smallThumb: String? = null,
    var originalThumb: String? = null,
    var largeThumb: String? = null
) : RealmObject()