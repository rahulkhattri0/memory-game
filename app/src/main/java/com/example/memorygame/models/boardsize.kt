package com.example.memorygame.models

enum class boardsize( var numcards :Int) {
    Easy(8),
    Medium(18),
    Hard(24);
    //the below lilnes of code do not depend on the enum objects
    companion object{
        fun getBoardsize(value:Int):boardsize{
            val boardsize=boardsize.values().first{
                it.numcards==value
            }
            return boardsize
        }
    }

    fun getwidth() : Int{
        return when(this) {
            Easy -> 2
            Medium -> 3
            Hard -> 4
        }
    }
    fun getheight() : Int{
        return numcards / getwidth()
    }
    fun getpairs(): Int{
        return numcards/2
    }

}