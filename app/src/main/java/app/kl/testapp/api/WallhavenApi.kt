package app.kl.testapp.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import app.kl.testapp.api.responses.SearchResponse

interface WallhavenApi {
    @GET("search")
    fun search(@Query("page") page: Int): Single<SearchResponse>
}