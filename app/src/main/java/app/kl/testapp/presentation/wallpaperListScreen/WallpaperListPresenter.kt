package app.kl.testapp.presentation.wallpaperListScreen

import moxy.MvpPresenter
import app.kl.testapp.api.responses.WallpaperData
import app.kl.testapp.db.WallpaperRealmObject
import app.kl.testapp.di.FragmentScope
import app.kl.testapp.repository.WallpaperRepository
import app.kl.testapp.usecases.Paginator
import io.realm.RealmResults
import java.io.IOException
import javax.inject.Inject

@FragmentScope
class WallpaperListPresenter @Inject constructor(
    private val repository: WallpaperRepository
) : MvpPresenter<WallpaperListView>() {

    private val paginator by lazy { repository.createPaginator(this::showPaginationState) }

    override fun onFirstViewAttach() {
        paginator.loadNextPage()
    }

    fun onRefresh() {
        paginator.refreshList()
    }

    fun loadNextPage() {
        paginator.loadNextPage()
    }

    private fun showPaginationState(state: Paginator.State) {
        viewState.showListState(
            when(state) {
                Paginator.State.EmptyList -> ListState.Empty
                is Paginator.State.Error -> {
                    if (state.t is IOException) ListState.DataFromCache(repository.getWallpapersCache())
                    else ListState.Error(state.t.toString())
                }
                Paginator.State.Loading -> ListState.Loading
                is Paginator.State.FullData,
                is Paginator.State.Data -> ListState.Data(state.data, false)
                is Paginator.State.DataWithLoading -> ListState.Data(state.data, true)
                is Paginator.State.DataWithError -> ListState.Data(state.data, false, "Ошибка при загрузке")
            }
        )
    }
}

sealed class ListState {
    object Empty : ListState()
    data class Data(val list: List<WallpaperData>, val withLoading: Boolean, val error: String? = null) : ListState()
    object Loading : ListState()
    data class Error(val error: String) : ListState()
    data class DataFromCache(val data: RealmResults<WallpaperRealmObject>) : ListState()
}
