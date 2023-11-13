package com.example.startingservices

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainService : Service() {
    var startId = 0
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val startValue = intent?.getStringExtra("KEY")?.toInt()
        runTimer(startId, startValue)
        return super.onStartCommand(intent, flags, startId)
    }

    private fun runTimer(startId: Int, startValue: Int?) {
        TimerThread(startId, startValue).start()
    }

    override fun onDestroy() {
        Log.d("Service State", "STOPPED")
        super.onDestroy()
    }

    inner class TimerThread(private val startId: Int, private val startValue: Int?) : Thread() {
        override fun run() {

            CoroutineScope(Dispatchers.Default).launch {
                countdown(startValue)
            }
        }

        private suspend fun countdown(startValue: Int?) {
            if (startValue != null) {
                for(i in startValue downTo 0) {
                    Log.d("Countdown", i.toString())
                    delay(1000)
                }
            }
        }
    }
}