package com.example.pokedex.ui

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.example.pokedex.R
import com.example.pokedex.data.dao.PokemonDao
import com.example.pokedex.data.fields.Pokemon
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.detalhes.*

class DetalhesActivity : AppCompatActivity() {
    private lateinit var context: Context
    private lateinit var dao: PokemonDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detalhes)

        initVar()
    }

    private fun initVar() {
        context = this
        dao = PokemonDao(context)

        val id = intent.getLongExtra(Pokemon.ID,-1)
        var data = dao.getPokemonById(id)

        tv_nome.text = data[Pokemon.NAME]
        tv_type.text = data[Pokemon.TIPO]
        val img_view = findViewById(R.id.img_detalhes) as ImageView
        Picasso.get().load(data[Pokemon.IMAGEM]).into(img_view)
    }

}