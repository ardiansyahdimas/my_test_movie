package com.test.core.data.source.local

import com.test.core.data.source.local.entity.MovieEntity
import com.test.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val movieDao: MovieDao,
) {
    fun getMovies():Flow<List<MovieEntity>> = movieDao.getMovies()
    suspend fun insertMovies(movieList: List<MovieEntity>) = movieDao.insertMovies(movieList)
    fun doesMovieExist() = movieDao.doesMovieExist()
    suspend fun deleteAllData() = movieDao.deleteAllData()
}