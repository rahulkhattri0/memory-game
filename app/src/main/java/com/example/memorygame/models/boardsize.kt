package com.example.memorygame.models

enum class boardsize( var numcards :Int) {
    Easy(8),
    Medium(18),
    Hard(24);


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