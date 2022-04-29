package com.example.memorygame

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.memorygame.models.boardsize
import kotlin.math.*

class MemoryAdapter(val context : Context, val size : boardsize,val images:List<Int>) : RecyclerView.Adapter<MemoryAdapter.ViewHolder>() {
    //similar to static in java

    companion object{
        const val MARGIN_SIZE = 10
    }
    override fun getItemCount(): Int {
        return size.numcards
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cardwidth = parent.width /size.getwidth() - (2* MARGIN_SIZE)
        val cardheight = parent.height/size.getheight() - (2* MARGIN_SIZE)
        val cardsidelength = min(cardwidth,cardheight)
        val inflatedview = LayoutInflater.from(context).inflate(R.layout.card,parent,false)
        val layoutparameters = inflatedview.findViewById<CardView>(R.id.card_view_memory).layoutParams as ViewGroup.MarginLayoutParams
        layoutparameters.height = cardsidelength
        layoutparameters.width = cardsidelength
        layoutparameters.setMargins(
            MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE)
        return ViewHolder(inflatedview)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview) {
        val imagebutton = itemview.findViewById<ImageButton>(R.id.image_button)

        fun bind(position: Int) {
            imagebutton.setImageResource(images[position])
         imagebutton.setOnClickListener {
             Log.i("something in the way","clicked at postion $position")
         }
        }

    }
}