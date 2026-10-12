package ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.ui.theme.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Alignment

//Annotation
@Composable
//Make function
fun DashboardEventBox(name: String){

    //Text
    Text(
        text = name,
        fontSize = 11.sp,
        lineHeight = 13.sp,
        maxLines = 2,
        modifier = Modifier.width(105.dp).height(65.dp).border(1.dp, Color.Gray, RoundedCornerShape(8.dp)).padding(8.dp)
    )
}

//Define User Dashboard
//Annotation
@Composable
//Make function
fun Screen4UserDashboard(
    //Make 2 Arrow functions
    onAddEventClicked : () -> Unit = {},
    onAvailabilityClicked : () -> Unit ={}
)

{
    //LazyColumn Section
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {

        //item section
        item {


            //header section
            //Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                //Text
                Text(text = "GATHER", fontSize = 25.sp)

                //Menu Icon
                IconButton(onClick = {}) {
                    //Icon Section - Burger Menu
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu"
                    )
                }
            }


            //Spacer
            Spacer(modifier = Modifier.height(25.dp))

            //Text
            Text(text = "WELCOME BACK!", fontSize = 15.sp)

            //Spacer
            Spacer(modifier = Modifier.height(30.dp))

            //Created Events
            Text(text = "MY CREATED EVENTS", fontSize = 16.sp)

            //Spacer
            Spacer(modifier = Modifier.height(10.dp))

            //Row Section - MEETING
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                DashboardEventBox("MEETING 1")
                DashboardEventBox("MEETING 2")
                DashboardEventBox("MEETING 3")

            }

            //Spacer
            Spacer(modifier = Modifier.height(10.dp))

            //Row Section - Add Event
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                DashboardEventBox("MEETING 4")

                //Button Section
                Button(onClick = onAddEventClicked) {
                    //Text
                    Text("+ ADD EVENT")
                }
            }
            //Spacer
            Spacer(modifier = Modifier.height(30.dp))

            //rsvped Events
            Text(text = "WHEN I'M GATHERING", fontSize = 16.sp)

            //Row Section - RSVPED
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                DashboardEventBox("RSVPED 1")
                DashboardEventBox("RSVPED 2")
                DashboardEventBox("RSVPED 3")
            }

            //Spacer
            Spacer(modifier = Modifier.height(30.dp))

            //Suggested Events
            Text(text = "SUGGESTED PUBLIC EVENTS", fontSize = 16.sp)

            //Spacer
            Spacer(modifier = Modifier.height(10.dp))

            //Row Section - EVENT
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                DashboardEventBox("EVENT 1")
                DashboardEventBox("EVENT 2")
                DashboardEventBox("EVENT 3")
            }

            //Spacer
            Spacer(modifier = Modifier.height(25.dp))

            //Button
            Button(onClick = onAvailabilityClicked) {

                //Text
                Text("MY AVAILABILITIES")
            }


        }


    }
}