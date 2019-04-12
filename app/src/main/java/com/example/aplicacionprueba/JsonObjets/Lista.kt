package com.example.aplicacionprueba.JsonObjets

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Lista(

    @PrimaryKey var ListId: Int,
    var NameList: String
)