package app.kl.testapp.db

import app.kl.testapp.di.ActivityScope
import io.reactivex.Single
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import javax.inject.Inject

@ActivityScope
class WallpaperLocalSource @Inject constructor(
    private val realm: Realm
) {

    fun getById(id: String): Single<WallpaperRealmObject> =
        realm.where<WallpaperRealmObject>()
            .equalTo("id", id)
            .findFirstAsync()
            .asFlowable<WallpaperRealmObject>()
            .filter { it.isLoaded && it.isValid }
            .firstOrError()

    fun getAll(): RealmResults<WallpaperRealmObject> =
        realm.where<WallpaperRealmObject>()
            .findAllAsync()
}