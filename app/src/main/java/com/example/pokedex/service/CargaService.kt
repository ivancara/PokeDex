package com.example.pokedex.service

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import com.example.pokedex.R
import com.example.pokedex.api.PokemonRepository
import com.example.pokedex.ui.MainActivity

class CargaService : Service() {
    private lateinit var nm: NotificationManager
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
    private fun notificate(context: Context){

        nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val mIntent = Intent(context, MainActivity::class.java)

        val pi = PendingIntent.getActivity(
            context,
            0,
            mIntent,
            0
        )

        val notificacao = NotificationCompat.Builder(context)
        notificacao.setSmallIcon(R.mipmap.ic_launcher)
        notificacao.setContentTitle("Sincronismo")
        notificacao.setContentText("Sincronismo sendo realizado...")
        notificacao.setContentIntent(pi)
        notificacao.setAutoCancel(true)
        notificacao.setDefaults(
            Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE
        )

        nm.notify(1, notificacao.build())
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (!RUNNING) {
            RUNNING = true
            notificate(this)
            thread = Thread(
                Runnable {
                    val repo = PokemonRepository(this)
                    repo.RunTask()
                    stopSelf()
                    nm.cancel(1)
                }
            )

            thread?.start()
        }
        return START_STICKY
    }
}