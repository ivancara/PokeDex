package com.example.pokedex.ui

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.pokedex.ui.adapter.MainListAdapter
import com.example.pokedex.R
import com.example.pokedex.api.PokemonRepository
import com.example.pokedex.data.dao.PokemonDao
import com.example.pokedex.service.CargaService
import kotlinx.android.synthetic.main.start.*

class MainActivity : AppCompatActivity() {

    private lateinit var context: Context
    private lateinit var pokemonDao: PokemonDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start)
        initVars()
        initActions()

        val repo = PokemonRepository(context)
        repo.RunTask()
    }

    private fun startService() {
        val intent = Intent(context, CargaService::class.java)
        startService(intent)
    }

    private fun initVars() {
        context = this
        pokemonDao = PokemonDao(context)
        lv_pokemon.adapter = MainListAdapter(
            context,
            pokemonDao.getPokemon(""),
            R.layout.celula
        )
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
    }
}
