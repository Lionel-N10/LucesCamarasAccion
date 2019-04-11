package com.example.aplicacionprueba.JsonObjets

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Users(
    @PrimaryKey(autoGenerate = true) var UserId: Int,
    var UserName: String,
    var UserPass: String
)