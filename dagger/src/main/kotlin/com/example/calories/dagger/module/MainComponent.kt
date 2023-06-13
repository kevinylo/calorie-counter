package com.example.calories.dagger.module

import com.example.manager.CaloriesManager
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
  (AppModule::class),
  (CaloriesModule::class)
])

interface MainComponent {
  // Only has one manager for now but could have a lot more
  fun caloriesManager(): CaloriesManager
}