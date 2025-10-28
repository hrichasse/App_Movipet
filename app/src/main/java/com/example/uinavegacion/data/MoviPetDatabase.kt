package com.example.uinavegacion.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.uinavegacion.data.dao.PetDao
import com.example.uinavegacion.data.dao.TripDao
import com.example.uinavegacion.data.entity.PetEntity
import com.example.uinavegacion.data.entity.TripEntity

@Database(
    entities = [PetEntity::class, TripEntity::class],
    version = 2,
    exportSchema = false
)
abstract class MoviPetDatabase : RoomDatabase() {
    abstract fun petDao(): PetDao
    abstract fun tripDao(): TripDao

    companion object {
        @Volatile
        private var INSTANCE: MoviPetDatabase? = null

        fun getDatabase(context: Context): MoviPetDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoviPetDatabase::class.java,
                    "movipet_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
