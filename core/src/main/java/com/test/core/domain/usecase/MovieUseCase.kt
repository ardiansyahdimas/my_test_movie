package com.test.core.domain.usecase

import com.test.core.data.Resource
import com.test.core.domain.model.MovieModel
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getMovies(page:Int): Flow<Resource<List<MovieModel>>>
}