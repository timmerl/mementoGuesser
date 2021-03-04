package com.timmerl.mementoguesser.presentation.mementoguesser

import androidx.lifecycle.ViewModel
import com.timmerl.mementoguesser.domain.repository.QuestionRepository

/**
 * Created by Timmerman_Lyderic on 02/03/2021.
 */

class MementoGuesserViewModel(
    rep: QuestionRepository
) : ViewModel() {

/*
 todo
  MementoManagementActivity -> affiche la liste de question
                              -> permet de supprimer/editer des questions
  réunir main et gameViewModel
  renommer Game en CurrentQuestion
  une Queue de Question -> une liveData pour le premier element (CurrentQuestion)
                        -> une liveData pour la liste des elements à venir (IncomingQuestion)
  déplacer main dans IncomingQuestionFragment
  faire une ui stylée
  MementoGuesserActivity (mainActivity)
  SelectMementoActivity -> affiche toutes les listes de question (memento)
                        -> itemClick: le memento est selectionné et MementoGuesserActivity se lance
                        -> permet de créer une nouvelle liste


 */

}