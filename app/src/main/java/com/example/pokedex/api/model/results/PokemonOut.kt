package com.example.pokedex.api.model.results

import com.google.gson.annotations.SerializedName

class PokemonOut(
    var count: Int = 0,
    @SerializedName("next")
    var _next: String = "",
    var previous: String = "",
    var results: ArrayList<Result>
)