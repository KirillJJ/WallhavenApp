package app.kl.testapp.usecases

import app.kl.testapp.repository.Page
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import org.junit.Test
import java.io.IOException
import java.util.concurrent.TimeUnit

class PaginatorTest {

    @Test
    fun `after loadNextPage page loading is invoking`() {
        val pageLoaderMock = mock<Function1<Int, Single<Page>>> {
            on { invoke(any()) } doReturn Single.never()
        }
        val stateHandlerMock = mock<Function1<Paginator.State, Unit>> {}


        val paginator = Paginator(pageLoaderMock, stateHandlerMock)

        paginator.loadNextPage()

        verify(pageLoaderMock).invoke(1)
    }

    @Test
    fun `first returned state is Loading`() {
        val pageLoaderMock = mock<Function1<Int, Single<Page>>> {
            on { invoke(any()) } doReturn Single.never()
        }
        val stateHandlerMock = mock<Function1<Paginator.State, Unit>> {}

        val paginator = Paginator(pageLoaderMock, stateHandlerMock)

        paginator.loadNextPage()

        verify(stateHandlerMock).invoke(Paginator.State.Loading)
    }

    @Test
    fun `if pageLoader fails next state is Error`() {
        val io = IOException()
        val pageLoaderMock = mock<Function1<Int, Single<Page>>> {
            on { invoke(any()) } doReturn (Single.just(1).delay(100, TimeUnit.MILLISECONDS).map { throw io } as Single<Page>)
        }
        val stateHandlerMock = mock<Function1<Paginator.State, Unit>> {}

        val paginator = Paginator(pageLoaderMock, stateHandlerMock)

        paginator.loadNextPage()

        verify(stateHandlerMock).apply {
            invoke(Paginator.State.Loading)
        }
    }
}