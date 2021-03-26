package com.timmerl.mementoguesser.presentation.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.timmerl.mementoguesser.presentation.navigation.AppNavigator

open class NavigationViewModel : ViewModel() {
    private val mutableNavEvent = MutableLiveData<UiEvent<AppNavigator>>()
    val navigationEvent: LiveData<UiEvent<AppNavigator>> = mutableNavEvent

    fun navigateToAddMemento() {
        mutableNavEvent.postValue(UiEvent.create(AppNavigator.NavigateToAddMemento))
    }

    fun navigateToMementoManagement() {
        mutableNavEvent.postValue(UiEvent.create(AppNavigator.NavigateToManagement))
    }

    fun navigateToMementoGuesser() {
        mutableNavEvent.postValue(UiEvent.create(AppNavigator.NavigateToMementoGuesser))
    }
}