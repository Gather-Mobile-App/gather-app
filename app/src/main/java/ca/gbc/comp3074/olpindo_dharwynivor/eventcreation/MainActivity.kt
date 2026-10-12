package ca.gbc.comp3074.olpindo_dharwynivor.eventcreation

import android.os.Bundle
import androidx.activity.ComponentActivity
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EventcreationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var currentScreen by remember { mutableIntStateOf(4) }

                    when (currentScreen) {
                        4 -> Screen4UserDashboard(
                            onAddEventClicked = { currentScreen = 8 },
                            onAvailabilityClicked = { currentScreen = 5 }
                        )

                        5 -> Screen5UserAvailabilities(
                            onDashboardClicked = { currentScreen = 4 }
                        )
                        8 -> Screen8EventCreation(
                            modifier = Modifier.padding(innerPadding),
                            onSubmitClicked = { currentScreen = 9 },
                        )
                        9 -> Screen9Success(
                            modifier = Modifier.padding(innerPadding),
                            onViewEventsClicked = { currentScreen = 8 },
                            onEditClicked = { currentScreen = 8 },
                        )
                    }
                }
            }
        }
    }
}
