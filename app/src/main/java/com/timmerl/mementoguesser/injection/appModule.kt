package com.timmerl.mementoguesser.injection

import android.app.Application
import androidx.room.Room
import com.timmerl.mementoguesser.data.database.AppDatabase
import com.timmerl.mementoguesser.data.database.dao.QuestionDao
import com.timmerl.mementoguesser.data.database.repository.QuestionRepositoryImpl
import com.timmerl.mementoguesser.domain.adapter.MementoInteractorImpl
import com.timmerl.mementoguesser.domain.adapter.MementoInteractor
import com.timmerl.mementoguesser.domain.repository.QuestionRepository
import com.timmerl.mementoguesser.presentation.addquestion.AddQuestionViewModel
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

    fun provideQuestionDao(database: AppDatabase): QuestionDao {
        return database.questionDao()
    }

    fun provideQuestionRepository(
        dao: QuestionDao
    ): QuestionRepository {
        return QuestionRepositoryImpl(dao)
    }

    fun provideMementoInteractor(
        rep: QuestionRepository
    ): MementoInteractor = MementoInteractorImpl(rep)

    single { provideDatabase(androidApplication()) }
    single { provideQuestionDao(get()) }
    single { provideQuestionRepository(get()) }
    single { provideMementoInteractor(get()) }
}

val viewModelModule = module {

    viewModel { MementoGuesserViewModel(adapter = get()) }
    viewModel { AddQuestionViewModel(adapter = get()) }
    viewModel { MementoManagementViewModel(adapter = get()) }
}