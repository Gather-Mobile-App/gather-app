package ca.gbc.comp3074.olpindo_dharwynivor.eventcreation.ui.theme.screens

import android.widget.Space
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
//Make funtcion
fun AvailabilityBox(name : String){

    //Text
    Text(
        text = name,
        fontSize = 11.sp,
        lineHeight = 13.sp,
        maxLines = 2,
        modifier = Modifier.width(105.dp).height(65.dp).border(1.dp, Color.Gray, RoundedCornerShape(8.dp)).padding(8.dp)
    )
}

//User Availability Section
//Annotation
@Composable
//Make function
fun Screen5UserAvailabilities(

    //Make 2 Arrow functions
    onNewAvailabilityClicked: () -> Unit = {},
    onDashboardClicked: () -> Unit = {}
){
    //LazyColumn Section
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {

        //item Section
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
            Text(text = "YOUR TIME TO GATHER", fontSize = 15.sp)

            //Spacer
            Spacer(modifier = Modifier.height(30.dp))

            //Make list of Availability
            //Text
            Text(text = "MY AVAILABILITIES", fontSize = 16.sp)

            //Spacer
            Spacer(modifier = Modifier.height(10.dp))

            //Row Section - Availability
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                AvailabilityBox("MAIN AVAILABILITY")
                AvailabilityBox("SCHEDULE 2")
                AvailabilityBox("SCHEDULE 3")
            }

            //Spacer
            Spacer(modifier = Modifier.height(10.dp))

            //Row - SCHEDULE 4
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                AvailabilityBox("SCHEDULE 4")

            }

            //Button - Add Availability
            Button(onClick = onNewAvailabilityClicked) {
                //Text
                Text("+ NEW AVAILABILITY")
            }

            //Spacer
            Spacer(modifier = Modifier.height(30.dp))

            //rsvped events
            Text(text = "WHEN I'M GATHERING", fontSize = 16.sp)

            //Spacer
            Spacer(modifier = Modifier.height(10.dp))

            //Row Section - RSVPED MEETING
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                AvailabilityBox("RSVPED MEETING 1")
                AvailabilityBox("RSVPED MEETING 2")
                AvailabilityBox("RSVPED MEETING 3")
            }

            //Spacer
            Spacer(modifier = Modifier.height(25.dp))

            //Button - BACK TO DASHBOARD
            Button(onClick = onDashboardClicked) {
                //Text
                Text("BACK TO DASHBOARD")
            }



        }
    }
}
