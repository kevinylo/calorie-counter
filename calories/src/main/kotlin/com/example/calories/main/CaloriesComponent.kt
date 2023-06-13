package com.example.calories.main

import androidx.fragment.app.FragmentActivity
import com.example.calories.dagger.module.MainComponent
import com.example.calories.dagger.module.scopes.PerActivity
import dagger.BindsInstance
import dagger.Component

@PerActivity
@Component(dependencies = [MainComponent::class])
interface CaloriesComponent {
    @Component.Factory
    interface Factory {
        fun create(
            mainComponent: MainComponent,
            @BindsInstance activity: FragmentActivity,
        ): CaloriesComponent
    }
    fun inject(fragment: CaloriesFragment)
}
