package com.timmerl.mementoguesser.app

import android.app.Application
import com.timmerl.mementoguesser.injection.databaseModule
import com.timmerl.mementoguesser.injection.repositoryModule
import com.timmerl.mementoguesser.injection.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

class MementoGuesserApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MementoGuesserApplication)
            modules(viewModelModule, repositoryModule, databaseModule)
        }
    }

}