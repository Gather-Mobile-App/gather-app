package ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Screen 1 - APPLICATION HOME SCREEN (UI prototype)
// Landing page for users who are not logged in. The app starts here.
@Composable
fun Screen1Home(
    modifier: Modifier = Modifier,
    onGoClicked: () -> Unit = {},
    onBrowseEventsClicked: () -> Unit = {},
    onSignUpClicked: () -> Unit = {},
    onLogInClicked: () -> Unit = {},
    onNavClicked: () -> Unit = {}
) {
    var eventCode by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("GATHER", fontSize = 24.sp, style = MaterialTheme.typography.titleLarge)
            IconButton(onClick = onNavClicked) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
            }
        }

        Text(
            "HOME SCREEN",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.weight(1f))

        // Enter event code + GO
        Text("ENTER YOUR EVENT CODE")
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = eventCode,
                onValueChange = { eventCode = it },
                placeholder = { Text("A1A1A1") },
                singleLine = true,
                modifier = Modifier.width(200.dp)
            )
            OutlinedButton(onClick = onGoClicked) {
                Text("GO")
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
        Text("OR")
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(onClick = onBrowseEventsClicked) {
            Text("BROWSE EVENTS")
        }

        Spacer(modifier = Modifier.weight(1f))

        // Bottom: SIGN UP / LOG IN
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = onSignUpClicked, modifier = Modifier.weight(1f)) {
                Text("SIGN UP")
            }
            Button(onClick = onLogInClicked, modifier = Modifier.weight(1f)) {
                Text("LOG IN")
            }
        }
    }
}
