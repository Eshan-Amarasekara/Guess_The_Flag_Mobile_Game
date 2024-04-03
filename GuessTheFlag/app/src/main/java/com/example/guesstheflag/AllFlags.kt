package com.example.guesstheflag

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class AllFlags : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GenerateAllFlags()
        }
    }
}

@Composable
fun GenerateAllFlags(){

    var flag by rememberSaveable { mutableStateOf<Int>(0)}
    var index by rememberSaveable { mutableStateOf<Int>(0)}

    Column() {
        Row{
            LazyColumn(
                modifier = Modifier
                    .height(800.dp)
                    .padding(10.dp)
            ) {
                items(countriesList) { countryName ->
                    index= countriesList.indexOf((countryName))
                    flag = CountryCodes[index]
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Color.White,
                                shape = RoundedCornerShape(18.dp) // Setting border radius
                            )
                            .border(
                                BorderStroke(4.dp, Color(235, 127, 0)),
                                shape = RoundedCornerShape(18.dp)
                            ),

                        ) {

                        Column(modifier = Modifier
                            .padding(top = 80.dp, start = 80.dp)
                            .fillMaxWidth()
                            ,verticalArrangement = Arrangement.Center)
                        {
                            val imageModifier = Modifier
                                .padding(10.dp)
                                .border(BorderStroke(6.dp, Color.Black))
                                .width(200.dp)
                                .height(150.dp)
                                .fillMaxSize()

                            //Rendering image
                            Image(
                                painter = painterResource(id = flag),
                                contentDescription = " ",
                                contentScale = ContentScale.FillBounds,
                                modifier = imageModifier

                            )
                        }

                        Row(modifier = Modifier
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        )  {
                            Text(
                                text = " $countryName",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = Color.Black, // Changing Colors if Selected

                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp)) // Add space between boxes
                }
            }
            }
        }
    }

