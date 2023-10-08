package com.test.movie.screen

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.lottie.LottieCompositionFactory
import com.test.core.R
import com.test.core.data.Resource
import com.test.core.ui.MovieAdapter
import com.test.core.utils.Utils.isInternetAvailable
import com.test.core.utils.Utils.showNotif
import com.test.movie.databinding.ActivityMovieBinding
import com.test.movie.service.MyBackgroundService
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MovieActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMovieBinding
    private val viewModel: MovieModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var serviceIntent: Intent
    private var updateDataReceiver: BroadcastReceiver? = null
    private var currentPage = 1

    companion object {
        const val UPDATE_DATA = "update_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        movieAdapter = MovieAdapter()
        getMovies(currentPage)

        serviceIntent = Intent(this, MyBackgroundService::class.java)
        startService(serviceIntent)
        registerUpdateDataReceiver()

    }

    private fun getMovies(page:Int) {
        viewModel.getMovie(page)
        val isEmpty = movieAdapter.itemCount < 1
        viewModel.resultValue?.observe(this){result ->
            when (result) {
                is Resource.Loading -> {
                    updateUI(true, false)
                }
                is Resource.Success -> {
                    updateUI(false, result.data?.isEmpty() == true)
                    movieAdapter.setData(result.data)
                }
                is Resource.Error -> {
                    updateUI(false, isEmpty)
                }
                else -> {
                    updateUI(false, isEmpty)
                }
            }
        }
        with(binding.rvMovie) {
            layoutManager = GridLayoutManager(this@MovieActivity, 2)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    private fun updateUI (state:Boolean, isEmpty:Boolean){
        binding.rvMovie.isVisible = !state
        binding.progressBar.isVisible = state
        val lottieFileName = if (isEmpty && currentPage < 2) "empty.json" else "loading.json"
        if (isEmpty && currentPage < 2) {
            binding.progressBar.isVisible = true
            binding.infoEmpty.isVisible = true
            if (!state) {
                showNotif(this@MovieActivity, getString(R.string.app_name), getString(R.string.empty_data))
            }
        } else {
            binding.infoEmpty.isVisible = false
        }

        LottieCompositionFactory.fromAsset(this, lottieFileName)
            .addListener { composition ->
                binding.progressBar.setComposition(composition)
                binding.progressBar.playAnimation()
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(serviceIntent)
        unregisterDownloadReceiver()
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    private fun registerUpdateDataReceiver(){
        updateDataReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (isInternetAvailable(this@MovieActivity)) {
                    currentPage++
                    showNotif(this@MovieActivity, getString(R.string.app_name), getString(R.string.old_data_has_been_deleted_and_new_data_is_available))
                    getMovies(currentPage)
                }
            }
        }
        val intentFilter = IntentFilter(UPDATE_DATA)
        LocalBroadcastManager.getInstance(this).registerReceiver(updateDataReceiver as BroadcastReceiver, intentFilter)
    }


    private fun unregisterDownloadReceiver() {
        if (updateDataReceiver != null) {
            try {
                unregisterReceiver(updateDataReceiver)
            } catch (e: Throwable) {
                Timber.e(e.message)
            }
            updateDataReceiver = null
        }
    }
}