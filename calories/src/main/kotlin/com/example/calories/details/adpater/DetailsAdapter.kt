package com.example.calories.details.adpater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import co.example.calories.R
import co.example.calories.databinding.ViewCaloriesBinding
import co.example.calories.databinding.ViewDateBinding
import co.example.calories.databinding.ViewDividerBinding
import co.example.calories.databinding.ViewEntryBinding
import co.example.calories.databinding.ViewMealtypeBinding
import com.example.calories.FoodEntry
import com.example.calories.modelconstant.MealType
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.joda.time.LocalDate
import javax.inject.Inject

class DetailsAdapter @Inject constructor(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val entryClickSubject = PublishSubject.create<FoodEntry>()

    private lateinit var rows: MutableList<EntryRow>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            EntryRowType.DATE.value -> DateViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.view_date, parent, false)
            )
            EntryRowType.MEAL_TYPE.value -> MealTypeViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.view_mealtype, parent, false)
            )
            EntryRowType.MEAL_ENTRY.value -> EntryViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.view_entry, parent, false),
                this
            )
            EntryRowType.CALORIES_TOTAL.value -> CaloriesViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.view_calories, parent, false)
            )
            EntryRowType.DIVIDER.value -> DividerViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.view_divider, parent, false)
            )
            else -> CaloriesViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.view_calories, parent, false)
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return rows[position].type.value
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (rows[position].type) {
            EntryRowType.DATE -> (holder as DateViewHolder).bind(rows[position] as EntryRowDate)
            EntryRowType.MEAL_TYPE -> (holder as MealTypeViewHolder).bind(rows[position] as EntryRowMealType)
            EntryRowType.MEAL_ENTRY -> (holder as EntryViewHolder).bind(rows[position] as EntryRowMealEntry)
            EntryRowType.CALORIES_TOTAL -> (holder as CaloriesViewHolder).bind(rows[position] as EntryRowCalories)
            EntryRowType.DIVIDER -> {}
        }
    }

    override fun getItemCount(): Int = rows.size

    fun setEntries(entries: List<EntryRow>) {
        rows = entries.toMutableList()
    }

    fun entrySelected(): Observable<FoodEntry> = entryClickSubject.hide()
}

sealed class EntryRow(
    val type: EntryRowType
)

object EntryRowDivider: EntryRow(type = EntryRowType.DIVIDER)

data class EntryRowDate(
    val date: LocalDate
): EntryRow(type = EntryRowType.DATE)

data class EntryRowMealType(
    val mealType: MealType
): EntryRow(type = EntryRowType.MEAL_TYPE)

data class EntryRowMealEntry(
    val entry: FoodEntry
): EntryRow(type = EntryRowType.MEAL_ENTRY)

data class EntryRowCalories(
    val caloriesTotal: Float
): EntryRow(type = EntryRowType.CALORIES_TOTAL)

enum class EntryRowType(val value: Int) {
    DATE(0),
    MEAL_TYPE(1),
    MEAL_ENTRY(2),
    CALORIES_TOTAL(3),
    DIVIDER(4)
}

class DateViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ViewDateBinding.bind(view)

    fun bind(entryRowDate: EntryRowDate) {
        binding.root.text = entryRowDate.date.toString("EEEE MMMM dd")
    }
}

class MealTypeViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ViewMealtypeBinding.bind(view)

    fun bind(entryRowMealType: EntryRowMealType) {
        binding.root.text = entryRowMealType.mealType.name
    }
}

class CaloriesViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ViewCaloriesBinding.bind(view)

    fun bind(entryRowCalories: EntryRowCalories) {
        binding.calories.text = entryRowCalories.caloriesTotal.toString()
    }
}

class DividerViewHolder(view: View): RecyclerView.ViewHolder(view)

class EntryViewHolder(
    private val view: View,
    adapter: DetailsAdapter
): RecyclerView.ViewHolder(view) {
    private val binding = ViewEntryBinding.bind(view)
    lateinit var currentEntry: FoodEntry

    init {
        view.setOnClickListener {
            adapter.entryClickSubject.onNext(currentEntry)
        }
    }

    fun bind(entryRowMealEntry: EntryRowMealEntry) {
        currentEntry = entryRowMealEntry.entry
        binding.name.text = currentEntry.name
        binding.calories.text = currentEntry.calories.toString()
        currentEntry.fat?.let {
            binding.fat.text = String.format("%s (f)", it.toString())
        } ?: run {
            binding.fat.visibility = View.INVISIBLE
        }

        currentEntry.protein?.let {
            binding.protein.text = String.format("%s (p)", it.toString())
        } ?: run {
            binding.protein.visibility = View.INVISIBLE
        }

        currentEntry.carb?.let {
            binding.carbs.text = String.format("%s (c)", it.toString())
        } ?: run {
            binding.carbs.visibility = View.INVISIBLE
        }
    }
}
