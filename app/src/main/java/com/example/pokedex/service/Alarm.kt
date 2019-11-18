package com.example.pokedex.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class Alarm :BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val intent = Intent(p0, CargaService::class.java)
        p0?.startService(intent)
    }
}