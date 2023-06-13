package com.example.calories.main

import android.util.AndroidException
import co.example.calories.core.mrp.AbstractPresenter
import com.example.calories.details.Initialize
import com.example.calories.details.adpater.DetailsConverter.toEntryRow
import com.example.calories.main.CaloriesConverter.patchWithAllCategories
import com.example.calories.main.CaloriesConverter.toMealCategoryData
import com.example.manager.CaloriesManager
import com.github.mikephil.charting.data.PieEntry
import com.uber.autodispose.autoDispose
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * MVI style presenter that be more easily tested based on states
 * Presenter reacts to renderer (view) and then doing one directional flow from presenter to renderer (view) for updates
 */
class CaloriesPresenter @Inject constructor(
    private val caloriesManager: CaloriesManager
): AbstractPresenter<CaloriesRenderer, CaloriesState>(initial = Start) {

    override fun consume(renderer: CaloriesRenderer) {
        super.consume(renderer)

        renderer
            .addEntryClicks
            .autoDispose(renderer)
            .subscribe({
                emit(AddEntry(it))
            }, {
                Timber.e(it)
            })

        Observable.merge(
            Observable.just(Unit),
            renderer.entriesUpdated
        )
            .observeOn(Schedulers.io())
            .flatMapSingle {
                caloriesManager.loadEntriesToday()
            }
            .map { entries ->
                val categoryData = entries.toMealCategoryData()
                val bottomSheetData = categoryData.patchWithAllCategories()
                val pieEntries = mutableListOf<PieEntry>()
                categoryData.forEach { (key, value) ->
                    pieEntries.add(PieEntry(value.totalCalories, key.name))
                }
                Pair(pieEntries, bottomSheetData)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .autoDispose(renderer)
            .subscribe({ (pieEntries, bottomSheetData) ->
                emit(SetPieEntries(pieEntries))
                emit(UpdateCategories(bottomSheetData))
            }, {
                Timber.e(it)
            })
    }

}
