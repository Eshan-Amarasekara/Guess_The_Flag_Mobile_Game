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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.guesstheflag.ui.theme.GuessTheFlagTheme

class AdvancedLevel : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GenerateRandomFlags(CountryCodes)
        }
    }
}

@Composable
fun GenerateRandomFlags(list :List<Int>) {
    var flag by remember { mutableIntStateOf(list.random()) }
    var flagIndex by remember { mutableIntStateOf(list.indexOf(flag)) }
    var valueFromMap by remember { mutableStateOf<String>("") }

    valueFromMap = countriesList.toList()[flagIndex]
    var flagList by remember { mutableStateOf(mutableListOf<Int>()) }
    var correction by remember { mutableStateOf<String>("") }
    var random2 by remember { mutableStateOf(list.filter { it != flag }.random()) }
    var random3 by remember { mutableStateOf(list.filter { it != flag && it != random2 }.random()) }
    var isShuffled by remember { mutableStateOf(false) }
    var guessInput1 by remember { mutableStateOf("") }
    var guessInput2 by remember { mutableStateOf("") }
    var guessInput3 by remember { mutableStateOf("") }
    var buttonText by remember { mutableStateOf<String>("Submit") }
    var is1Correct by remember { mutableStateOf<Boolean?>(null) }
    var is2Correct by remember { mutableStateOf<Boolean?>(null) }
    var is3Correct by remember { mutableStateOf<Boolean?>(null) }
    var tries by remember { mutableStateOf<Int>(3) }
    var textField1Color by remember { mutableStateOf(when (is1Correct) {
        true -> Color.Green
        false -> Color.Red
        null -> Color.White
    })}
    var textField2Color by remember { mutableStateOf(when (is2Correct) {
        true -> Color.Green
        false -> Color.Red
        null -> Color.White
    })}
    var textField3Color by remember { mutableStateOf(when (is3Correct) {
        true -> Color.Green
        false -> Color.Red
        null -> Color.White
    })}
    var is1Enabled by remember { mutableStateOf(true) }
    var is2Enabled by remember { mutableStateOf(true) }
    var is3Enabled by remember { mutableStateOf(true) }


    flagList.add(flag)
    flagList.add(random2)
    flagList.add(random3)


    //Randomizing the list and assigning them to variables
    //https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/shuffle.html
    if (isShuffled == false) {
        flagList.shuffle()
        isShuffled = true
    }

    var flag1 by remember { mutableStateOf(flagList[0]) }
    var flag2 by remember { mutableStateOf(flagList[1]) }
    var flag3 by remember { mutableStateOf(flagList[2]) }

    Column() {

        Spacer(modifier = Modifier.height(16.dp))

        Row{
            val imageModifier = Modifier
                .padding(10.dp)
                .border(BorderStroke(6.dp, Color.Black))
                .width(200.dp)
                .height(150.dp)
                .fillMaxSize()

            Image(
                painter = painterResource(id = flag1),
                contentDescription = " ",
                contentScale = ContentScale.FillBounds,
                modifier = imageModifier

            )
            Log.d(countriesList.toList()[(list.indexOf(flag1))],"Flag1")
            Log.d(guessInput1,"flag1 input")


            TextField(
                value = guessInput1,

                //https://stackoverflow.com/questions/67136058/textfield-maxlength-android-jetpack-compose
                onValueChange = {guessInput1 = it },
                label = { Text("Input Guess:", style = TextStyle(fontWeight = FontWeight.Bold)) },
                enabled=(is1Enabled),
                //https://www.kodeco.com/38708142-jetpack-compose-getting-started/lessons/13
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color(235, 127, 0) ,
                    focusedContainerColor = Color(235, 127, 0).copy(alpha = 0.2f),
                    focusedLabelColor = Color(235, 127, 0),
                    unfocusedContainerColor = textField1Color,
                    disabledTextColor = Color.Green,
                    disabledIndicatorColor = Color.Green.copy(alpha = 0.2f)

                ), modifier = Modifier.align(Alignment.CenterVertically),
                )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row{
            val imageModifier = Modifier
                .padding(10.dp)
                .border(BorderStroke(6.dp, Color.Black))
                .width(200.dp)
                .height(150.dp)
                .fillMaxSize()

            Image(
                painter = painterResource(id = flag2),
                contentDescription = " ",
                contentScale = ContentScale.FillBounds,
                modifier = imageModifier

            )
            Log.d(countriesList.toList()[(list.indexOf(flag2))],"Flag2")
            Log.d(guessInput2,"flag2 input")

            TextField(
                value = guessInput2,

                //https://stackoverflow.com/questions/67136058/textfield-maxlength-android-jetpack-compose
                onValueChange = {guessInput2 = it},
                label = { Text("Input Guess:", style = TextStyle(fontWeight = FontWeight.Bold)) },
                enabled=(is2Enabled),

                //https://www.kodeco.com/38708142-jetpack-compose-getting-started/lessons/13
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color(235, 127, 0),
                    focusedContainerColor = Color(235, 127, 0).copy(alpha = 0.2f),
                    focusedLabelColor = Color(235, 127, 0),
                    unfocusedContainerColor = textField2Color,
                    disabledTextColor = Color.Green,
                    disabledIndicatorColor = Color.Green.copy(alpha = 0.2f)

                ), modifier = Modifier.align(Alignment.CenterVertically),
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row{
            val imageModifier = Modifier
                .padding(10.dp)
                .border(BorderStroke(6.dp, Color.Black))
                .width(200.dp)
                .height(150.dp)
                .fillMaxSize()

            Image(
                painter = painterResource(id = flag3),
                contentDescription = " ",
                contentScale = ContentScale.FillBounds,
                modifier = imageModifier

            )
            Log.d(countriesList.toList()[(list.indexOf(flag3))],"Flag3")
            Log.d(guessInput3,"flag3 input")

            TextField(
                value = guessInput3,

                //https://stackoverflow.com/questions/67136058/textfield-maxlength-android-jetpack-compose
                onValueChange = {guessInput3 = it},
                label = { Text("Input Guess:", style = TextStyle(fontWeight = FontWeight.Bold)) },
                enabled=(is3Enabled),

                //https://www.kodeco.com/38708142-jetpack-compose-getting-started/lessons/13
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color(235, 127, 0),
                    focusedContainerColor = Color(235, 127, 0).copy(alpha = 0.2f),
                    focusedLabelColor = Color(235, 127, 0),
                    unfocusedContainerColor = textField3Color,
                    disabledTextColor = Color.Green,
                    disabledIndicatorColor = Color.Green.copy(alpha = 0.2f)

                ), modifier = Modifier.align(Alignment.CenterVertically),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ){
            Button(onClick = {
                if (buttonText == "Next") {
                    flag = list.random()
                    flagIndex =(list.indexOf(flag))
                    valueFromMap =""
                    valueFromMap = countriesList.toList()[flagIndex]
                    flagList = mutableListOf<Int>()
                    correction=""
                    random2 = (list.filter { it != flag }.random())
                    random3 = (list.filter { it != flag && it != random2 }.random())
                    isShuffled =false
                    guessInput1 =""
                    guessInput2 =""
                    guessInput3 =""
                    buttonText = "Submit"
                    is1Correct = null
                    is2Correct = null
                    is3Correct = null
                    tries =3
                    flagList.add(flag)
                    flagList.add(random2)
                    flagList.add(random3)
                    is1Enabled=true
                    is2Enabled=true
                    is3Enabled=true
                    if (isShuffled == false) {
                        flagList.shuffle()
                        isShuffled = true
                    }

                    flag1 =(flagList[0])
                    flag2 =(flagList[1])
                    flag3 =(flagList[2])

                    textField1Color =(when (is1Correct) {
                        true -> Color.Green
                        false -> Color.Red
                        null -> Color.White
                    })
                    textField2Color =(when (is2Correct) {
                        true -> Color.Green
                        false -> Color.Red
                        null -> Color.White
                    })
                    textField3Color =(when (is3Correct) {
                        true -> Color.Green
                        false -> Color.Red
                        null -> Color.White
                    })
                } else {
                    if (countriesList.toList()[(list.indexOf(flag1))] == guessInput1) {
                        is1Correct = true
                        is1Enabled=false
                    } else {
                        is1Correct = false
                    }
                    textField1Color =(when (is1Correct) {
                        true -> Color.Green
                        false -> Color.Red
                        null -> Color.White
                    })

                    if (countriesList.toList()[(list.indexOf(flag2))] == guessInput2) {
                        is2Correct = true
                        is2Enabled=false

                    } else {
                        is2Correct = false
                    }
                    textField2Color =(when (is2Correct) {
                        true -> Color.Green
                        false -> Color.Red
                        null -> Color.White
                    })
                    if (countriesList.toList()[(list.indexOf(flag3))] == guessInput3) {
                        is3Correct = true
                        is3Enabled=false

                    } else {
                        is3Correct = false
                    }
                    textField3Color =(when (is3Correct) {
                        true -> Color.Green
                        false -> Color.Red
                        null -> Color.White
                    })
                    if (is1Correct == false || is2Correct == false || is3Correct == false) {
                        tries -= 1
                    }
                    if (is1Correct == true && is2Correct == true && is3Correct == true) {
                        correction = "Correct!"
                        buttonText = "Next"
                        Log.d(correction, "correction")
                    } else if (tries == 0) {
                        correction = "Wrong!"
                        buttonText = "Next"
                    }
                }
                             },
                colors = ButtonDefaults.buttonColors(Color(235, 127, 0))
            ) {
                Text(buttonText)
            }
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
