package com.test.movie.service

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.test.movie.screen.MovieActivity
import timber.log.Timber

class MyBackgroundService : Service() {
    companion object {
        internal val TAG = MyBackgroundService::class.java.simpleName
    }

    private val handler = Handler(Looper.getMainLooper())
    private val delayMillis: Long = 60 * 1000

    private val periodicTask = object : Runnable {
        override fun run() {
            val intent = Intent(MovieActivity.UPDATE_DATA)
            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
            handler.postDelayed(this, delayMillis)
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Timber.tag(TAG).d( "Service dijalankan...")
        handler.post(periodicTask)
        return START_STICKY
    }

    override fun onDestroy() {
        handler.removeCallbacks(periodicTask)
        super.onDestroy()
    }
}