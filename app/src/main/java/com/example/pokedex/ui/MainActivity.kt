package com.example.pokedex.ui

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.text.Editable
import android.text.TextWatcher
import com.example.pokedex.ui.adapter.MainListAdapter
import com.example.pokedex.R
import com.example.pokedex.api.PokemonRepository
import com.example.pokedex.data.dao.PokemonDao
import com.example.pokedex.data.fields.Pokemon
import com.example.pokedex.service.Alarm
import com.example.pokedex.service.CargaService
import kotlinx.android.synthetic.main.celula.*
import kotlinx.android.synthetic.main.start.*

class MainActivity : AppCompatActivity() {

    private lateinit var context: Context
    private lateinit var pokemonDao: PokemonDao
    private lateinit var am: AlarmManager
    private lateinit var nm: NotificationManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start)
        initVars()
        initActions()
        startService()
    }
    private fun startService() {
        val mIntent = Intent(context, Alarm::class.java)

        val pi = PendingIntent.getBroadcast(
            context,
            0,
            mIntent,
            0
        )
        am.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() * ((60 * 1000))*60,
            ((60 * 1000))*60,
            pi
        )
    }

    private fun initVars() {
        context = this
        pokemonDao = PokemonDao(context)
        lv_pokemon.adapter = MainListAdapter(
            context,
            pokemonDao.getPokemon(""),
            R.layout.celula
        )
        am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    private fun initActions() {
        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                lv_pokemon.adapter = MainListAdapter(
                    context,
                    pokemonDao.getPokemon(et_search.text.toString()),
                    R.layout.celula
                )
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        }
        )

        lv_pokemon.setOnItemClickListener { adapterView, view, i, l ->
            pokemonDao.darPrioridade(l)
            val intent = Intent(context, DetalhesActivity::class.java)
            intent.putExtra(Pokemon.ID, l)
            startActivity(intent)
        }
    }
}
