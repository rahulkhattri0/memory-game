package com.example.memorygame.models

import com.example.memorygame.utils.default_icons

class memorygame(val boardsize: boardsize) {
    val list_of_images_mapped:List<MemoryCard>
    var indexofSingleselectedcard:Int?=null
    var numpairsfound:Int=0
    var cardflips:Int=0
init {
    val chosen_images= default_icons.shuffled().take(boardsize.getpairs())
    val randomimages= (chosen_images + chosen_images).shuffled()
    list_of_images_mapped= randomimages.map { it ->
        MemoryCard(it,false,false)
    }
    print(list_of_images_mapped)
}
    fun flipcard(pos:Int):Boolean{
        cardflips++
        val card = list_of_images_mapped[pos]
        var matchfound:Boolean=false
        //three conditions arise here either 0 cards are flipped over or exactly 1 card is flipped over or 2 cards are flipped over
        //we just need to worry about the exactly once condition in which we check whether the corressponding pair to that card has been found or not.
        if(indexofSingleselectedcard==null){
            restorecards()
            indexofSingleselectedcard=pos
        }
        else{
            matchfound= checkForMatch(indexofSingleselectedcard!!,pos)
            indexofSingleselectedcard=null
        }
        card.isFaceUp = !card.isFaceUp
        return matchfound
    }

    private fun checkForMatch(position1: Int, position2: Int): Boolean {
        if (list_of_images_mapped[position1].identify == list_of_images_mapped[position2].identify){
            list_of_images_mapped[position1].isMatched=true
            list_of_images_mapped[position2].isMatched=true
            numpairsfound++
            return true
        }
        else
            return false
    }

    private fun restorecards() {
        for(cards in list_of_images_mapped){
            if (cards.isMatched==false)
                cards.isFaceUp=false

        }
    }

    fun hasWon(): Boolean {
        return numpairsfound == boardsize.getpairs()
    }

    fun isFaceUp(position:Int): Boolean {
        return list_of_images_mapped[position].isFaceUp
    }

    fun getmoves(): String {
        return "${cardflips/2}"
    }
}
