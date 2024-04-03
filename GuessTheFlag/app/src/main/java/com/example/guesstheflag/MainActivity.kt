package com.example.guesstheflag

//Optimized Imports
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //Calling HomePage() Composable function
            HomePage()
        }
    }
}

@Composable
fun HomePage(){
    //Initiating Column
    Column(
        modifier = Modifier.fillMaxSize()
        .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //
        val context = LocalContext.current
        var isChecked by rememberSaveable { mutableStateOf(false) }
        Spacer(modifier = Modifier.height(24.dp))

        //Initiating Rows to put elements in different rows
        Row {
            Text(text = "Country - Flag",
                style = TextStyle(color = Color(235, 127, 0), fontStyle = FontStyle.Italic),
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold,
            )
        }
        Row {
            Text(text = "Game",
                style = TextStyle(color = Color(235, 127, 0), fontStyle = FontStyle.Italic),
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold,
            )
        }


        Spacer(modifier = Modifier.height(132.dp))

        Row{
            Button(onClick = {
                var i = Intent(context, GuessTheCountry::class.java)
                i.putExtra("timer", isChecked)
                context.startActivity(i)
            },modifier=Modifier
                .width(130.dp),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color.Black),
                border = BorderStroke(3.dp, Color(235, 127, 0)),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(Color(235, 127, 0)))
            {
                Text(text = "Guess the Country")
            }
            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = {
                var i = Intent(context, GuessHints::class.java)
                i.putExtra("timer", isChecked)
                context.startActivity(i)
            },modifier=Modifier
                .width(130.dp),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color.Black),
                border = BorderStroke(3.dp, Color(235, 127, 0)),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(Color(235, 127, 0)))

            {
                Text(text = "Guess - Hints")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row{
            Button(onClick = {
                var i = Intent(context, GuessTheFlag::class.java)
                i.putExtra("timer", isChecked)
                context.startActivity(i)},modifier=Modifier
                .width(130.dp),

                border = BorderStroke(3.dp, Color(235, 127, 0)),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(Color(235, 127, 0)))
            {
                Text(text = "Guess the Flag")
            }
            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = {
                var i = Intent(context, AdvancedLevel::class.java)
                i.putExtra("timer", isChecked)
                context.startActivity(i)
            },modifier=Modifier
                .width(130.dp),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color.Black),
                border = BorderStroke(3.dp, Color(235, 127, 0)),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(Color(235, 127, 0)))
            {
                Text(text = "Advanced Level")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row{
            Button(onClick = {
                var i = Intent(context, AllFlags::class.java)
                context.startActivity(i)},
                modifier=Modifier
                .width(130.dp),

                border = BorderStroke(3.dp, Color(235, 127, 0)),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(Color(235, 127, 0)))
            {
                Text(text = "View All Flags")
            }
        }
        Spacer(modifier = Modifier.height(64.dp))

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ){
            Spacer(modifier = Modifier.width(74.dp))
            Text(text = "Timer : ",
                fontSize = 24.sp)
            Switch(
                checked = isChecked,
                onCheckedChange = {
                    isChecked = it
                },
                colors = SwitchDefaults.colors(
                    uncheckedBorderColor = Color(235, 127, 0),
                    uncheckedTrackColor = Color(235, 127, 0).copy(alpha = 0.2f),
                    uncheckedThumbColor = Color(235, 127, 0),
                    checkedBorderColor =  Color(235, 127, 0),
                    checkedTrackColor =  Color(235, 127, 0),
                    checkedThumbColor = Color.White,

                    )
            )
        }
        Spacer(modifier = Modifier.height(150.dp))

        Row{
            Text(text = "Created By Eshan Amarasekara")
        }
    }
}


