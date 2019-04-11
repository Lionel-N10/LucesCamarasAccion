package com.example.aplicacionprueba.JsonObjets

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey


@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Users::class,
            parentColumns = ["UserId"],
            childColumns = ["UserId"],
            onDelete = CASCADE
        )]
)
data class ListMovies(
    @PrimaryKey(autoGenerate = true) var ListId: Int,
    var UserId: Int,
    var MovieId: Int,
    var ListName: String
)