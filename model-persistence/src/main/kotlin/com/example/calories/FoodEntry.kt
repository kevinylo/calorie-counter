package com.example.calories

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.calories.modelconstant.MealType
import kotlinx.parcelize.Parcelize
import org.joda.time.DateTime

@Entity(
    tableName = "food_entry"
)

@Parcelize
data class FoodEntry(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0L,

    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "type")
    val type: MealType = MealType.UNKNOWN,

    @ColumnInfo(name = "calories")
    val calories: Float,

    @ColumnInfo(name = "protein")
    val protein: Float? = null,

    @ColumnInfo(name = "fat")
    val fat: Float? = null,

    @ColumnInfo(name = "carb")
    val carb: Float? = null,

    @ColumnInfo(name = "entry_date")
    val entryDate: DateTime,
): Parcelable

