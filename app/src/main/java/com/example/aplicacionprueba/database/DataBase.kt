package com.example.aplicacionprueba.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aplicacionprueba.DAO.DaoList
import com.example.aplicacionprueba.DAO.DaoListMovies
import com.example.aplicacionprueba.DAO.DaoUser
import com.example.aplicacionprueba.JsonObjets.ListMovies
import com.example.aplicacionprueba.JsonObjets.Lista
import com.example.aplicacionprueba.JsonObjets.Users


@Database(entities = [Lista::class, Users::class, ListMovies::class], version = 6, exportSchema = false)

abstract class DataBase : RoomDatabase() {

    abstract fun DaoMovies(): DaoListMovies
    abstract fun DaoList(): DaoList
    abstract fun DaoUser(): DaoUser

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