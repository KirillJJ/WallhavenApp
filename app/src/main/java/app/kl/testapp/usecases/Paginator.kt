package app.kl.testapp.usecases

import io.reactivex.Single
import io.reactivex.disposables.Disposable
import app.kl.testapp.api.responses.WallpaperData
import app.kl.testapp.repository.Page

class Paginator(
    private val nextPageLoader: (Int) -> Single<Page>,
    view: (State) -> Unit
) {

    private val stateMachine = PaginationStateMachine( { load(it.page) }, view)
    private var lastDownLoadDisposable: Disposable? = null

    fun loadNextPage() {
        stateMachine.onEvent(PaginationEvent.LoadNextPage)
    }

    fun refreshList() {
        stateMachine.onEvent(PaginationEvent.LoadFirstPage)
    }

    private fun load(page: Int) {
        lastDownLoadDisposable?.dispose()
        lastDownLoadDisposable = nextPageLoader(page)
            .subscribe(
                { stateMachine.onEvent(PaginationEvent.PageLoaded(it.data, it.pagesCount)) },
                { stateMachine.onEvent(PaginationEvent.ErrorOnLoading(it)) }
            )
    }

    sealed class State(val currentPage: Int, val data: List<WallpaperData>) {
        object EmptyList : State(0, emptyList())
        data class Error(val t: Throwable) : State(0, emptyList())
        object Loading : State(0, emptyList())
        class Data(currentPage: Int, data: List<WallpaperData>) : State(currentPage, data)
        class FullData(currentPage: Int, data: List<WallpaperData>) : State(currentPage, data)
        class DataWithLoading(currentPage: Int, data: List<WallpaperData>) : State(currentPage, data)
        class DataWithError(currentPage: Int, data: List<WallpaperData>, val error: Throwable?) : State(currentPage, data)
    }

    sealed class PaginationEvent {
        object LoadFirstPage : PaginationEvent()
        object LoadNextPage : PaginationEvent()
        class PageLoaded(val data: List<WallpaperData>, val pagesCount: Int) : PaginationEvent()
        class ErrorOnLoading(val t: Throwable) : PaginationEvent()
    }

    data class LoadPage(val page: Int)

    class PaginationStateMachine(
        sideEffectRunner: (LoadPage) -> Unit,
        stateUpdateHandler: (State) -> Unit
    ) : AbstractStateMachine<State, PaginationEvent, LoadPage>(
        sideEffectRunner = sideEffectRunner,
        stateUpdateHandler = stateUpdateHandler,
        initialState = State.EmptyList
    ) {

        override fun onEventInternal(event: PaginationEvent): Pair<State, LoadPage?>? =
            when(event) {
                PaginationEvent.LoadFirstPage -> {
                    if (state == State.Loading) null
                    else State.Loading to LoadPage(1)
                }
                is PaginationEvent.LoadNextPage -> when(state) {
                    State.EmptyList,
                    is State.Error -> State.Loading to LoadPage(1)
                    is State.DataWithLoading,
                    is State.FullData,
                    State.Loading -> null
                    is State.DataWithError,
                    is State.Data -> State.DataWithLoading(state.currentPage, state.data) to LoadPage(state.currentPage + 1)
                }
                is PaginationEvent.PageLoaded -> {
                    if (event.pagesCount == state.currentPage + 1) {
                        State.FullData(
                            event.pagesCount,
                            state.data + event.data
                        ) to null
                    }
                    else {
                        State.Data(
                            state.currentPage + 1,
                            state.data + event.data
                        ) to null
                    }
                }
                is PaginationEvent.ErrorOnLoading -> {
                    if (state == State.Loading){
                        State.Error(event.t) to null
                    } else {
                        State.DataWithError(state.currentPage, state.data, event.t) to null
                    }
                }
            }
    }
}
