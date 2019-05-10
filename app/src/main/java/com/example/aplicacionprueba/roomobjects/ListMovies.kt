package com.example.aplicacionprueba.roomobjects

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE

@Entity(
    primaryKeys = ["MovieId", "ListId"],
    foreignKeys = [
        ForeignKey(
            entity = Lista::class,
            parentColumns = ["ListId"],
            childColumns = ["ListId"],
            onDelete = CASCADE
        )
    ]
)
data class ListMovies(
    var ListId: Int,
    var MovieId: Int
)