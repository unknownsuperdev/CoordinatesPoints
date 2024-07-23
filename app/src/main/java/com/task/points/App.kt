package com.task.points

import android.app.Application
import android.content.Context
import com.task.points.di.AppModule
import com.task.data.di.DataModule
import com.task.domain.di.DomainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.ksp.generated.module
import timber.log.Timber

class App : Application() {

    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
       appContext = applicationContext
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidContext(appContext)
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            modules(modules)
        }
    }

    private val modules = listOf(
        DataModule().module,
        com.task.domain.di.DomainModule().module,
        AppModule().module
    )
}
