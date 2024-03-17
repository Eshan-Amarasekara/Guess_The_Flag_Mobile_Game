package com.example.guesstheflag

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        GuessButton(text = "Button 1")
        Spacer(modifier = Modifier.height(16.dp))
        GuessButton(text = "Button 2")
        Spacer(modifier = Modifier.height(16.dp))
        GuessButton(text = "Button 3")
        Spacer(modifier = Modifier.height(16.dp))
        GuessButton(text = "Button 4")
    }

}

@Composable
fun GuessButton(text: String) {
    Button(onClick = { /* Handle button click */ }) {
        Text(text = text)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GuessTheFlagTheme {
        HomePage()
    }
}