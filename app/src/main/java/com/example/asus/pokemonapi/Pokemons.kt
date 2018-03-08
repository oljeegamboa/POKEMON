package com.example.asus.pokemonapi

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.net.URL
import java.util.ArrayList

class Pokemons (pokeDex: ArrayList<Pokemon>, context: Context):RecyclerView.Adapter<Pokemons.pokemonNames> (){

    val context = context
    override fun onBindViewHolder(holder: pokemonNames?, position: Int) {
        val pmon = dataPok[position]


        val title = pmon.name

        val url = pmon.imagepath

        val ins = URL(url).openStream()


        var mIcon = BitmapFactory.decodeStream(ins)
        holder!!.titlev.setImageBitmap(mIcon)



        holder.descript.text =  title.capitalize()


        holder.setReclicker(object : PokemonSelect{

            override fun onPokemonSelect(view: View, pos: Int) {

                Toast.makeText(context,title + " Clicked ", Toast.LENGTH_SHORT).show()
            }
        })



    }


    var dataPok: ArrayList<Pokemon> = pokeDex
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int):pokemonNames {
        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.listview, parent, false)


        return pokemonNames(view)    }


    override fun getItemCount(): Int {
        return dataPok.size
    }

    class pokemonNames(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        override fun onClick(p0: View?) {
            this.reClicker!!.onPokemonSelect(p0!!, adapterPosition)
        }



        val titlev: ImageView
        val descript: TextView

        var reClicker:PokemonSelect? = null


        init {


            titlev = itemView.findViewById<ImageView>(R.id.image)
            descript = itemView.findViewById<TextView>(R.id.description)

            itemView.setOnClickListener(this)
        }

        fun setReclicker(pokClicker: PokemonSelect) {
            this.reClicker = pokClicker
        }
    }
}