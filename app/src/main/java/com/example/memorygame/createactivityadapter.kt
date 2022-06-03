package com.example.memorygame

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.memorygame.models.boardsize
import com.google.android.material.snackbar.Snackbar
import kotlin.math.min
interface CustomCardClicked{
    fun onCustomCardClicked()
}
class createactivityadapter(
    val context: Context,val list_of_uri:List<Uri>,val boardsize: boardsize,val customCardClicked: CustomCardClicked
) : RecyclerView.Adapter<createactivityadapter.viewHolder>(){

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        if(position < list_of_uri.size){
            holder.bind(list_of_uri[position])
        }
        else{
            holder.bind()
        }
    }

    override fun getItemCount(): Int {
        return boardsize.getpairs()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val cardHeight= parent.height / boardsize.getheight()
        val cardwidth= parent.width / boardsize.getwidth()
        val cardsize = min(cardHeight,cardwidth)
        val inflatedview = LayoutInflater.from(context).inflate(R.layout.card_image,parent,false)
        val layoutparameters = inflatedview.findViewById<ImageView>(R.id.custom_image_from_gallery).layoutParams
        layoutparameters.height = cardsize
        layoutparameters.width = cardsize
        return viewHolder(inflatedview)
    }
    inner class viewHolder(itemview:View):RecyclerView.ViewHolder(itemview){
        val custom_image = itemview.findViewById<ImageView>(R.id.custom_image_from_gallery)
        fun bind(){
            custom_image.setOnClickListener {
//                Toast.makeText(this,"no image selected",Toast.LENGTH_LONG).show()
                customCardClicked.onCustomCardClicked()
            }
        }
        fun bind(uri:Uri){
            custom_image.setImageURI(uri)
            custom_image.setOnClickListener(null)
        }
    }
}