package com.example.pokedex.data.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.pokedex.data.fields.Infra
import com.example.pokedex.database.Database

open class Dao(
    var context: Context
) {
    protected var db: SQLiteDatabase? = null

    fun OpenDb(){
        val helper = Database(
            context,
            Infra.DB,
            null,
            Infra.VERSION
        )

        db = helper.writableDatabase
    }

    fun CloseDb(){
        if(db != null){
            db?.close()
        }
    }
}