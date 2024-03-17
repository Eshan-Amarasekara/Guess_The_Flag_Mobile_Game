package com.example.guesstheflag

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.guesstheflag.ui.theme.GuessTheFlagTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomePage()
        }
    }
}

@Composable
fun HomePage(){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current

        Row{
            Button(onClick = {
                var i = Intent(context, GuessTheCountry::class.java)
                context.startActivity(i)
            },modifier=Modifier
                .size(110.dp),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color.Black),
                border = BorderStroke(3.dp, Color.Black),
                shape = RoundedCornerShape(20),
            ) {
                Text(text = "Guess the Country")
            }
            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = {},modifier=Modifier
                .size(110.dp),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color.Magenta),
                border = BorderStroke(3.dp, Color.Black),
                shape = RoundedCornerShape(20),
            ) {
                Text(text = "Guess - Hints")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row{
            Button(onClick = {},modifier=Modifier
                .size(110.dp),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color.Magenta),
                border = BorderStroke(3.dp, Color.Black),
                shape = RoundedCornerShape(20),
            ) {
                Text(text = "Guess the Flag")
            }
            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = {},modifier=Modifier
                .size(110.dp),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color.Magenta),
                border = BorderStroke(3.dp, Color.Black),
                shape = RoundedCornerShape(20),

            ) {
                Text(text = "Advanced Level")
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GuessTheFlagTheme {
        HomePage()
    }
}