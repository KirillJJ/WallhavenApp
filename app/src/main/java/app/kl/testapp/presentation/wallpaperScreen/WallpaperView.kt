package app.kl.testapp.presentation.wallpaperScreen

import app.kl.testapp.db.WallpaperRealmObject
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface WallpaperView : MvpView {
    fun showWallpaper(wallpaper: WallpaperRealmObject)
    fun showLoading(show: Boolean)
}