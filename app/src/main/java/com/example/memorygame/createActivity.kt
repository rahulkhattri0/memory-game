package com.e

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import com.example.memorygame.CustomCardClicked
import com.example.memorygame.R
import com.example.memorygame.createactivityadapter
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memorygame.models.boardsize
import com.example.memorygame.utils.PICKED_BOARD_SIZE
import java.net.URI

class createActivity : AppCompatActivity() {
    private lateinit var boardsize: boardsize
    private lateinit var rvimagepicker:RecyclerView
    private lateinit var savebutton: Button
    private lateinit var textfield:EditText
    private lateinit var adapter:createactivityadapter
    private val gallerypicIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true).setType("image/*")
    private var  permissions : ActivityResultLauncher<Array<String>> = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()){
        it.entries.forEach {
                permission ->
            val granted = permission.value
            val nameofpermission = permission.key
            if(granted){
                if(nameofpermission== Manifest.permission.READ_EXTERNAL_STORAGE){
                    Toast.makeText(this,"permission for storage granted", Toast.LENGTH_LONG).show()
                }
                opengallery.launch(gallerypicIntent)
            }
            else{
                if(nameofpermission== Manifest.permission.READ_EXTERNAL_STORAGE){
                    Toast.makeText(this,"permission for storage denied", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private val list_of_uris= mutableListOf<Uri>() //mutable list that contains the paths of the images that are chosen by the user
    private var opengallery: ActivityResultLauncher<Intent> =registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val data = result.data?.data
            val clippeddata = result.data?.clipData
            if (clippeddata != null) {
                for (i in 0 until clippeddata.itemCount) {
                    val clipItem = clippeddata.getItemAt(i)
                    if (list_of_uris.size < boardsize.getpairs()) {
                        list_of_uris.add(clipItem.uri)
                    }
                }
            }
            else if(data != null){
                list_of_uris.add(data)
            }
        }
        adapter.notifyDataSetChanged()
        supportActionBar!!.title = "Choose pics: ${list_of_uris.size}/${boardsize.getpairs()}"


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        rvimagepicker=findViewById(R.id.image_picker)
        savebutton=findViewById(R.id.savebutton)
        textfield=findViewById(R.id.edit_game_name)
        boardsize = intent.getSerializableExtra(PICKED_BOARD_SIZE) as boardsize //getting the boardsize from the put extra method that we called in the main activity
        supportActionBar!!.title = "Choose pics: 0/${boardsize.getpairs()}"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        rvimagepicker.layoutManager=GridLayoutManager(this,boardsize.getwidth())
        adapter= createactivityadapter(this,list_of_uris,boardsize,
            object : CustomCardClicked {
                override fun onCustomCardClicked() {
                    if(!readstorageallowed())
                    {
                        requeststoragepermissions()
                    }
                    else
                    {
                        opengallery.launch(gallerypicIntent)
                    }
                }
            })
        rvimagepicker.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home /*back button id provided to us by android*/ ){
            finish()
        }
        return true
    }
    private fun requeststoragepermissions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        ) {
            showRationale("Storage permission required", "Click on the 'OK' below to grant storage access ")
        } else {
            permissions.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
        }
    }
    private fun showRationale(Title:String,message:String) {
        val b: AlertDialog.Builder = AlertDialog.Builder(this)
        b.setTitle(Title).setMessage(message).setPositiveButton("OK") { _,_ ->
            permissions.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
        }
        val a : AlertDialog = b.create()
        a.show()
    }
    private fun readstorageallowed():Boolean{
        val result = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }
}