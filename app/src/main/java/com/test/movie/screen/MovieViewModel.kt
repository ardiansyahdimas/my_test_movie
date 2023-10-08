package com.test.movie.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.test.core.data.Resource
import com.test.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.test.core.domain.model.MovieModel

@HiltViewModel
class MovieModel @Inject constructor (private val movieUseCase: MovieUseCase) : ViewModel() {
    var resultValue: LiveData<Resource<List<MovieModel>>>? =null
    fun getMovie(page:Int) {
        resultValue = movieUseCase.getMovies(page).asLiveData()
    }
}