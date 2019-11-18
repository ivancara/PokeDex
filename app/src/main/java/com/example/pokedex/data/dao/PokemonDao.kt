package com.example.pokedex.data.dao

import android.content.ContentValues
import android.content.Context
import com.example.pokedex.data.HmAux
import com.example.pokedex.data.model.Pokemon
import java.lang.StringBuilder

class PokemonDao(
    context: Context
) : Dao(context) {

    fun inserirPokemon(pokemon: Pokemon) {
        super.OpenDb()
        val cv = ContentValues()
        cv.put(com.example.pokedex.data.fields.Pokemon.ID, pokemon.id)
        cv.put(com.example.pokedex.data.fields.Pokemon.IMAGEM, pokemon.imagem)
        cv.put(com.example.pokedex.data.fields.Pokemon.TIPO, pokemon.tipo)
        cv.put(com.example.pokedex.data.fields.Pokemon.NAME, pokemon.name)
        cv.put(com.example.pokedex.data.fields.Pokemon.FAVORITO, pokemon.favorito)
        cv.put(com.example.pokedex.data.fields.Pokemon.PRIORITY, pokemon.priority)

        super.db?.insert(com.example.pokedex.data.fields.Pokemon.TABLE, null, cv)

        super.CloseDb()
    }

    fun alterarPokemon(pokemon: Pokemon) {
        super.OpenDb()
        val cv = ContentValues()
        cv.put(com.example.pokedex.data.fields.Pokemon.ID, pokemon.id)
        cv.put(com.example.pokedex.data.fields.Pokemon.IMAGEM, pokemon.imagem)
        cv.put(com.example.pokedex.data.fields.Pokemon.TIPO, pokemon.tipo)
        cv.put(com.example.pokedex.data.fields.Pokemon.NAME, pokemon.name)
        cv.put(com.example.pokedex.data.fields.Pokemon.FAVORITO, pokemon.favorito)
        cv.put(com.example.pokedex.data.fields.Pokemon.PRIORITY, pokemon.priority)
        val conditional = "${com.example.pokedex.data.fields.Pokemon.ID} = ?"
        val args = arrayOf(pokemon.id.toString())
        super.db?.update(com.example.pokedex.data.fields.Pokemon.TABLE, cv, conditional, args)

        super.CloseDb()
    }

    fun favoritar(favorito: Boolean, id: Long) {
        super.OpenDb()
        val cv = ContentValues()
        cv.put(com.example.pokedex.data.fields.Pokemon.FAVORITO, favorito)
        val conditional = "${com.example.pokedex.data.fields.Pokemon.ID} = ?"
        val args = arrayOf(id.toString())
        super.db?.update(com.example.pokedex.data.fields.Pokemon.TABLE, cv, conditional, args)

        super.CloseDb()
    }

    fun darPrioridade(id: Long) {
        super.OpenDb()
        val sb = StringBuilder()

        sb.append("UPDATE ${com.example.pokedex.data.fields.Pokemon.TABLE} ")
        sb.append("SET ${com.example.pokedex.data.fields.Pokemon.PRIORITY} = ${com.example.pokedex.data.fields.Pokemon.PRIORITY} + 1 ")
        sb.append("WHERE ${com.example.pokedex.data.fields.Pokemon.ID} = $id")
        super.db?.rawQuery(sb.toString(), null)

        super.CloseDb()
    }

    fun getPokemon(search: String): ArrayList<HmAux> {
        val sb = StringBuilder()
        var auxList = ArrayList<HmAux>()
        sb.append(
            "SELECT ${com.example.pokedex.data.fields.Pokemon.ID}," +
                    "${com.example.pokedex.data.fields.Pokemon.NAME}," +
                    "${com.example.pokedex.data.fields.Pokemon.TIPO}," +
                    "${com.example.pokedex.data.fields.Pokemon.IMAGEM}," +
                    "${com.example.pokedex.data.fields.Pokemon.FAVORITO}," +
                    "${com.example.pokedex.data.fields.Pokemon.PRIORITY}" +
                    " FROM ${com.example.pokedex.data.fields.Pokemon.TABLE} "
        )
        if (search.isNotEmpty()) {
            sb.append("WHERE ${com.example.pokedex.data.fields.Pokemon.NAME} LIKE '%$search%' ")
            sb.append("OR ${com.example.pokedex.data.fields.Pokemon.TIPO} LIKE '%$search%'")
        }
        sb.append("ORDER BY ${com.example.pokedex.data.fields.Pokemon.PRIORITY} DESC,")
        sb.append("${com.example.pokedex.data.fields.Pokemon.ID} ASC")

        super.OpenDb()

        val cursor = super.db?.rawQuery(sb.toString(), null)
        var aux: HmAux
        while (cursor!!.moveToNext()) {
            aux = HmAux()

            aux[com.example.pokedex.data.fields.Pokemon.ID] = cursor.getString(0)
            aux[com.example.pokedex.data.fields.Pokemon.NAME] = cursor.getString(1)
            aux[com.example.pokedex.data.fields.Pokemon.TIPO] = cursor.getString(2)
            aux[com.example.pokedex.data.fields.Pokemon.IMAGEM] = cursor.getString(3)
            aux[com.example.pokedex.data.fields.Pokemon.PRIORITY] = cursor.getString(4)
            aux[com.example.pokedex.data.fields.Pokemon.FAVORITO] = cursor.getString(5)

            auxList.add(aux)
        }
        super.CloseDb()

        return auxList
    }

    fun getPokemonById(id: Long): HmAux {
        val sb = StringBuilder()
        sb.append(
            "SELECT ${com.example.pokedex.data.fields.Pokemon.ID}," +
                    "${com.example.pokedex.data.fields.Pokemon.NAME}," +
                    "${com.example.pokedex.data.fields.Pokemon.TIPO}," +
                    "${com.example.pokedex.data.fields.Pokemon.IMAGEM}," +
                    "${com.example.pokedex.data.fields.Pokemon.FAVORITO}," +
                    "${com.example.pokedex.data.fields.Pokemon.PRIORITY}" +
                    " FROM ${com.example.pokedex.data.fields.Pokemon.TABLE} "
        )
            sb.append("WHERE ${com.example.pokedex.data.fields.Pokemon.ID} = $id ")

        super.OpenDb()

        val cursor = super.db?.rawQuery(sb.toString(), null)
        var aux: HmAux = HmAux()
        while (cursor!!.moveToNext()) {
            aux = HmAux()

            aux[com.example.pokedex.data.fields.Pokemon.ID] = cursor.getString(0)
            aux[com.example.pokedex.data.fields.Pokemon.NAME] = cursor.getString(1)
            aux[com.example.pokedex.data.fields.Pokemon.TIPO] = cursor.getString(2)
            aux[com.example.pokedex.data.fields.Pokemon.IMAGEM] = cursor.getString(3)
            aux[com.example.pokedex.data.fields.Pokemon.PRIORITY] = cursor.getString(4)
            aux[com.example.pokedex.data.fields.Pokemon.FAVORITO] = cursor.getString(5)
        }
        super.CloseDb()

        return aux
    }


    fun existById(id: Long): Boolean {
        super.OpenDb()
        val sb = StringBuilder()
        sb.append("SELECT 1 FROM ${com.example.pokedex.data.fields.Pokemon.TABLE} ")
        sb.append("WHERE ${com.example.pokedex.data.fields.Pokemon.ID} = $id;")
        val cursor = super.db?.rawQuery(sb.toString().toLowerCase(), null)!!

        var resultado = cursor.count > 0
        super.CloseDb()

        return resultado
    }
}