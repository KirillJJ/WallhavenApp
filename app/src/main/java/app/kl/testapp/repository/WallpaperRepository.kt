package app.kl.testapp.repository

import app.kl.testapp.db.WallpaperLocalSource
import app.kl.testapp.db.WallpaperRealmObject
import app.kl.testapp.di.ActivityScope
import app.kl.testapp.usecases.Paginator
import io.reactivex.Single
import io.realm.RealmResults
import javax.inject.Inject

@ActivityScope
class WallpaperRepository @Inject constructor(
    private val wallpaperLocalSource: WallpaperLocalSource,
    private val webDataSrc: WebDataSrc
) {

    fun createPaginator(view: (Paginator.State) -> Unit) =
        Paginator(webDataSrc::loadPage, view)

    fun getWallpapersCache(): RealmResults<WallpaperRealmObject> = wallpaperLocalSource.getAll()

    fun getWallpaperById(id: String): Single<WallpaperRealmObject> = wallpaperLocalSource.getById(id)

}