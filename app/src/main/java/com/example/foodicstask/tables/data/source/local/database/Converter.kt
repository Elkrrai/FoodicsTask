package com.example.foodicstask.tables.data.source.local.database

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun fromProductIdList(value: List<Int>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toProductIdList(value: String): List<Int> {
        return Json.decodeFromString(value)
    }
}
