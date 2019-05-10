package com.example.aplicacionprueba.roomobjects

import androidx.room.Entity

@Entity(
    primaryKeys = ["UserId"]
)

data class Users(
    var UserId: Int,
    var UserName: String,
    var UserPass: String
)