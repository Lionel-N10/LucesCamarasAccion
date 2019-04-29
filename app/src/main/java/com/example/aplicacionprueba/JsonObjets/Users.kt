package com.example.aplicacionprueba.JsonObjets

import androidx.room.Entity

@Entity(
    primaryKeys = ["UserId"]
)

data class Users(
    var UserId: Int,
    var UserName: String,
    var UserPass: String
)