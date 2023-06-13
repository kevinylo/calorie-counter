package com.example.calories.details

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import com.example.BaseActivity
import com.example.calories.dagger.module.MainComponent
import com.example.calories.dagger.module.scopes.PerActivity
import dagger.BindsInstance
import dagger.Component

@PerActivity
@Component(dependencies = [MainComponent::class])
interface DetailsComponent {
    @Component.Factory
    interface Factory {
        fun create(
            mainComponent: MainComponent,
            @BindsInstance activity: FragmentActivity,
        ): DetailsComponent
    }
    fun inject(activity: DetailsActivity)
    fun inject(fragment: DetailsFragment)
}
