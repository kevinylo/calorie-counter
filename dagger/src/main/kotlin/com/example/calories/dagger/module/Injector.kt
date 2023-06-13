package com.example.calories.dagger.module

import android.app.Application
import com.example.manager.CaloriesManager

object Injector : MainComponent {

  private lateinit var component: MainComponent

  fun init(app: Application) {
    component = DaggerMainComponent
      .builder()
      .appModule(AppModule(app))
      .caloriesModule(CaloriesModule((app)))
      .build()
  }

  override fun caloriesManager(): CaloriesManager = component.caloriesManager()
}