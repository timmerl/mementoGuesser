package com.timmerl.mementoguesser.presentation.view.app

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import com.timmerl.mementoguesser.presentation.common.MgScaffold
import com.timmerl.mementoguesser.presentation.utils.Navigator
import com.timmerl.mementoguesser.presentation.view.MainViewModel
import com.timmerl.mementoguesser.presentation.view.addmemento.AddMementoScreen
import com.timmerl.mementoguesser.presentation.view.editmemento.EditMementoScreen
import com.timmerl.mementoguesser.presentation.view.mementoguesser.MementoGuesserScreen
import com.timmerl.mementoguesser.presentation.view.mementomanagement.MementoManagementsScreen
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun MgApp(
    backDispatcher: OnBackPressedDispatcher
) {
    val viewModel = getViewModel<MainViewModel>()
    val navigator: Navigator<Destination> = rememberSaveable(
        saver = Navigator.saver(backDispatcher)
    ) {
        Navigator(
            initial = Destination.Guesser,
            backDispatcher = backDispatcher
        )
    }
    val scaffoldState = rememberScaffoldState()

    val openDrawerEvent = viewModel.drawerShouldBeOpened.observeAsState()
    if (openDrawerEvent.value == true) {
        // Open drawer and reset state in VM.
        LaunchedEffect("launchEffectMain") {
            scaffoldState.drawerState.open()
            viewModel.resetOpenDrawerAction()
        }
    }
    val actions = remember(navigator) { Actions(navigator) }
    val scope = rememberCoroutineScope()
    MgScaffold(
        scaffoldState,
        onGuesserClicked = {
            actions.guesser()
            scope.launch { scaffoldState.drawerState.close() }
        },
        onManagementClicked = {
            actions.management()
            scope.launch { scaffoldState.drawerState.close() }
        },
        onAddMementoClicked = {
            actions.addMemento()
            scope.launch { scaffoldState.drawerState.close() }
        }
    ) {
        Crossfade(navigator.current) { destination ->
            when (destination) {
                Destination.AddMemento -> {
                    AddMementoScreen(viewModel = getViewModel())
                }
                Destination.Guesser -> {
                    MementoGuesserScreen(
                        viewModel = getViewModel(),
                        navigateToAddMemento = actions.addMemento
                    )
                }
                Destination.Management -> {
                    MementoManagementsScreen(
                        viewModel = getViewModel(),
                        onEmptyAction = actions.addMemento,
                        onEditAction = actions.editMemento,
                    )
                }
                is Destination.EditMemento -> {
                    EditMementoScreen(
                        viewModel = getViewModel(
                            parameters = {
                                parametersOf(
                                    destination.mementoId
                                )
                                parametersOf(
                                    destination.imageId
                                )
                            }
                        ),
                        onActionDone = actions.upPress
                    )
                }
            }
        }
    }
}