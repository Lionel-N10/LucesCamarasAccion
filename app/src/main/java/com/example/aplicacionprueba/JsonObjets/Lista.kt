package com.example.aplicacionprueba.JsonObjets

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE

@Entity(
    primaryKeys = ["ListId"],
    foreignKeys = [
        ForeignKey(
            entity = Users::class,
            parentColumns = ["UserId"],
            childColumns = ["UserId"],
            onDelete = CASCADE
        )]
)

data class Lista(
    var ListId: Int,
    var UserId: Int,
    var NameList: String
)