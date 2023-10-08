package com.test.core.data

import android.content.Context
import com.test.core.NetworkBoundResource
import com.test.core.data.source.local.LocalDataSource
import com.test.core.data.source.remote.RemoteDataSource
import com.test.core.data.source.remote.network.ApiResponse
import com.test.core.data.source.remote.response.ResultsMovieItem
import com.test.core.domain.model.MovieModel
import com.test.core.domain.repository.IMovieRepository
import com.test.core.utils.MovieDataMapper
import com.test.core.utils.Utils.isInternetAvailable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val context: Context
) : IMovieRepository {
    override fun getMovies(page:Int): Flow<Resource<List<MovieModel>>> =
        object : NetworkBoundResource<List<MovieModel>, List<ResultsMovieItem>>() {
            override fun loadFromDB(): Flow<List<MovieModel>>  {
                return localDataSource.getMovies().map { MovieDataMapper.mapEntitiesToDomain(it)}
            }

            override fun shouldFetch(data: List<MovieModel>?): Boolean{
                if (isInternetAvailable(context) && page > 1) {
                    return true
                } else {
                    return data.isNullOrEmpty()
                }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ResultsMovieItem>>> = remoteDataSource.getMovies(page)


            override suspend fun saveCallResult(data: List<ResultsMovieItem>) {
                if (data.isNotEmpty()) {
                    GlobalScope.launch {
                        val isExistData = localDataSource.doesMovieExist()
                        if (isExistData){
                            localDataSource.deleteAllData()
                        }
                        val movieList = MovieDataMapper.mapResponsesToEntities(data)
                        localDataSource.insertMovies(movieList)
                    }
                }
            }
        }.asFlow()

}