package com.example.calories.main.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import co.example.calories.R
import co.example.calories.databinding.ViewCategoryBinding
import com.example.calories.MealCategoryData
import com.example.calories.modelconstant.MealType
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable

class CategoryView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private val binding: ViewCategoryBinding
    private lateinit var type: MealType

    init {
        binding = ViewCategoryBinding.bind(LayoutInflater.from(context).inflate(R.layout.view_category, this))
    }

    fun addClicks(): Observable<MealType> {
        return binding.add.clicks().map { type }
    }

    fun initialize(data: MealCategoryData) {
        type = data.type
        binding.category.text = type.name
        binding.calories.text = data.totalCalories.toString()
        binding.fat.text = data.totalFat?.toString() ?: ""
        binding.protein.text = data.totalProtein?.toString() ?: ""
        binding.carbs.text = data.totalCarbs?.toString() ?: ""
    }

    fun highlight(color: Int) {
        binding.box.setBackgroundColor(color)
    }

    fun disableHighlight() {
        binding.box.setBackgroundColor(Color.LTGRAY)
    }
}