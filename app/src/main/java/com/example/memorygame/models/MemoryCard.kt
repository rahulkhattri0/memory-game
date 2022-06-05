package com.example.memorygame.models

data class MemoryCard(
    val identify:Int,
    val cardimageurl:String?,
    var isFaceUp:Boolean,
    var isMatched:Boolean
)