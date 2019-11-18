package com.example.pokedex.data.model

class Pokemon(
    var id: Long = -1L,
    var name: String = "",
    var tipo:String = "",
    var imagem: String = "",
    var favorito: Boolean = false,
    var priority: Int = 1,
    var evolveTo: ArrayList<Evolve_To>
)