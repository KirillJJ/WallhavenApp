package app.kl.testapp.presentation.wallpaperScreen

import app.kl.testapp.di.FragmentScope
import app.kl.testapp.repository.WallpaperRepository
import io.reactivex.disposables.Disposable
import moxy.MvpPresenter
import javax.inject.Inject

@FragmentScope
class WallpaperPresenter @Inject constructor(
    private val wallpaperRepository: WallpaperRepository
) : MvpPresenter<WallpaperView>() {

    private var disposable: Disposable? = null

    fun setWallpaperId(id: String) {
        disposable = wallpaperRepository.getWallpaperById(id)
            .doOnSubscribe { viewState.showLoading(true) }
            .doFinally { viewState.showLoading(false) }
            .subscribe(viewState::showWallpaper)
    }

    override fun onDestroy() {
        disposable?.dispose()
        disposable = null
    }
}
