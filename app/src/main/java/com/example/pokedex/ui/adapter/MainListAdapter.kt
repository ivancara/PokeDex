package com.example.pokedex.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.pokedex.R
import com.example.pokedex.data.HmAux
import com.example.pokedex.data.fields.Pokemon
import com.squareup.picasso.Picasso

class MainListAdapter(
    var context: Context,
    var data: ArrayList<HmAux>,
    var resource: Int
): BaseAdapter() {
    private val inflater = LayoutInflater.from(context)
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var mView = p1
        if(mView == null){
            mView = inflater.inflate(resource,p2, false)
        }

        val nome = mView?.findViewById(R.id.tv_Nome) as TextView
        nome.text = data[p0][Pokemon.NAME]
        val id = mView?.findViewById(R.id.tv_ID) as TextView
        id.text = "#${data[p0][Pokemon.ID]}"
        val tipo = mView?.findViewById(R.id.tv_Tipo) as TextView
        tipo.text = data[p0][Pokemon.TIPO]
        val img = mView?.findViewById(R.id.iv_Badge) as ImageView
        Picasso.get().load(data[p0][Pokemon.IMAGEM]).into(img)
        return mView
    }

    override fun getItem(p0: Int): Any {
        return data[p0]
    }

    override fun getItemId(p0: Int): Long {
        return data[p0][Pokemon.ID]?.toLong()!!
    }

    override fun getCount(): Int {
        return data.size
    }
}