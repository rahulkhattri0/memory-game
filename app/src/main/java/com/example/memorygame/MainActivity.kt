package com.example.memorygame

import android.animation.ArgbEvaluator
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memorygame.models.MemoryCard
import com.example.memorygame.models.boardsize
import com.example.memorygame.models.memorygame
import com.example.memorygame.utils.default_icons
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private var rvboard:RecyclerView? = null
    private var tvmoves:TextView? = null
    private var tvpairs:TextView? = null
    private var constraintLayout:ConstraintLayout?=null
    private var Boardsize: boardsize = boardsize.Medium
    private lateinit var adapter: MemoryAdapter
    private lateinit var memorygame: memorygame
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupboard()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuinflater: MenuInflater = getMenuInflater()
        menuinflater.inflate(R.menu.menu_item_main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.refresh -> if()
        }
        return true
    }
    private fun setupboard() {
        rvboard = findViewById(R.id.rvboard)
        tvmoves = findViewById(R.id.moves)
        tvpairs = findViewById(R.id.pairs)
        constraintLayout = findViewById(R.id.main_constraint_layout)
        memorygame = memorygame(Boardsize)
        adapter = MemoryAdapter(
            this,
            Boardsize,
            memorygame.list_of_images_mapped,/*anonymous inner class*/
            object : MemoryAdapter.CardClickListener {
                override fun onCardClicked(position: Int) {
                    updategamewithflip(position)
                }

            })
        rvboard!!.adapter = adapter
        rvboard!!.layoutManager = GridLayoutManager(this, Boardsize.getwidth())
    }

    private fun updategamewithflip(position: Int) {
//        if (memorygame.hasWon())
//        {
//            Snackbar.make(constraintLayout!!,"you have won the game",Snackbar.LENGTH_LONG).show()
//            return
//        }
        if(memorygame.isFaceUp(position)){
            Snackbar.make(constraintLayout!!,"Invalid move",Snackbar.LENGTH_SHORT).show()
            return
        }
        if(memorygame.flipcard(position)){
            Log.i("match found!","pairs:${memorygame.numpairsfound}/${Boardsize.getpairs()}")//simple log entry to check everything is working fine
            val color:Int  = ArgbEvaluator().evaluate(
                memorygame.numpairsfound.toFloat()/Boardsize.getpairs(),
                Color.RED,
                Color.GREEN
            ) as Int
            tvpairs!!.setTextColor(color)
            tvpairs!!.text= "PAIRS: ${memorygame.numpairsfound}/${Boardsize.getpairs()}"
            if(memorygame.hasWon()){
                Snackbar.make(constraintLayout!!,"you have won the game!congrats",Snackbar.LENGTH_LONG).show()
            }
        }
        tvmoves!!.text="Moves: "+memorygame.getmoves()
        adapter.notifyDataSetChanged()//tells recycler view that there is a change in the contents of the view

    }
}