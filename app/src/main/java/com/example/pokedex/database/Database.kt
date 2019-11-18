package com.example.pokedex.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception
import java.lang.StringBuilder

class Database(
    var context: Context,
    var name: String?,
    var factory: SQLiteDatabase.CursorFactory?,
    var version: Int
) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(p0: SQLiteDatabase?) {
        try {
            val sb = StringBuilder()
            sb.append(
                "CREATE TABLE IF NOT EXISTS Pokemon\n" +
                        "(\n" +
                        "    ID INT,\n" +
                        "    NAME VARCHAR,\n" +
                        "    TIPO VARCHAR,\n" +
                        "    DESCRICAO VARCHAR,\n" +
                        "    IMAGEM VARCHAR,\n" +
                        "    FAVORITO BIT,\n" +
                        "    PRIORITY NUMBER\n" +
                        ");\n" +
                        "\n" +
                        "CREATE TABLE IF NOT EXISTS Evolution_Chain(\n" +
                        "    POKEMONID INT REFERENCES Pokemon(ID),\n" +
                        "    EVOLVETO INT REFERENCES Pokemon(ID)\n" +
                        ");"
            )
            val comandos = sb.toString().split(";")
            for (i in 0 until comandos.size) {
                p0?.execSQL(comandos[i].toLowerCase())
            }
        } catch (e: Exception) {
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        try{
            val sb = StringBuilder()

            sb.append("DROP TABLE IF EXISTS Pokemon;")
            sb.append("DROP TABLE IF EXISTS Evolution_Chain;")

            val comandos = sb.toString().split(";")
            for (i in 0 until comandos.size) {
                p0?.execSQL(comandos[i].toLowerCase())
            }
        }catch (e:Exception){

        }
        onCreate(p0)
    }
}