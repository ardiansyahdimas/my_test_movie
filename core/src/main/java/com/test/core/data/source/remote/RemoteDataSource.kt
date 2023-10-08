package com.test.core.data.source.remote

import com.test.core.data.source.remote.network.ApiResponse
import com.test.core.data.source.remote.network.ApiService
import com.test.core.data.source.remote.response.ResultsMovieItem
import com.test.core.utils.Config
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    val TAG = "RemoteDataSource"
    suspend fun getMovies(page:Int): Flow<ApiResponse<List<ResultsMovieItem>>> {
        return flow {
            try {
                val response = apiService.getMovies(Config.API_KEY, page)
                val dataArray = response.results
                if(dataArray?.isNotEmpty() == true){
                    emit(ApiResponse.Success(response.results))
                }else {
                    emit(ApiResponse.Empty)
                }
            }catch (e:Exception){
                emit(ApiResponse.Error(e.toString()))
                Timber.tag(TAG).d("getMovies: ${e.message}")
            }
        }.flowOn(Dispatchers.IO)
    }
}
