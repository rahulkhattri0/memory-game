package com.example.memorygame.models

import com.google.firebase.firestore.PropertyName

//in this data class we get the image urls corressponding to the document and the annotation we have used("@PropertyName") is provieded to us by firestore
// this annotation is used to specify the field(in our case "images")
data class User_image_list(
    @PropertyName("images") val images:List<String>? =null
)
