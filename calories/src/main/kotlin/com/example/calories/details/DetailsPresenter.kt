package com.example.calories.details

import co.example.calories.core.mrp.AbstractPresenter
import com.example.calories.Optional
import com.example.calories.details.adpater.DetailsConverter.toEntryRow
import com.example.manager.CaloriesManager
import com.uber.autodispose.autoDispose
import io.reactivex.schedulers.Schedulers
import org.joda.time.DateTime
import timber.log.Timber
import javax.inject.Inject

/**
 * MVI style presenter that be more easily tested based on states
 * Presenter reacts to renderer (view) and then doing one directional flow from presenter to renderer (view) for updates
 */
class DetailsPresenter @Inject constructor(
    private val caloriesManager: CaloriesManager
): AbstractPresenter<DetailsRenderer, DetailsState>(initial = Start) {

    var nextPageCursor = Optional.absent<DateTime>()

    override fun consume(renderer: DetailsRenderer) {
        super.consume(renderer)
        renderer
            .addEntryClicked
            .autoDispose(renderer)
            .subscribe {
                emit(NewEntry)
            }

        renderer
            .searchUpdated
            .skip(1)
            .observeOn(Schedulers.io())
            .flatMapSingle {
                caloriesManager.search(it.toString())
            }
            .autoDispose(renderer)
            .subscribe({ entries ->
                emit(Initialize(entries.toEntryRow()))
            }, {
                Timber.e(it)
            })

        renderer
            .entriesUpdated
            .flatMap {
                caloriesManager.loadEntries()
            }
            .doOnNext { entries ->
                nextPageCursor = Optional.of(entries.last().entryDate)
            }
            .autoDispose(renderer)
            .subscribe({ entries ->
                emit(Initialize(entries.toEntryRow()))
            }, {
                Timber.e(it)
            })

        renderer
            .entrySelected
            .autoDispose(renderer)
            .subscribe({
                emit(EditEntry(it))
            }, {
                Timber.e(it)
            })

        caloriesManager
            .loadEntries()
            .autoDispose(renderer)
            .subscribe({ entries ->
                emit(Initialize(entries.toEntryRow()))
            }, {
                Timber.e(it)
            })

    }

}