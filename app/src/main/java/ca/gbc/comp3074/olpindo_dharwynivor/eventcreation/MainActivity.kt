package ca.gbc.comp3074.olpindo_dharwynivor.eventcreation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.ui.theme.EventcreationTheme
import ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.ui.theme.screens.Screen8EventCreation
import ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.ui.theme.screens.Screen9Success
import ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.ui.theme.screens.Screen4UserDashboard
import ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.ui.theme.screens.Screen5UserAvailabilities
import ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.ui.theme.screens.Screen1Home
import ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.ui.theme.screens.Screen6NewAvailability
import ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.ui.theme.screens.Screen7EventAvailabilities

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EventcreationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
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
                            onGoClicked = { goTo(6) },
                            onLogInClicked = { goTo(4) }
                        )

                        4 -> Screen4UserDashboard(
                            onAddEventClicked = { goTo(8) },
                            onAvailabilityClicked = { goTo(5) }
                        )

                        5 -> Screen5UserAvailabilities(
                            onNewAvailabilityClicked = { goTo(6) },
                            onDashboardClicked = { goTo(4) }
                        )

                        6 -> Screen6NewAvailability(
                            modifier = Modifier.padding(innerPadding),
                            onSaveClicked = { goTo(5) },
                            onBackClicked = { goTo(5) }
                        )

                        7 -> Screen7EventAvailabilities(
                            modifier = Modifier.padding(innerPadding),
                            onBackClicked = { goTo(4) }
                        )

                        8 -> Screen8EventCreation(
                            modifier = Modifier.padding(innerPadding),
                            onSubmitClicked = { goTo(9) },
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
