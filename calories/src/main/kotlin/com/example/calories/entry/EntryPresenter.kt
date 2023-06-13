package com.example.calories.entry

import co.example.calories.core.mrp.AbstractPresenter
import com.example.calories.FoodEntry
import com.example.calories.Optional
import com.example.calories.modelconstant.MealType
import com.example.manager.CaloriesManager
import com.uber.autodispose.autoDispose
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.joda.time.DateTime
import timber.log.Timber
import javax.inject.Inject

class EntryPresenter @Inject constructor(
    private val caloriesManager: CaloriesManager,
    private val entry: Optional<FoodEntry>,
    private val category: Optional<MealType>
): AbstractPresenter<EntryRenderer, EntryState>(initial = Start) {

    override fun consume(renderer: EntryRenderer) {
        super.consume(renderer)

        if (entry.isPresent) {
            emit(InitializeEntry(entry.get()))
        }

        if (category.isPresent) {
            emit(SetMealType(category.get()))
        }

        renderer
            .entrySaved
            .observeOn(Schedulers.io())
            .flatMapCompletable {
                caloriesManager.add(FoodEntry(
                    id = entry.orNull()?.id ?: it.id,
                    name = it.name,
                    calories = it.calories,
                    type = it.type,
                    fat = it.fat,
                    protein = it.protein,
                    carb = it.carb,
                    entryDate = it.entryDate
                ))
                    .doOnComplete { emit(Saved) }
            }
            .autoDispose(renderer)
            .subscribe({}, {
                Timber.e(it)
            })

        renderer
            .inputTextChanged
            .doOnNext { (input, value) ->
                if (input == InputCategory.CALORIES && value.toString().toFloatOrNull() == null) {
                    emit(EntryException(input))
                }
            }
            .autoDispose(renderer)
            .subscribe({}, {
                Timber.e(it)
            })

        Observable.merge(
            renderer.entryNameChanged,
            renderer.inputTextChanged
        )
            .map {
                renderer.isCurrentEntryValid()
            }
            .autoDispose(renderer)
            .subscribe({
                emit(EnableSave(it))
            }, {
                Timber.e(it)
            })

        renderer
            .entryDeleted
            .filter { entry.isPresent }
            .observeOn(Schedulers.io())
            .flatMapCompletable {
                caloriesManager.delete(entry.get())
                    .doOnComplete {
                        emit(Deleted)
                    }
            }
            .autoDispose(renderer)
            .subscribe({}, {
                Timber.e(it)
            })

        renderer
            .datePickerClicked
            .autoDispose(renderer)
            .subscribe({
                emit(ShowDatePicker)
            }, {})
    }

}