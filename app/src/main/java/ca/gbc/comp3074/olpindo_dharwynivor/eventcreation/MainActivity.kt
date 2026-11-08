package ca.gbc.comp3074.olpindo_dharwynivor.eventcreation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.ui.theme.EventcreationTheme
import ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.ui.theme.screens.Screen1Home
import ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.ui.theme.screens.Screen2MainNavAnon
import ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.ui.theme.screens.Screen3MainNavLoggedIn
import ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.ui.theme.screens.Screen4UserDashboard
import ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.ui.theme.screens.Screen5UserAvailabilities
import ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.ui.theme.screens.Screen6NewAvailability
import ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.ui.theme.screens.Screen7EventAvailabilities
import ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.ui.theme.screens.Screen8EventCreation
import ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.ui.theme.screens.Screen9Success
import ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.ui.theme.viewmodel.ParticipantViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: ParticipantViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EventcreationTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                val notification = viewModel.latestNotification
                LaunchedEffect(notification) {
                    notification?.let {
                        snackbarHostState.showSnackbar(it)
                        viewModel.clearNotification()
                    }
                }
                Scaffold(snackbarHost = {SnackbarHost(snackbarHostState)}, modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var currentScreen by remember { mutableIntStateOf(1) }

                    // Screens we came from, so the phone's Back button can return to them
                    val history = remember { mutableStateListOf<Int>() }

                    // Use this to open a screen (remembers where we were)
                    fun goTo(screen: Int) {
                        history.add(currentScreen)
                        currentScreen = screen
                    }

                    // Phone Back button / gesture: go to the previous screen
                    BackHandler(enabled = history.isNotEmpty()) {
                        currentScreen = history.removeAt(history.lastIndex)
                    }

                    when (currentScreen) {
                        1 -> Screen1Home(
                            modifier = Modifier.padding(innerPadding),
                            onGoClicked = {
                                viewModel.triggerNotification("Event code validated!")
                                goTo(6)
                            },
                            onLogInClicked = { goTo(4) },
                            onNavClicked = { goTo(2) }
                        )

                        2 -> Screen2MainNavAnon(
                            modifier = Modifier.padding(innerPadding),
                            onNavClicked = { goTo(3) }
                        )

                        3 -> Screen3MainNavLoggedIn(
                            modifier = Modifier.padding(innerPadding),
                            onAvailabilityClicked = { goTo(5) },
                            onDashboardClicked = { goTo(4) }
                        )

                        4 -> Screen4UserDashboard(
                            onAddEventClicked = { goTo(8) },
                            onAvailabilityClicked = { goTo(5) },
                            onEventClicked = { goTo(7) }

                        )

                        5 -> Screen5UserAvailabilities(
                            onNewAvailabilityClicked = { goTo(6) },
                            onDashboardClicked = { goTo(4) }
                        )

                        6 -> Screen6NewAvailability(
                            modifier = Modifier.padding(innerPadding),
                            onSaveClicked = {
                                viewModel.submitAvailability("My Custom Schedule")
                                goTo(5)
                            },
                            onImportClicked = {
                                viewModel.importIcsCalendar()
                            },
                            onBackClicked = { goTo(5) }
                        )

                        7 -> Screen7EventAvailabilities(
                            modifier = Modifier.padding(innerPadding),
                            onBackClicked = { goTo(4) }
                        )

                        8 -> Screen8EventCreation(
                            modifier = Modifier.padding(innerPadding),
                            onSubmitClicked = {
                                viewModel.triggerNotification("Event successfully created!")
                                goTo(9)
                            },
                        )

                        9 -> Screen9Success(
                            modifier = Modifier.padding(innerPadding),
                            onViewEventsClicked = { goTo(7) },
                            onEditClicked = { goTo(8) },
                        )
                    }
                }
            }
        }
    }
}