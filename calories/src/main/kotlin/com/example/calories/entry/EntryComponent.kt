package com.example.calories.entry

import android.app.Activity
import com.example.calories.FoodEntry
import com.example.calories.Optional
import com.example.calories.dagger.module.MainComponent
import com.example.calories.dagger.module.scopes.PerActivity
import com.example.calories.modelconstant.MealType
import dagger.BindsInstance
import dagger.Component

@PerActivity
@Component(dependencies = [MainComponent::class])
interface EntryComponent {
    @Component.Factory
    interface Factory {
        fun create(
            mainComponent: MainComponent,
            @BindsInstance activity: Activity,
            @BindsInstance entry: Optional<FoodEntry>,
            @BindsInstance category: Optional<MealType>
        ): EntryComponent
    }
    fun inject(activity: EntryActivity)
}
