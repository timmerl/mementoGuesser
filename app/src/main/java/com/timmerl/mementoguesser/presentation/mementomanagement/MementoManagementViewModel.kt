package com.timmerl.mementoguesser.presentation.mementomanagement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.timmerl.mementoguesser.domain.repository.QuestionRepository

/**
 * Created by Timmerman_Lyderic on 02/03/2021.
 */

class MementoManagementViewModel(
    rep: QuestionRepository
) : ViewModel() {

    val questionList = rep.getAll().asLiveData(viewModelScope.coroutineContext)

}