package com.example.memorygame

import android.animation.ArgbEvaluator
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e.createActivity
import com.example.memorygame.models.MemoryCard
import com.example.memorygame.models.boardsize
import com.example.memorygame.models.memorygame
import com.example.memorygame.utils.PICKED_BOARD_SIZE
import com.example.memorygame.utils.default_icons
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private var rvboard:RecyclerView? = null
    private var tvmoves:TextView? = null
    private var tvpairs:TextView? = null
    private var constraintLayout:ConstraintLayout?=null
    private var Boardsize: boardsize = boardsize.Easy
    private lateinit var adapter: MemoryAdapter
    private lateinit var memorygame: memorygame
    companion object{
        const val CREATE_REQUEST_CODE:Int = 32
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvboard = findViewById(R.id.rvboard)
        tvmoves = findViewById(R.id.moves)
        tvpairs = findViewById(R.id.pairs)
        constraintLayout = findViewById(R.id.main_constraint_layout)
        setupboard()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuinflater: MenuInflater = getMenuInflater()
        menuinflater.inflate(R.menu.menu_item_main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.refresh -> if((memorygame.cardflips/2) >0 && memorygame.hasWon()==false){
                showalert("Quit this game?",null)
            }
            else{
                setupboard()
            }
            R.id.sizeselector -> showChangeSizeDialog("Change board size :",null)
            R.id.custom_board -> showCreateBoardDialog("Create your own board",null)
        }
        return true
    }

    private fun showCreateBoardDialog(title: String, view: View?) {
        val alertDialog=AlertDialog.Builder(this).setTitle(title).setView(view).setNegativeButton("cancel",null)
        val items:Array<String> = arrayOf("Easy : 2 X 4","Medium : 3 X 6","Hard : 4 X 6")
        var desiredboardsize:boardsize?=null
        alertDialog.setSingleChoiceItems(items,-1){
                _,which -> desiredboardsize = when(which){
            0 -> boardsize.Easy
            1 -> boardsize.Medium
            else -> boardsize.Hard
        }
        }
        alertDialog.setPositiveButton("OK"){
                _,_ -> if(desiredboardsize==null){
            Toast.makeText(this,"please select a boardsize", Toast.LENGTH_LONG).show()
                }
                    else{
                        val intent = Intent(this, createActivity::class.java)
            intent.putExtra(/*as this is shared between activities it is better to keep it in the constants file*/PICKED_BOARD_SIZE,desiredboardsize)
            startActivityForResult(intent,CREATE_REQUEST_CODE)
                    }
        }

        alertDialog.show()

    }

    private fun showChangeSizeDialog(title: String, view: View?) {
        val alertDialog=AlertDialog.Builder(this).setTitle(title).setView(view).setNegativeButton("cancel",null)
        val items:Array<String> = arrayOf("Easy : 2 X 4","Medium : 3 X 6","Hard : 4 X 6")
        val selectedItem=when(Boardsize){
            boardsize.Easy -> 0
            boardsize.Medium -> 1
            else -> 2
        }
        alertDialog.setSingleChoiceItems(items,selectedItem){
                _,which -> Boardsize = when(which){
                    0 -> boardsize.Easy
                    1 -> boardsize.Medium
                    else -> boardsize.Hard
                }
        }
        alertDialog.setPositiveButton("OK"){
            _,_ -> setupboard()
        }

        alertDialog.show()

    }

    private fun showalert(title:String,view: View?) {
        AlertDialog.Builder(this).setTitle(title).setView(view).setNegativeButton("cancel",null).setPositiveButton("Yes"){
            _,_ -> setupboard()
        }.show()
    }

    private fun setupboard() {
        when(Boardsize){
            boardsize.Easy -> {
                tvpairs!!.text = "Pairs: 0/${boardsize.Easy.getpairs()}"
                tvmoves!!.text = "Easy : 2 X 4"
            }
            boardsize.Medium -> {
                tvpairs!!.text = "Pairs: 0/${boardsize.Medium.getpairs()}"
                tvmoves!!.text = "Medium : 3 X 6"
            }
            boardsize.Hard  -> {
                tvpairs!!.text = "Pairs: 0/${boardsize.Hard.getpairs()}"
                tvmoves!!.text = "Hard : 4 X 6"
            }

        }

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