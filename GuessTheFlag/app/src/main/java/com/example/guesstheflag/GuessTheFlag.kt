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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
    var flag by rememberSaveable { mutableIntStateOf(list.random()) }
    var flagIndex by rememberSaveable { mutableIntStateOf(list.indexOf(flag)) }
    var valueFromMap by rememberSaveable { mutableStateOf<String>("") }
    valueFromMap = countriesList.toList()[flagIndex]
    var flagList by rememberSaveable { mutableStateOf(mutableListOf<Int>()) }
    var correction by rememberSaveable { mutableStateOf<String>("") }
    var random2 by rememberSaveable { mutableStateOf(list.filter { it != flag }.random()) }
    var random3 by rememberSaveable { mutableStateOf(list.filter { it != flag && it != random2 }.random()) }
    var isShuffled by rememberSaveable { mutableStateOf(false) }
    var isEnabled by rememberSaveable { mutableStateOf(true) }

    flagList.add(flag)
    flagList.add(random2)
    flagList.add(random3)


    //Randomizing the list and assigning them to variables
    //https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/shuffle.html
    if (isShuffled == false){
        flagList.shuffle()
        isShuffled = true
    }

    var flag1 by rememberSaveable { mutableStateOf(flagList[0]) }
    var flag2 by rememberSaveable { mutableStateOf(flagList[1]) }
    var flag3 by rememberSaveable { mutableStateOf(flagList[2]) }

    //Logs to check random flags
    Log.d(flag1.toString(), "flag1")
    Log.d(flag2.toString(), "flag2")
    Log.d(flag3.toString(), "flag3")
    Log.d(flagList.toString(), "flagList")



    Column (modifier = Modifier
        .verticalScroll(rememberScrollState())){
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
                .clickable(enabled = isEnabled) {

                    if (countriesList.toList()[(list.indexOf(flag1))] == valueFromMap) {
                        correction = "Correct!"
                    } else {
                        correction = "Wrong!"
                    }

                    flagList.clear()
                    isEnabled=false
                }.alpha(if (isEnabled) 1f else 0.5f)

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
                .clickable(enabled = isEnabled) {
                    if (countriesList.toList()[(list.indexOf(flag2))] == valueFromMap) {
                        correction = "Correct!"
                    } else {
                        correction = "Wrong!"
                    }
                    flagList.clear()
                    isEnabled=false

                }.alpha(if (isEnabled) 1f else 0.5f)

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
                .clickable(enabled = isEnabled) {
                    if (countriesList.toList()[(list.indexOf(flag3))] == valueFromMap) {
                        correction = "Correct!"
                    } else {
                        correction = "Wrong!"
                    }
                    flagList.clear()
                    isEnabled=false

                }.alpha(if (isEnabled) 1f else 0.5f)
            Log.e(flag3.toString(),"flag3")
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

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = {
                    flagList.clear()
                    flag = (list.random())
                    flagIndex = (list.indexOf(flag))
                    valueFromMap = countriesList.toList()[flagIndex]
                    correction = ""
                    random2 = list.filter { it != flag }.random()
                    random3 = list.filter { it != flag && it != random2 }.random()
                    isShuffled = false

                    flagList.add(flag)
                    flagList.add(random2)
                    flagList.add(random3)
                    if (isShuffled == false){
                        flagList.shuffle()
                        isShuffled = true
                    }
                    flag1 = (flagList[0])
                    flag2 = (flagList[1])
                    flag3 = (flagList[2])
                    isEnabled = true
                },
                colors = ButtonDefaults.buttonColors(Color(235, 127, 0))
            ) {
                Text(text = "Next")
            }
        }
    }
}