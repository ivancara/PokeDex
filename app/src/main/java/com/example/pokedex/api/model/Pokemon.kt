package com.example.pokedex.api.model

class Pokemon(
    var name:String ="",
    var id: Long = -1L,
    var sprites: Sprite? = null,
    var types: ArrayList<Slot> = ArrayList<Slot>()
)