package com.example.pokedex.api


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {
    @GET("pokemon/{id}/")
    fun GetPokemonById(@Path("id") id: Int) : Call<ResponseBody>

    @GET("pokemon/{name}/")
    fun GetPokemonByName(@Path("name") name: String): Call<ResponseBody>

    @GET("pokemon")
    fun GetPokemon(@Query("offset") offset:Int, @Query("limit") limit:Int): Call<ResponseBody>
}