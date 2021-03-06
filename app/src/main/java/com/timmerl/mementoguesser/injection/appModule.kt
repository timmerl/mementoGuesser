package com.timmerl.mementoguesser.injection

import android.app.Application
import androidx.room.Room
import com.timmerl.mementoguesser.data.database.AppDatabase
import com.timmerl.mementoguesser.data.database.dao.QuestionDao
import com.timmerl.mementoguesser.data.database.repository.QuestionRepositoryImpl
import com.timmerl.mementoguesser.domain.repository.QuestionRepository
import com.timmerl.mementoguesser.presentation.addquestion.AddQuestionViewModel
import com.timmerl.mementoguesser.presentation.currentquestion.CurrentQuestionViewModel
import com.timmerl.mementoguesser.presentation.mementoguesser.MementoGuesserViewModel
import com.timmerl.mementoguesser.presentation.mementomanagement.MementoManagementViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

val databaseModule = module {

    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "questionDB")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideQuestionDao(database: AppDatabase): QuestionDao {
        return database.questionDao()
    }

    single { provideDatabase(androidApplication()) }
    single { provideQuestionDao(get()) }
}

val repositoryModule = module {

    fun provideQuestionRepository(
        dao: QuestionDao
    ): QuestionRepository {
        return QuestionRepositoryImpl(dao)
    }

    single { provideQuestionRepository(get()) }

}


val viewModelModule = module {

    viewModel { CurrentQuestionViewModel(rep = get()) }
    viewModel { AddQuestionViewModel(rep = get()) }
    viewModel { MementoGuesserViewModel(rep = get()) }
    viewModel { MementoManagementViewModel(rep = get()) }
}