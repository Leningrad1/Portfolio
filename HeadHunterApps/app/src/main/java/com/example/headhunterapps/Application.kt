package com.example.headhunterapps

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.headhunterapp.data.db.AppDataBase
import com.example.headhunterapp.data.db.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@HiltAndroidApp
class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE: Application
            private set
    }
}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDataBase {
        return Room.inMemoryDatabaseBuilder(
            context,
            AppDataBase::class.java
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideUserDao(db: AppDataBase): UserDao = db.userDao()
}