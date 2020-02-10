package app.kl.testapp.presentation.wallpaperListScreen

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface WallpaperListView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showListState(state: ListState)
}