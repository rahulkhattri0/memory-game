package com.example.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.memorygame.models.boardsize
import com.example.memorygame.utils.PICKED_BOARD_SIZE

class createActivity : AppCompatActivity() {
    private lateinit var boardsize: boardsize
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        boardsize = intent.getSerializableExtra(PICKED_BOARD_SIZE) as boardsize //getting the boardsize from the put extra method that we called in the main activity
        supportActionBar!!.title = "Choose pics: 0/${boardsize.getpairs()}"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home /*back button id provided to us by android*/ ){
            finish()
        }
        return true
    }
}