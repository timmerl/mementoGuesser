package com.timmerl.mementoguesser.presentation.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    // TODO: Expose a flow for events
    private val _drawerShouldBeOpened = MutableLiveData(false)
    val drawerShouldBeOpened: LiveData<Boolean> = _drawerShouldBeOpened

    fun openDrawer() {
        _drawerShouldBeOpened.value = true
    }

    fun resetOpenDrawerAction() {
        _drawerShouldBeOpened.value = false
    }
}
