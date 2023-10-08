package com.test.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun getMovies(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movieList: List<MovieEntity>)

    @Query("SELECT EXISTS(SELECT 1 FROM movies LIMIT 1)")
    fun doesMovieExist(): Boolean

    @Query("DELETE FROM movies")
    suspend fun deleteAllData(): Int

}