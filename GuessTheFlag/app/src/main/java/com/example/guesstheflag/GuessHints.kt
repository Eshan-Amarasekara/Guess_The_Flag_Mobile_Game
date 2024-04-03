package com.example.guesstheflag

//Optimized Imports
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

class GuessHints : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var timer = intent.getBooleanExtra("timer", false)
            GenerateRandomFlagForHints(CountryCodes,timer)
        }
    }
}



@Composable
fun GenerateRandomFlagForHints(list : List<Int>, timer:Boolean) {
    //https://stackoverflow.com/questions/68164883/how-do-i-create-a-jetpack-compose-column-where-a-middle-child-is-scrollable-but
    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())) {
        var correction by rememberSaveable { mutableStateOf<String>("") }                     //Variable used to check the correction of the answer
        var buttonText by rememberSaveable { mutableStateOf<String>("Submit") }               //Text for button, initiated with "Submit"
        var flag by rememberSaveable { mutableIntStateOf(list.random()) }                           //getting the first random flag
        var flagIndex by rememberSaveable { mutableIntStateOf(list.indexOf(flag)) }                 //Getting the index of the first flag
        var correctAnswer by rememberSaveable { mutableStateOf<String>("") }                  //Variable to hold correct answer
        var correctAnswerText by rememberSaveable { mutableStateOf<String>("") }              //Variable to hold the text before the answer
        var valueFromList by rememberSaveable { mutableStateOf<String>("") }                  //Variable created to get String value of country name
        var dashList by rememberSaveable{ mutableStateOf( mutableListOf<String>()) }                //List to contain the dashes that should be printed
        var letterList by rememberSaveable { mutableStateOf( mutableListOf<Char>()) }               //List to contain the letters of random flag
        var userGuess by rememberSaveable { mutableStateOf("") }                              //Variable to hold user's input
        var wrongCount by rememberSaveable { mutableStateOf<Int>(3) }                         //Attempt count
        var time by rememberSaveable { mutableStateOf(10)}                                    //time variable with 10 initial to start the timer at 10
        var pause by rememberSaveable { mutableStateOf(false) }                               //Boolean variable to check if the timer should pause

        Row {
            //timer:
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
                pause=!pause
                time = 10
                buttonText = "Next"
                correctAnswer = valueFromList
                correctAnswerText+="Correct Answer is:"
                correction = "Wrong!"
            }

            //display timer if switch is toggled
            if(timer) {
                Text(
                    text = "Time left: $time",
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp
                )
            }
        }


        Row(modifier= Modifier.align(Alignment.CenterHorizontally)) {
            valueFromList = countriesList.toList()[flagIndex]
            letterList = valueFromList.lowercase().toMutableList()  //converting the flag name to list elements

            //Checking if both letterList and dashList are both same size
            //If not for the number of letters in letterList, adding dashes and spaces to dash list
            if(letterList.size != dashList.size) {
                for (letter in letterList) {
                    if (letter == ' ') {
                        dashList.add(' '.toString())
                    } else {
                        dashList.add('-'.toString())
                    }
                }
            }

            //Logs to get following data for debugging
            Log.d(flagIndex.toString(), "GenerateRandomFlag: ")
            Log.d(dashList.toString(),"Dash list")
            Log.d(dashList.size.toString(),"Dash list size")
            Log.d(letterList.size.toString(),"Letter list size")
            Log.d(letterList.toString(), "letter List")
            Log.d(valueFromList,"Country from list")



            val imageModifier = Modifier
                .padding(20.dp)
                .border(BorderStroke(6.dp, Color.Black))
                .sizeIn(maxWidth = 300.dp, maxHeight = 300.dp)


            //Rendering image
            Image(
                painter = painterResource(id = flag),
                contentDescription = " ",
                contentScale = ContentScale.FillHeight,
                modifier = imageModifier
            )
        }


        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center) {
            Text(
                text = dashList.joinToString("  "),
                        style = TextStyle (fontStyle = FontStyle.Italic),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,)
        }

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center) {
            //Calling userguess function
            userGuess = userGuesses()
        }


        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ){

            Button(
                onClick = {
                    //Resetting variables when "Next" is clicked
                    if (buttonText == "Next") {
                        correction = ""
                        buttonText = "Submit"
                        correctAnswer = ""
                        correctAnswerText = ""
                        valueFromList=""
                        val flag2 = list.random()
                        flag = flag2
                        flagIndex = list.indexOf(flag)
                        dashList.clear()
                        letterList.clear()
                        wrongCount=3
                        pause=false
                        time = 10

                    }else{
                        //Checking if user input is in the letterList, if so assigning that to the relevant element of dash list
                        for (i in 0..<(letterList.size)){
                            if(userGuess == letterList[i].toString()){
                                dashList[i] = userGuess
                                Log.d(dashList.toString(),"updated list")
                            }
                        }

                        //Counting attempts
                        if(userGuess !in letterList.toString()){
                            wrongCount-=1
                        }

                        //if the attempts are finished or the answer is guessed, make "Next" button available
                        if ((wrongCount == 0) || "-" !in dashList){
                            pause=!pause
                            time = 10
                            buttonText = "Next"
                            correctAnswer = valueFromList
                            correctAnswerText+="Correct Answer is:"
                            if(wrongCount==0){
                                correction = "Wrong!"
                            }else{
                                correction = "Correct!"
                            }

                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(Color(235, 127, 0))
            ) {
                Text(text = buttonText)
            }

        }

        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ){
            Text(text = "You have $wrongCount chances left")
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
            //Text added separately to add blue color only to the correct country part
            Text(text= correctAnswerText)
            Text(text = correctAnswer,style = TextStyle(color = Color.Blue),
                fontWeight = FontWeight.Bold)
        }
    }
}

//Composable function created to make text fields for users
//https://developer.android.com/develop/ui/compose/text/user-input
@Composable
fun userGuesses(): String {
    var guessInput by rememberSaveable { mutableStateOf("") }

    TextField(
        value = guessInput,

        //https://stackoverflow.com/questions/67136058/textfield-maxlength-android-jetpack-compose
        onValueChange = {if (it.length <= 1) guessInput = it.lowercase() },
        label = { Text("Input Guess:", style = TextStyle(fontWeight = FontWeight.Bold)) },
        //https://www.kodeco.com/38708142-jetpack-compose-getting-started/lessons/13
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color(235, 127, 0),
            focusedContainerColor = Color(235, 127, 0).copy(alpha = 0.2f),
            focusedLabelColor = Color(235, 127, 0),
            unfocusedContainerColor = Color(240,240,240)
        )
    )
    return guessInput
}
