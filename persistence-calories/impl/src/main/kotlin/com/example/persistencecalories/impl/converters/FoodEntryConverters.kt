package com.example.persistencecalories.impl.converters

import androidx.room.TypeConverter
import com.example.calories.modelconstant.MealType
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormatter
import org.joda.time.format.ISODateTimeFormat

object FoodEntryConverters {
    private val formatter: DateTimeFormatter = ISODateTimeFormat.dateTime()

    @TypeConverter
    @JvmStatic
    fun toMealType(value: String): MealType {
        return try {
            MealType.valueOf(value.toUpperCase())
        } catch (e: Exception) {
            MealType.UNKNOWN
        }
    }

    @TypeConverter
    @JvmStatic
    fun toDateTime(value: String?): DateTime? {
        return value?.let {
            return formatter.parseDateTime(value)
        }
    }

    @TypeConverter
    @JvmStatic
    fun fromDateTime(date: DateTime?): String? {
        return date?.let { formatter.print(date) }
    }
}