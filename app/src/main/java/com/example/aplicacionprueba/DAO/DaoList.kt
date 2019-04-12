package com.example.aplicacionprueba.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.aplicacionprueba.JsonObjets.Lista
import com.example.aplicacionprueba.JsonObjets.Users

@Dao
interface DaoList {

    @Query("SELECT * FROM Lista")
    fun getLista(): List<Lista>

    @Insert
    fun insertLista(lista: Lista): Long

    @Delete
    fun deleteLista(lista: Lista)
}