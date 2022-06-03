package com.example.memorygame.utils

import android.graphics.Bitmap

object BitmapScaler {
fun scale_To_fit_height(original_bitmap:Bitmap,height:Int): Bitmap {
    val factor:Float = height / original_bitmap.height.toFloat()
    val scaled_bitmap:Bitmap = Bitmap.createScaledBitmap(original_bitmap,(original_bitmap.width * factor).toInt(),height,true)
    return  scaled_bitmap
}
}
