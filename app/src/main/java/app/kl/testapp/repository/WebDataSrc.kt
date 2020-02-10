package app.kl.testapp.repository

import app.kl.testapp.api.WallhavenApi
import app.kl.testapp.api.responses.SearchResponse
import app.kl.testapp.api.responses.WallpaperData
import app.kl.testapp.db.WallpaperDBInserter
import app.kl.testapp.db.WallpaperRealmObject
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebDataSrc @Inject constructor(
    private val api: WallhavenApi,
    private val wallpaperDBInserter: WallpaperDBInserter
) {
    fun loadPage(page: Int): Single<Page> =
        api.search(page)
            .map(this::mapToNewPage)
            .doOnSuccess { wallpaperDBInserter.insertOrUpdate(it.data.map(this::map)) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    private fun mapToNewPage(response: SearchResponse): Page =
        Page(
            currentPage = response.meta.currentPage,
            pagesCount = response.meta.lastPage,
            data = response.wallpapers
        )

    private fun map(data: WallpaperData): WallpaperRealmObject =
        WallpaperRealmObject(
            id = data.id,
            uri = data.url,
            largeThumb = data.thumbnails.large,
            originalThumb = data.thumbnails.original,
            smallThumb = data.thumbnails.small
        )
}

data class Page(val currentPage: Int, val pagesCount: Int, val  data: List<WallpaperData>)