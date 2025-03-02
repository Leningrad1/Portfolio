package com.example.headhunterapp

import android.app.Application
import androidx.room.Room
import com.google.firebase.crashlytics.FirebaseCrashlytics

class Application : Application() {
    lateinit var db: AppDataBase
        private set

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        db = Room
            .inMemoryDatabaseBuilder(
                this,
                AppDataBase::class.java
            )
            .fallbackToDestructiveMigration()
            .build()
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
        /*FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)*/
    }

    companion object {
        lateinit var INSTANCE: com.example.headhunterapp.Application
            private set
    }
}