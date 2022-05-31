package com.example.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memorygame.models.MemoryCard
import com.example.memorygame.models.boardsize
import com.example.memorygame.models.memorygame
import com.example.memorygame.utils.default_icons

class MainActivity : AppCompatActivity() {
    private var rvboard:RecyclerView? = null
    private var tvmoves:TextView? = null
    private var tvpairs:TextView? = null
    private var Boardsize: boardsize = boardsize.Medium
    private lateinit var adapter: MemoryAdapter
    private lateinit var memorygame: memorygame
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvboard = findViewById(R.id.rvboard)
        tvmoves = findViewById(R.id.moves)
        tvpairs = findViewById(R.id.pairs)
        memorygame=memorygame(Boardsize)
        adapter=MemoryAdapter(this,Boardsize,memorygame.list_of_images_mapped,/*anonymous inner class*/ object:MemoryAdapter.CardClickListener{
            override fun onCardClicked(position: Int) {
                updategamewithflip(position)
            }

        })
        rvboard!!.adapter =adapter
        rvboard!!.layoutManager = GridLayoutManager(this,Boardsize.getwidth())
    }

    private fun updategamewithflip(position: Int) {
        memorygame.flipcard(position)
        adapter.notifyDataSetChanged()//tells recycler view that there is a change in the contents of the view
    }
}