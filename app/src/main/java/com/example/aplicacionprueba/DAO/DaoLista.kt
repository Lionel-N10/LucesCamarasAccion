package com.example.aplicacionprueba.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.aplicacionprueba.JsonObjets.ListMovies

@Dao
interface DaoLista {

    @Query("SELECT * FROM ListMovies")
    fun getListas(): List<ListMovies>

    @Insert
    fun insertLista(lista: ListMovies): Long

    @Delete
    fun deleteLista(lista: ListMovies)
}


