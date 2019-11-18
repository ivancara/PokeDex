package com.example.pokedex.api

import android.content.Context
import com.example.pokedex.config.Configuration
import com.example.pokedex.data.HmAux
import com.example.pokedex.api.model.Pokemon
import com.example.pokedex.api.model.results.PokemonOut
import com.example.pokedex.data.dao.PokemonDao
import com.example.pokedex.data.fields.Evolution_Chain
import com.example.pokedex.data.model.Evolve_To
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonRepository {
    private lateinit var context: Context

    constructor(context: Context) {
        this.context = context
    }

    private fun CreateApi(): PokemonApi {
        var client = OkHttpClient.Builder()
            .build()
        var api = Retrofit.Builder()
            .baseUrl(Configuration.ENDPOINT)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().create()
                )
            ).client(client)
            .build()
        return api.create(PokemonApi::class.java)
    }

    fun RunTask() {
        var api = CreateApi()
        var gson = Gson()
        api.GetPokemon(0, 151).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    var resposta = response.body()?.string()
                    var pokemonOut = gson.fromJson(resposta, PokemonOut::class.java)

                    for (i in 0 until pokemonOut.results.size) {
                        var pokemon = pokemonOut.results[i]
                        api.GetPokemonByName(pokemon.name).enqueue(object : Callback<ResponseBody> {
                            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                            }

                            override fun onResponse(
                                call: Call<ResponseBody>,
                                response: Response<ResponseBody>
                            ) {
                                if (response.isSuccessful) {
                                    var resposta = response.body()?.string()

                                    var resultado = gson.fromJson(resposta, Pokemon::class.java)

                                    val dao = PokemonDao(context)
                                    var type = ""

                                    for (i in 0 until resultado.types.size){
                                        type += " ${resultado.types[i].type.name.capitalize()} /"
                                    }

                                    var pokemonData = com.example.pokedex.data.model.Pokemon(
                                        name = resultado.name.capitalize(),
                                        id = resultado.id,
                                        imagem = resultado.sprites?.frontDefault!!,
                                        evolveTo = ArrayList<Evolve_To>(),
                                        favorito = false,
                                        priority = 0,
                                        tipo = type.removeSuffix("/")
                                    )

                                    if (dao.existById(resultado.id)) {
                                        dao.alterarPokemon(pokemonData)
                                    } else {
                                        dao.inserirPokemon(pokemonData)
                                    }
                                }
                            }
                        })
                    }
                }
            }
        })
    }
}