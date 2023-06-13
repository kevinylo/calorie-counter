package com.example.calories.details

import co.example.calories.core.mrp.Renderer
import com.example.calories.FoodEntry
import io.reactivex.Observable

interface DetailsRenderer: Renderer<DetailsState> {
    val addEntryClicked: Observable<Unit>
    val entrySelected: Observable<FoodEntry>
    val entriesUpdated: Observable<Unit>
    val searchUpdated: Observable<CharSequence>
}