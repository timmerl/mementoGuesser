package com.timmerl.mementoguesser.presentation.navigation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timmerl.mementoguesser.presentation.addmemento.AddMementoActivity
import com.timmerl.mementoguesser.presentation.mementoguesser.MementoGuesserActivity
import com.timmerl.mementoguesser.presentation.mementomanagement.MementoManagementActivity
import com.timmerl.mementoguesser.presentation.utils.UiEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NavigationViewModel : ViewModel() {
    private val mutableEvent = MutableLiveData<UiEvent<AppNavigator>>()
    val navigationEvent: LiveData<UiEvent<AppNavigator>> = mutableEvent

    fun navigateToAddMemento() {
        viewModelScope.launch(Dispatchers.IO) {
            mutableEvent.postValue(UiEvent.create(AppNavigator.NavigateToAddMemento))
        }
    }

    fun navigateToManagement() {
        viewModelScope.launch(Dispatchers.IO) {
            mutableEvent.postValue(UiEvent.create(AppNavigator.NavigateToManagement))
        }
    }

    fun navigateToGuesser() {
        viewModelScope.launch(Dispatchers.IO) {
            mutableEvent.postValue(UiEvent.create(AppNavigator.NavigateToMementoGuesser))
        }
    }
}

interface INavigator {
    fun launch(context: Context)
}

sealed class AppNavigator : INavigator {
    object NavigateToAddMemento : AppNavigator() {
        override fun launch(context: Context) {
            AddMementoActivity.launch(context)
        }
    }

    object NavigateToManagement : AppNavigator() {
        override fun launch(context: Context) {
            MementoManagementActivity.launch(context)
        }
    }

    object NavigateToMementoGuesser : AppNavigator() {
        override fun launch(context: Context) {
            MementoGuesserActivity.launch(context)
        }
    }
}