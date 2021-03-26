package com.timmerl.mementoguesser.presentation.navigation

import androidx.appcompat.app.AppCompatActivity
import com.timmerl.mementoguesser.presentation.addmemento.AddMementoActivity
import com.timmerl.mementoguesser.presentation.mementoguesser.MementoGuesserActivity
import com.timmerl.mementoguesser.presentation.mementomanagement.MementoManagementActivity

interface INavigator {
    fun launch(activity: AppCompatActivity)
}

sealed class AppNavigator : INavigator {
    object NavigateToAddMemento : AppNavigator() {
        override fun launch(activity: AppCompatActivity) {
            AddMementoActivity.launch(activity)
            activity.finish()
        }
    }

    object NavigateToManagement : AppNavigator() {
        override fun launch(activity: AppCompatActivity) {
            MementoManagementActivity.launch(activity)
            activity.finish()
        }
    }

    object NavigateToMementoGuesser : AppNavigator() {
        override fun launch(activity: AppCompatActivity) {
            MementoGuesserActivity.launch(activity)
            activity.finish()
        }
    }
}