package com.example.calories

import android.app.Application
import com.example.calories.dagger.module.Injector

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Injector.init(this)
    }
}