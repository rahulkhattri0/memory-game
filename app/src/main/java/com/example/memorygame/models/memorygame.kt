package com.example.memorygame.models

import com.example.memorygame.utils.default_icons

class memorygame(boardsize: boardsize) {
    val list_of_images_mapped:List<MemoryCard>
    var numberofpairsfound : Int =0

init {
    val chosen_images= default_icons.shuffled().take(boardsize.getpairs())
    val randomimages= (chosen_images + chosen_images).shuffled()
    list_of_images_mapped= randomimages.map { it ->
        MemoryCard(it,false,false)
    }
    print(list_of_images_mapped)
}
    fun flipcard(pos:Int){
        val card = list_of_images_mapped[pos]
        card.isFaceUp = !card.isFaceUp
    }
}
