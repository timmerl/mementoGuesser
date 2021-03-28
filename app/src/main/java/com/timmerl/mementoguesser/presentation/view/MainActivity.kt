package com.timmerl.mementoguesser.presentation.view

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.accompanist.insets.ProvideWindowInsets
import com.timmerl.mementoguesser.R
import com.timmerl.mementoguesser.databinding.MainActivityBinding
import com.timmerl.mementoguesser.presentation.common.MgDrawerScaffold
import com.timmerl.mementoguesser.presentation.utils.BackPressHandler
import com.timmerl.mementoguesser.presentation.utils.LocalBackPressedDispatcher
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            // Provide WindowInsets to our content. We don't want to consume them, so that
            // they keep being pass down the view hierarchy (since we're using fragments).
            ProvideWindowInsets(consumeWindowInsets = false) {
                CompositionLocalProvider(
                    LocalBackPressedDispatcher provides this.onBackPressedDispatcher
                ) {
                    val scaffoldState = rememberScaffoldState()

                    val openDrawerEvent = viewModel.drawerShouldBeOpened.observeAsState()
                    if (openDrawerEvent.value == true) {
                        // Open drawer and reset state in VM.
                        LaunchedEffect("launchEffectMain") {
                            scaffoldState.drawerState.open()
                            viewModel.resetOpenDrawerAction()
                        }
                    }

                    // Intercepts back navigation when the drawer is open
                    val scope = rememberCoroutineScope()
                    if (scaffoldState.drawerState.isOpen) {
                        BackPressHandler {
                            scope.launch {
                                scaffoldState.drawerState.close()
                            }
                        }
                    }

                    MgDrawerScaffold(
                        scaffoldState,
                        onManagementClicked = {
                            findNavController().navigate(R.id.navigate_to_Management)
                            scope.launch {
                                scaffoldState.drawerState.close()
                            }
                        },
                        onAddMementoClicked = {
                            findNavController().navigate(R.id.navigate_to_add_memento)
                            scope.launch {
                                scaffoldState.drawerState.close()
                            }
                        }
                    ) {
                        //// TODO: Fragments inflated via AndroidViewBinding don't work as expected
                        ////  https://issuetracker.google.com/179915946
                        //// AndroidViewBinding(ContentMainBinding::inflate)
                        FragmentAwareAndroidViewBinding(bindingBlock = MainActivityBinding::inflate)
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController().navigateUp() || super.onSupportNavigateUp()
    }

    /**
     * See https://issuetracker.google.com/142847973
     */
    private fun findNavController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController
    }

}