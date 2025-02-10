package com.example.foodicstask.tables.data.source.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.foodicstask.tables.data.source.local.entities.CategoryEntity
import com.example.foodicstask.tables.data.source.local.entities.ProductEntity

@Database(
    entities = [CategoryEntity::class, ProductEntity::class],
    version = 1
)
abstract class TablesDatabase :
    RoomDatabase(),
    CategoriesDatabase,
    ProductsDatabase {

    companion object {
        private const val DATABASE_NAME = "tables_database"

        @Volatile
        private var INSTANCE: TablesDatabase? = null

        fun getInstance(context: Context): TablesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    TablesDatabase::class.java,
                    DATABASE_NAME
                )
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
