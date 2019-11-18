package com.example.pokedex.data.fields

import android.os.Build

object Infra{
    const val DB = "pokemon.db3"
    const val VERSION = 3
}

object Pokemon {
    const val NAME = "name"
    const val ID = "id"
    const val IMAGEM = "imagem"
    const val TIPO = "tipo"
    const val FAVORITO = "favorito"
    const val PRIORITY = "priority"
    const val TABLE = "pokemon"
}
object Evolution_Chain {
    const val POKEMONID = "pokemonid"
    const val EVOLVETO = "evolveto"
    const val TABLE = "evolution_chain"
}