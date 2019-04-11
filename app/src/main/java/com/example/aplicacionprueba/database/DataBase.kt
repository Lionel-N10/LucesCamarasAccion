package com.example.aplicacionprueba.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aplicacionprueba.DAO.DaoLista
import com.example.aplicacionprueba.DAO.DaoUsers
import com.example.aplicacionprueba.JsonObjets.ListMovies
import com.example.aplicacionprueba.JsonObjets.Users

@Database(entities = [Users::class, ListMovies::class], version = 1, exportSchema = false)

abstract class DataBase : RoomDatabase() {
    abstract fun DaoUsers(): DaoUsers
    abstract fun DaoList(): DaoLista

    companion object {
        @Volatile
        private var instance: DataBase? = null
        private val LOCK = Any()

        private fun Build(context: Context) = Room.databaseBuilder(context, DataBase::class.java, "database.db").build()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: Build(context).also { instance = it }
        }
    }
}