package com.example.guesstheflag

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class GuessTheFlag : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Generate3Flags(CountryCodes)
        }
    }
}

//@Composable
//fun generateNewFlag(flag:Int){
//
//    val imageModifier = Modifier
//        .padding(10.dp)
//        .border(BorderStroke(6.dp, Color.Black))
//        .width(200.dp)
//        .height(150.dp)
//        .fillMaxSize()
//        .clickable {
//
//        }
//
//    Image(
//        painter = painterResource(id = flag),
//        contentDescription = " ",
//        contentScale = ContentScale.FillBounds,
//        modifier = imageModifier
//
//    )
//
//}

@Composable
fun Generate3Flags(list :List<Int>){
    var flag by remember { mutableIntStateOf(list.random()) }
    var flagIndex by remember { mutableIntStateOf(list.indexOf(flag)) }
    var valueFromMap by remember { mutableStateOf<String>("") }
    valueFromMap = countriesList.toList()[flagIndex]
    var flagList by remember { mutableStateOf(mutableListOf<Int>()) }
    var correction by remember { mutableStateOf<String>("") }

    flagList.add(flag)

    while(true){
        var random2 = list.random()
        if(random2 !in flagList){
            flagList.add(random2)
            break
        }
    }

    while(true) {
        var random3 = list.random()
        if (random3 !in flagList) {
            flagList.add(random3)
            break
        }
    }




    //Randomizing the list and assigning them to variables
    //https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/shuffle.html
    flagList.shuffle()
    var flag1 = flagList[0]
    var flag2 = flagList[1]
    var flag3 = flagList[2]

    //Logs to check random flags
    Log.d(flag1.toString(), "flag1")
    Log.d(flag2.toString(), "flag2")
    Log.d(flag3.toString(), "flag3")
    Log.d(flagList.toString(), "flagList")



    Column (){
        Row(modifier= Modifier.align(Alignment.CenterHorizontally)){
            Text(text = "$valueFromMap",
                style = TextStyle (),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.Center,
            modifier =  Modifier.fillMaxWidth()
        ){
//            var flag1Index=(list.indexOf(flag))
            val imageModifier = Modifier
                .padding(10.dp)
                .border(BorderStroke(6.dp, Color.Black))
                .width(200.dp)
                .height(150.dp)
                .fillMaxSize()
                .clickable {
                    if (countriesList.toList()[(list.indexOf(flag1))] == valueFromMap) {
                        correction = "Correct!"
                    } else {
                        correction = "Wrong!"
                    }
                }

            Image(
                painter = painterResource(id = flag1),
                contentDescription = " ",
                contentScale = ContentScale.FillBounds,
                modifier = imageModifier

            )

        }
        Row(modifier= Modifier.align(Alignment.CenterHorizontally)){
            val imageModifier = Modifier
                .padding(10.dp)
                .border(BorderStroke(6.dp, Color.Black))
                .width(200.dp)
                .height(150.dp)
                .fillMaxSize()
                .clickable {
                    if (countriesList.toList()[(list.indexOf(flag2))] == valueFromMap) {
                        correction = "Correct!"
                    } else {
                        correction = "Wrong!"
                    }
                }

            Image(
                painter = painterResource(id = flag2),
                contentDescription = " ",
                contentScale = ContentScale.FillBounds,
                modifier = imageModifier

            )

        }
        Row(modifier= Modifier.align(Alignment.CenterHorizontally)){
            val imageModifier = Modifier
                .padding(10.dp)
                .border(BorderStroke(6.dp, Color.Black))
                .width(200.dp)
                .height(150.dp)
                .fillMaxSize()
                .clickable {
                    if (countriesList.toList()[(list.indexOf(flag3))] == valueFromMap) {
                        correction = "Correct!"
                    } else {
                        correction = "Wrong!"
                    }
                }

            Image(
                painter = painterResource(id = flag3),
                contentDescription = " ",
                contentScale = ContentScale.FillBounds,
                modifier = imageModifier

            )

        }

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {

            if(correction == "Correct!") {
                Text(text = correction,
                    style = TextStyle(color = Color.Green, fontStyle = FontStyle.Italic),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                )
            }else if(correction == "Wrong!"){
                Text(text = correction,
                    style = TextStyle(color = Color.Red, fontStyle = FontStyle.Italic),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}