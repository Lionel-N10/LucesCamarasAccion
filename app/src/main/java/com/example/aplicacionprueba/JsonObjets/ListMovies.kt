package com.example.aplicacionprueba.JsonObjets

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey


@Entity(
    primaryKeys = ["ListId", "MovieId"],
    foreignKeys = [
        ForeignKey(
            entity = Lista::class,
            parentColumns = ["ListId"],
            childColumns = ["ListId"],
            onDelete = CASCADE
        )]
)
data class ListMovies(
    var ListId: Int,
    var MovieId: Int
)