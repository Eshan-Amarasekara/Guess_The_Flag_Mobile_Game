package com.example.guesstheflag

//Optimized Imports
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
import androidx.compose.runtime.LaunchedEffect
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
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

class GuessTheFlag : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //getting the data passed from the MainActivity
            var timer = intent.getBooleanExtra("timer", false)
            Generate3Flags(CountryCodes,timer)
        }
    }
}

@Composable
fun Generate3Flags(list :List<Int>,timer: Boolean){
    var flag by rememberSaveable { mutableIntStateOf(list.random()) }                                       //getting the first random flag
    var flagIndex by rememberSaveable { mutableIntStateOf(list.indexOf(flag)) }                             //Getting the index of the first flag
    var valueFromList by rememberSaveable { mutableStateOf<String>("") }                              //Variable created to get String value of country name
    valueFromList = countriesList.toList()[flagIndex]                                                       //Getting String value of country name
    var flagList by rememberSaveable { mutableStateOf(mutableListOf<Int>()) }                               //Variable to hold the 3 random flags
    var correction by rememberSaveable { mutableStateOf<String>("") }                                 //Variable used to check the correction of the answer
    var random2 by rememberSaveable { mutableStateOf(list.filter { it != flag }.random()) }                 //Getting 2nd random flag (checked if it's same as the 1st flag)
    var random3 by rememberSaveable { mutableStateOf(list.filter { it != flag && it != random2 }.random()) }//Getting 3rd random flag (checked if it's same as the 1st and 2nd flags)
    var isShuffled by rememberSaveable { mutableStateOf(false) }                                      //Boolean value to check if it's shuffled
    var isEnabled by rememberSaveable { mutableStateOf(true) }                                        //Boolean variable to make list items disabled or enabled
    var time by rememberSaveable { mutableStateOf(10)}                                                //time variable with 10 initial to start the timer at 10
    var pause by rememberSaveable { mutableStateOf(false) }                                           //Boolean variable to check if the timer should pause


    //Adding generated flags to a list
    flagList.add(flag)
    flagList.add(random2)
    flagList.add(random3)


    //Randomizing the list and assigning them to variables
    //https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/shuffle.html
    if (isShuffled == false){
        flagList.shuffle()
        isShuffled = true
    }

    //assigning flags from the list to variables
    var flag1 by rememberSaveable { mutableStateOf(flagList[0]) }
    var flag2 by rememberSaveable { mutableStateOf(flagList[1]) }
    var flag3 by rememberSaveable { mutableStateOf(flagList[2]) }

    //Logs to check random flags
    Log.d(flag1.toString(), "flag1")
    Log.d(flag2.toString(), "flag2")
    Log.d(flag3.toString(), "flag3")
    Log.d(flagList.toString(), "flagList")



    //https://stackoverflow.com/questions/68164883/how-do-i-create-a-jetpack-compose-column-where-a-middle-child-is-scrollable-but
    Column (modifier = Modifier
        .verticalScroll(rememberScrollState())){
        //Timer:
        Row {
            //https://medium.com/@android-world/jetpack-compose-countdown-timer-9531dd3119a6
            if (timer) {
                LaunchedEffect(key1 = time, key2 = pause) {
                    while (time > 0 && !pause) {
                        delay(1.seconds)
                        time--
                    }
                }
            }
            //Auto submit when timer hits 0
            if(time==0){
               isEnabled = false
                correction = "Wrong!"
                pause=!pause
                time=10
            }

            //Display timer if switch is toggled
            if(timer) {
                Text(
                    text = "Time left: $time",
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp
                )
            }
        }

        //Display name of the correct flag
        Row(modifier= Modifier.align(Alignment.CenterHorizontally)){
            Text(text = "$valueFromList",
                style = TextStyle (),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.Center,
            modifier =  Modifier.fillMaxWidth()
        ){
            //Adding customised properties for image separately and assigning to a variable
            val imageModifier = Modifier
                .padding(10.dp)
                .border(BorderStroke(6.dp, Color.Black))
                .width(200.dp)
                .height(150.dp)
                .fillMaxSize()
                .clickable(enabled = isEnabled) {

                    if (countriesList.toList()[(list.indexOf(flag1))] == valueFromList) {
                        correction = "Correct!"
                    } else {
                        correction = "Wrong!"
                    }

                    flagList.clear()
                    isEnabled=false

                //alpha used to change opacity when the images are disabled or enabled
                }.alpha(if (isEnabled) 1f else 0.5f)

            //Rendering image
            Image(
                painter = painterResource(id = flag1),
                contentDescription = " ",
                contentScale = ContentScale.FillBounds,
                modifier = imageModifier

            )

        }
        Row(modifier= Modifier.align(Alignment.CenterHorizontally)){
            val imageModifier = Modifier
                //Adding customised properties for image separately and assigning to a variable
                .padding(10.dp)
                .border(BorderStroke(6.dp, Color.Black))
                .width(200.dp)
                .height(150.dp)
                .fillMaxSize()
                .clickable(enabled = isEnabled) {
                    if (countriesList.toList()[(list.indexOf(flag2))] == valueFromList) {
                        correction = "Correct!"
                    } else {
                        correction = "Wrong!"
                    }
                    flagList.clear()
                    isEnabled=false

                    //alpha used to change opacity when the images are disabled or enabled
                }.alpha(if (isEnabled) 1f else 0.5f)

            Image(
                painter = painterResource(id = flag2),
                contentDescription = " ",
                contentScale = ContentScale.FillBounds,
                modifier = imageModifier

            )

        }
        Row(modifier= Modifier.align(Alignment.CenterHorizontally)){
            //Adding customised properties for image separately and assigning to a variable
            val imageModifier = Modifier
                .padding(10.dp)
                .border(BorderStroke(6.dp, Color.Black))
                .width(200.dp)
                .height(150.dp)
                .fillMaxSize()
                .clickable(enabled = isEnabled) {
                    if (countriesList.toList()[(list.indexOf(flag3))] == valueFromList) {
                        correction = "Correct!"
                    } else {
                        correction = "Wrong!"
                    }
                    flagList.clear()
                    isEnabled=false

                    //alpha used to change opacity when the images are disabled or enabled
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

            //checking what correction value is and displaying accordingly
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
                    //resetting all variables when "Next" is clicked
                    pause=false
                    flagList.clear()
                    flag = (list.random())
                    flagIndex = (list.indexOf(flag))
                    valueFromList = countriesList.toList()[flagIndex]
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