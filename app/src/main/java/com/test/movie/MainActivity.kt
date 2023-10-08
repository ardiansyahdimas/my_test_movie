package com.test.movie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.test.core.R.string
import com.test.core.utils.Utils.isInternetAvailable
import com.test.core.utils.Utils.showNotif
import com.test.movie.databinding.ActivityMainBinding
import com.test.movie.screen.MovieActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private val handler = Handler(Looper.getMainLooper())
    private val delayMillis: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (!isInternetAvailable(this@MainActivity)){
            showNotif(this@MainActivity, getString(string.app_name), getString(string.no_internet_connected))
        }
        startHandlerLoop()
    }

    private fun startHandlerLoop() {
        handler.postDelayed({
            startActivity(Intent(this@MainActivity, MovieActivity::class.java))
            finish()
        }, delayMillis)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}