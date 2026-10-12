package ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.ui.theme.screens

// THIS IS A PROTOTYPE!!! NEEDS FUNCTIONALITY!!!
// NEED TO HOOK INTO NAV TOGGLE BUTTON TO WOOSH THIS IN/OUT LEFT

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Screen2MainNavAnon(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(Color(0xFFEEEEEE))
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Gather",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            IconButton(onClick = { /* NEED ACTION - TEMP PROTOTYPE - LATER WE WILL TOGGLE NAV */ }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Toggle Main Nav Menu",
                    tint = Color.Black
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(2f)
                    .background(Color(0xFFF5F5F5))
                    .padding(24.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Button(
                    onClick = { /* NEED ACTION - TEMP PROTOTYPE */ },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
                ) {
                    Text("Let's Meet", fontSize = 16.sp)
                }

                Button(
                    onClick = { /* NEED ACTION - TEMP PROTOTYPE */ },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
                ) {
                    Text("How To Gather", fontSize = 16.sp)
                }

                Button(
                    onClick = { /* NEED ACTION - TEMP PROTOTYPE */ },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
                ) {
                    Text("Log In / Sign Up", fontSize = 16.sp)
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .background(Color.Gray.copy(alpha = 0.9f)),
                contentAlignment = Alignment.Center
            ) {
            }
        }
    }
}
