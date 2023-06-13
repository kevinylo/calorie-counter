package com.example.calories.entry

import android.app.Activity
import android.content.Intent
import android.opengl.Visibility
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import co.example.calories.R
import co.example.calories.databinding.ActivityEntryBinding
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.datePicker
import com.afollestad.materialdialogs.datetime.timePicker
import com.example.BaseActivity
import com.example.calories.Entry
import com.example.calories.FoodEntry
import com.example.calories.Optional
import com.example.calories.dagger.module.Injector
import com.example.calories.isValid
import com.example.calories.modelconstant.MealType
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.checked
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.LocalDate
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class EntryActivity : BaseActivity(), EntryRenderer {

    @Inject
    lateinit var presenter: EntryPresenter

    private lateinit var binding: ActivityEntryBinding
    private val dateTimeSelector = BehaviorSubject.createDefault(Optional.absent<DateTime>())

    companion object {
        const val ENTRY = "FOOD_ENTRY"
        const val CATEGORY = "ENTRY_CATEGORY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerEntryComponent.factory()
            .create(
                mainComponent = Injector,
                activity = this,
                entry = intent.getParcelableExtra<FoodEntry>(ENTRY)?.let {
                    Optional.of(it)
                } ?: Optional.absent(),
                category = intent.getStringExtra(CATEGORY)?.let {
                    Optional.of(MealType.valueOf(it))
                } ?: Optional.absent()
            )
            .inject(this)

        binding = ActivityEntryBinding.inflate(layoutInflater)

        setContentView(binding.root)

        presenter.consume(this)
    }

    override val entryDeleted: Observable<Unit> by lazy { binding.delete.clicks() }

    override val datePickerClicked: Observable<Unit> by lazy { binding.dateButton.clicks() }

    override val entrySaved: Observable<FoodEntry> by lazy {
        binding.save.clicks().map {
            FoodEntry(
                name = binding.name.text!!.toString(),
                calories = binding.calories.text!!.toString().toFloat(),
                type = when (binding.mealType.checkedRadioButtonId) {
                    R.id.breakfast -> MealType.BREAKFAST
                    R.id.lunch -> MealType.LUNCH
                    R.id.dinner -> MealType.DINNER
                    R.id.snack -> MealType.SNACK
                    else -> MealType.UNKNOWN
                },
                fat = binding.fat.text?.toString()?.toFloatOrNull(),
                protein = binding.protein.text?.toString()?.toFloatOrNull(),
                carb = binding.carbs.text?.toString()?.toFloatOrNull(),
                entryDate = dateTimeSelector.value?.orNull() ?: DateTime.now(DateTimeZone.forOffsetHours(-8))
            )
        }
    }

    override val entryNameChanged: Observable<CharSequence> by lazy {
        binding.name.textChanges()
    }

    override fun isCurrentEntryValid(): Boolean {
        return Entry(
            name = binding.name.text?.toString(),
            calories = binding.calories.text?.toString(),
            protein = binding.protein.text?.toString(),
            fat = binding.fat.text?.toString(),
            carb = binding.carbs.text?.toString()
        )
            .isValid()
    }

    override val inputTextChanged: Observable<Pair<InputCategory, CharSequence>> by lazy {
        Observable.merge(
            binding.calories.textChanges().skip(1).map { Pair(InputCategory.CALORIES, it) },
            binding.fat.textChanges().skip(1).map { Pair(InputCategory.FAT, it) },
            binding.protein.textChanges().skip(1).map { Pair(InputCategory.PROTEIN, it) },
            binding.carbs.textChanges().skip(1).map { Pair(InputCategory.CARBS, it) },
        )
    }

    override fun render(state: EntryState) {
        when (state) {
            is Start -> {}
            is Deleted -> this.finish()
            is EntryException -> Toast.makeText(this, String.format("invalid %s", state.type), Toast.LENGTH_SHORT).show()
            is EnableSave -> binding.save.isEnabled = state.enable
            is Saved -> {
                setResult(Activity.RESULT_OK, Intent())
                this.finish()
            }
            is InitializeEntry -> {
                binding.name.setText(state.entry.name, TextView.BufferType.EDITABLE)
                binding.calories.setText(state.entry.calories.toString(), TextView.BufferType.EDITABLE)
                binding.setType(state.entry.type)
                binding.fat.setText(state.entry.fat?.toString() ?: "", TextView.BufferType.EDITABLE)
                binding.protein.setText(state.entry.protein?.toString() ?: "", TextView.BufferType.EDITABLE)
                binding.carbs.setText(state.entry.carb?.toString() ?: "", TextView.BufferType.EDITABLE)
                binding.delete.visibility = View.VISIBLE
                binding.dateButton.text = state.entry.entryDate.toLocalDate().toString("EEEE MMMM dd")
                dateTimeSelector.onNext(Optional.of(state.entry.entryDate))
            }
            is ShowDatePicker -> MaterialDialog(this).show {
                datePicker { dialog, calendar ->
                    dateTimeSelector.onNext(Optional.of(DateTime(calendar, DateTimeZone.forOffsetHours(-8))))
                    binding.dateButton.text = SimpleDateFormat("EEEE MMMM dd", Locale.US).format(calendar.time)
                }
            }
            is SetMealType -> {
                binding.setType(state.type)
                binding.mealType.visibility = View.INVISIBLE
            }
        }
    }

    private fun ActivityEntryBinding.setType(type: MealType) {
        when (type) {
            MealType.BREAKFAST -> breakfast.isChecked = true
            MealType.LUNCH -> lunch.isChecked = true
            MealType.DINNER -> dinner.isChecked = true
            MealType.SNACK -> snack.isChecked = true
            else -> {}
        }
    }

}