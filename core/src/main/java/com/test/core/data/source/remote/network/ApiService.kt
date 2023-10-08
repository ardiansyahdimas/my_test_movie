package com.test.core.data.source.remote.network


import com.test.core.data.source.remote.response.GetMoviesResponse
import retrofit2.http.*


interface ApiService {
    @Headers("Content-Type: application/json")
    @GET("discover/movie")
    suspend fun getMovies(
        @Query("api_key") apiKey:String,
        @Query("page") page: Int,
    ): GetMoviesResponse

}
