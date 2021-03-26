package com.timmerl.mementoguesser.injection

import android.app.Application
import androidx.room.Room
import com.timmerl.mementoguesser.data.database.AppDatabase
import com.timmerl.mementoguesser.data.database.dao.MementoDao
import com.timmerl.mementoguesser.data.database.repository.MementoRepositoryImpl
import com.timmerl.mementoguesser.domain.adapter.MementoAdapter
import com.timmerl.mementoguesser.domain.adapter.MementoAdapterImpl
import com.timmerl.mementoguesser.domain.repository.QuestionRepository
import com.timmerl.mementoguesser.presentation.addmemento.AddMementoViewModel
import com.timmerl.mementoguesser.presentation.mementoguesser.MementoGuesserViewModel
import com.timmerl.mementoguesser.presentation.mementomanagement.MementoManagementViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

val mementoModule = module {
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "questionDB")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideMementoDao(database: AppDatabase): MementoDao {
        return database.mementoDao()
    }

    fun provideMementoRepository(
        dao: MementoDao
    ): QuestionRepository {
        return MementoRepositoryImpl(dao)
    }

    fun provideMementoInteractor(
        rep: QuestionRepository
    ): MementoAdapter = MementoAdapterImpl(rep)

    single { provideDatabase(androidApplication()) }
    single { provideMementoDao(get()) }
    single { provideMementoRepository(get()) }
    single { provideMementoInteractor(get()) }
}

val viewModelModule = module {

    viewModel { MementoGuesserViewModel(adapter = get()) }
    viewModel { AddMementoViewModel(adapter = get()) }
    viewModel { MementoManagementViewModel(adapter = get()) }
}