package com.example.calories.entry

import co.example.calories.core.mrp.Renderer
import com.example.calories.FoodEntry
import io.reactivex.Observable

interface EntryRenderer : Renderer<EntryState> {
    val entryDeleted: Observable<Unit>
    val entrySaved: Observable<FoodEntry>
    val datePickerClicked: Observable<Unit>
    val inputTextChanged: Observable<Pair<InputCategory, CharSequence>>
    val entryNameChanged: Observable<CharSequence>
    fun isCurrentEntryValid(): Boolean
}

