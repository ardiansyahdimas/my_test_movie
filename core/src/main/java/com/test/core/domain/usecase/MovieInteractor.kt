package com.test.core.domain.usecase

import com.test.core.data.Resource
import com.test.core.domain.model.MovieModel
import com.test.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(private val movieRepository: IMovieRepository):
    MovieUseCase {
    override fun getMovies(page:Int): Flow<Resource<List<MovieModel>>>  = movieRepository.getMovies(page)
}