package com.example.aplicacionprueba.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aplicacionprueba.DAO.DaoList
import com.example.aplicacionprueba.DAO.DaoListMovies
import com.example.aplicacionprueba.JsonObjets.ListMovies
import com.example.aplicacionprueba.JsonObjets.Lista


@Database(entities = [Lista::class, ListMovies::class], version = 2, exportSchema = false)

abstract class DataBase : RoomDatabase() {

    abstract fun DatMovies(): DaoListMovies
    abstract fun DaoList(): DaoList

    companion object {
        @Volatile
        private var instance: DataBase? = null
        private val LOCK = Any()

        private fun Build(context: Context) = Room.databaseBuilder(context, DataBase::class.java, "database.db").fallbackToDestructiveMigration().build()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: Build(context).also { instance = it }
        }
    }
}