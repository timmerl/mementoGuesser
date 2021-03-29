package com.timmerl.mementoguesser.presentation.view.app

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import com.google.accompanist.insets.ProvideWindowInsets
import com.timmerl.mementoguesser.presentation.common.MgScaffold
import com.timmerl.mementoguesser.presentation.view.MainViewModel
import com.timmerl.mementoguesser.presentation.view.addmemento.AddMementoScreen
import com.timmerl.mementoguesser.presentation.view.mementoguesser.MementoGuesserScreen
import com.timmerl.mementoguesser.presentation.view.mementomanagement.MementoManagementsScreen
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@ExperimentalFoundationApi
@Composable
fun MgApp(
    backDispatcher: OnBackPressedDispatcher
) {
    val viewModel = getViewModel<MainViewModel>()
    val navigator: Navigator<Destination> = rememberSaveable(
        saver = Navigator.saver(backDispatcher)
    ) {
        Navigator(Destination.Guesser, backDispatcher)
    }
    val actions = remember(navigator) { Actions(navigator) }

    ProvideWindowInsets {

        val scaffoldState = rememberScaffoldState()

        val openDrawerEvent = viewModel.drawerShouldBeOpened.observeAsState()
        if (openDrawerEvent.value == true) {
            // Open drawer and reset state in VM.
            LaunchedEffect("launchEffectMain") {
                scaffoldState.drawerState.open()
                viewModel.resetOpenDrawerAction()
            }
        }
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
                        AddMementoScreen(addMementoViewModel = getViewModel())
                    }
                    Destination.Guesser -> {
                        MementoGuesserScreen(viewModel = getViewModel())
                    }
                    Destination.Management -> {
                        MementoManagementsScreen(
                            viewModel = getViewModel(),
                            onEmptyAction = actions.addMemento
                        )
                    }
                }
            }
        }
    }
}