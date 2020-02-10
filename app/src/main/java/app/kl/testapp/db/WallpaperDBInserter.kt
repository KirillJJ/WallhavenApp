package app.kl.testapp.db

import io.realm.Realm
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WallpaperDBInserter @Inject constructor() {

    fun insertOrUpdate(list: List<WallpaperRealmObject>) {
        Realm.getDefaultInstance().use {
            it.executeTransactionAsync {
                it.insertOrUpdate(list)
            }
        }
    }
}