package com.example.pokedex.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.pokedex.api.PokemonRepository

class CargaService : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    companion object {
        var RUNNING = false
    }

    private var thread: Thread? = null

    override fun onDestroy() {
        super.onDestroy()

        thread?.isInterrupted
        RUNNING = false
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!RUNNING) {
            RUNNING = true
            thread = Thread(
                Runnable {
                    val repo = PokemonRepository(this)
                    repo.RunTask()
                    stopSelf()
                }
            )

            thread?.start()
        }
        return START_STICKY
    }
}