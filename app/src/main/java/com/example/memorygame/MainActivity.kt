package com.example.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memorygame.models.boardsize
import com.example.memorygame.utils.default_icons

class MainActivity : AppCompatActivity() {
    private var rvboard:RecyclerView? = null
    private var tvmoves:TextView? = null
    private var tvpairs:TextView? = null
    private var Boardsize: boardsize = boardsize.Hard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvboard = findViewById(R.id.rvboard)
        tvmoves = findViewById(R.id.moves)
        tvpairs = findViewById(R.id.pairs)
        val chosen_images=default_icons.shuffled().take(Boardsize.getpairs())
        val randomimages= (chosen_images + chosen_images).shuffled()
        rvboard!!.adapter=MemoryAdapter(this,Boardsize,randomimages)
        rvboard!!.layoutManager = GridLayoutManager(this,Boardsize.getwidth())
    }
}