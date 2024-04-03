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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

class AdvancedLevel : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //getting the data passed from the MainActivity
            var timer = intent.getBooleanExtra("timer", false)
            GenerateRandomFlags(CountryCodes,timer)
        }
    }
}

@Composable
fun GenerateRandomFlags(list :List<Int>,timer: Boolean) {
    var flag by rememberSaveable { mutableIntStateOf(list.random()) }                                       //getting the first random flag
    var flagIndex by rememberSaveable { mutableIntStateOf(list.indexOf(flag)) }                             //Getting the index of the first flag
    var valueFromList by rememberSaveable { mutableStateOf<String>("") }                              //Variable created to get String value of country name
    valueFromList = countriesList.toList()[flagIndex]                                                       //Getting String value of country name
    var flagList by rememberSaveable { mutableStateOf(mutableListOf<Int>()) }                               //Variable to hold the 3 random flags
    var correction by rememberSaveable { mutableStateOf<String>("") }                                 //Variable used to check the correction of the answer
    var random2 by rememberSaveable { mutableStateOf(list.filter { it != flag }.random()) }                 //Getting 2nd random flag (checked if it's same as the 1st flag)
    var random3 by rememberSaveable { mutableStateOf(list.filter { it != flag && it != random2 }.random()) }//Getting 3rd random flag (checked if it's same as the 1st and 2nd flags)
    var isShuffled by rememberSaveable { mutableStateOf(false) }                                      //Boolean value to check if it's shuffled
    var guessInput1 by rememberSaveable { mutableStateOf("") }                                        //Variable used to store first user input
    var guessInput2 by rememberSaveable { mutableStateOf("") }                                        //Variable used to store second user input
    var guessInput3 by rememberSaveable { mutableStateOf("") }                                        //Variable used to store third user input
    var buttonText by rememberSaveable { mutableStateOf<String>("Submit") }                           //Text for button, initiated with "Submit"
    var is1Correct by rememberSaveable { mutableStateOf<Boolean?>(null) }                             //Boolean to check if 1st user input is correct
    var is2Correct by rememberSaveable { mutableStateOf<Boolean?>(null) }                             //Boolean to check if 2nd user input is correct
    var is3Correct by rememberSaveable { mutableStateOf<Boolean?>(null) }                             //Boolean to check if 3rd user input is correct
    var tries by rememberSaveable { mutableStateOf<Int>(3) }                                          //Try count for attempts

    //3 different text field colors that changes relevant to condition
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

    //variables to check if the text fields should be enabled or not
    var is1Enabled by rememberSaveable { mutableStateOf(true) }
    var is2Enabled by rememberSaveable { mutableStateOf(true) }
    var is3Enabled by rememberSaveable { mutableStateOf(true) }

    //3 different variables to set "Correct Country is:" so that it can be applied only for wrong answers given by user
    var correctAnswer1Text by rememberSaveable { mutableStateOf<String>("") }
    var correctAnswer2Text by rememberSaveable { mutableStateOf<String>("") }
    var correctAnswer3Text by rememberSaveable { mutableStateOf<String>("") }

    //3 different variables to set country so that it can be applied only for wrong answers given by user
    var correctAnswer1Country by rememberSaveable { mutableStateOf<String>("") }
    var correctAnswer2Country by rememberSaveable { mutableStateOf<String>("") }
    var correctAnswer3Country by rememberSaveable { mutableStateOf<String>("") }

    //variable to keep score
    var score by rememberSaveable { mutableStateOf<Int>(0)}

    //Adding the generated flags to flaglist
    flagList.add(flag)
    flagList.add(random2)
    flagList.add(random3)


    //Randomizing the list and assigning them to variables
    //https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/shuffle.html
    if (isShuffled == false) {
        flagList.shuffle()
        isShuffled = true
    }

    //assigning flags from the list to variables
    var flag1 by rememberSaveable { mutableStateOf(flagList[0]) }
    var flag2 by rememberSaveable { mutableStateOf(flagList[1]) }
    var flag3 by rememberSaveable { mutableStateOf(flagList[2]) }

    //time variable with 10 initial to start the timer at 10
    var time by rememberSaveable { mutableStateOf(10)}
    //Boolean variable to check if the timer should pause
    var pause by rememberSaveable { mutableStateOf(false) }


    //https://stackoverflow.com/questions/68164883/how-do-i-create-a-jetpack-compose-column-where-a-middle-child-is-scrollable-but
    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())) {


        Row(modifier = Modifier.fillMaxWidth()
            , horizontalArrangement = Arrangement.Absolute.Right
        ) {
            //Showing score
            Text(
                text = "Score: $score/3",
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp
            )
        }

        Row {
            //Timer:
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
                pause = !pause
                if (countriesList.toList()[(list.indexOf(flag1))] == guessInput1) {
                    is1Correct = true
                    if(is1Enabled){
                        score+=1
                    }
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
                    if(is2Enabled){
                        score+=1
                    }
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
                    if(is3Enabled){
                        score+=1
                    }
                    is3Enabled=false


                } else {
                    is3Correct = false
                }
                textField3Color =(when (is3Correct) {
                    true -> Color.Green
                    false -> Color.Red
                    null -> Color.White
                })

                correction = "Wrong!"
                buttonText = "Next"
                correctAnswer1Text= "Correct Answer is: "
                correctAnswer1Country = countriesList.toList()[(list.indexOf(flag1))]
                correctAnswer2Text= "Correct Answer is: "
                correctAnswer2Country = countriesList.toList()[(list.indexOf(flag2))]
                correctAnswer3Text= "Correct Answer is: "
                correctAnswer3Country = countriesList.toList()[(list.indexOf(flag3))]

            }
            if(timer) {
                Text(
                    text = "Time left: $time",
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))


        Row{
            //Adding customised properties for image separately and assigning to a variable
            val imageModifier = Modifier
                .padding(10.dp)
                .border(BorderStroke(6.dp, Color.Black))
                .width(200.dp)
                .height(150.dp)
                .fillMaxSize()

            //Rendering image
            Image(
                painter = painterResource(id = flag1),
                contentDescription = " ",
                contentScale = ContentScale.FillBounds,
                modifier = imageModifier

            )

            //Logs to get generated flag and users input
            Log.d(countriesList.toList()[(list.indexOf(flag1))],"Flag1")
            Log.d(guessInput1,"flag1 input")


            //Text field for user input
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
        Row{
            //Displaying correct country name if user input is incorrect
            if (is1Enabled) {
                Text(text = correctAnswer1Text)
                Text(text = correctAnswer1Country, style = TextStyle(color = Color.Blue),
                    fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row{
            //Adding customised properties for image separately and assigning to a variable
            val imageModifier = Modifier
                .padding(10.dp)
                .border(BorderStroke(6.dp, Color.Black))
                .width(200.dp)
                .height(150.dp)
                .fillMaxSize()

            //Rendering image
            Image(
                painter = painterResource(id = flag2),
                contentDescription = " ",
                contentScale = ContentScale.FillBounds,
                modifier = imageModifier

            )

            //Logs to get generated flag and users input
            Log.d(countriesList.toList()[(list.indexOf(flag2))],"Flag2")
            Log.d(guessInput2,"flag2 input")

            //Text field for user inputs
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
        Row{
            //Displaying correct country name if user input is incorrect
            if (is2Enabled) {
                Text(text = correctAnswer2Text)
                Text(text = correctAnswer2Country, style = TextStyle(color = Color.Blue),
                    fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        //Adding customised properties for image separately and assigning to a variable
        Row{
            val imageModifier = Modifier
                .padding(10.dp)
                .border(BorderStroke(6.dp, Color.Black))
                .width(200.dp)
                .height(150.dp)
                .fillMaxSize()

            //Rendering Image
            Image(
                painter = painterResource(id = flag3),
                contentDescription = " ",
                contentScale = ContentScale.FillBounds,
                modifier = imageModifier
            )

            //Logs to get generated flag and users input
            Log.d(countriesList.toList()[(list.indexOf(flag3))],"Flag3")
            Log.d(guessInput3,"flag3 input")

            //Text field for user input
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
        Row{
            //Displaying correct country name if user input is incorrect
            if(is3Enabled){
                Text(text = correctAnswer3Text)
                Text(text = correctAnswer3Country, style = TextStyle(color = Color.Blue),
                    fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        //Row to display attempts
        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ){
            Text(text = "You have $tries chances left")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ){
            //Resetting all variables when "Next" is clicked
            Button(onClick = {
                if (buttonText == "Next") {
                    time = 10
                    score = 0
                    pause = false
                    correctAnswer1Text=""
                    correctAnswer2Text=""
                    correctAnswer3Text=""
                    correctAnswer1Country=""
                    correctAnswer2Country=""
                    correctAnswer3Country=""
                    flag = list.random()
                    flagIndex =(list.indexOf(flag))
                    valueFromList =""
                    valueFromList = countriesList.toList()[flagIndex]
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

                    //validation of answers done when submit button is clicked:
                } else {
                    pause = !pause

                    if (countriesList.toList()[(list.indexOf(flag1))] == guessInput1) {
                        is1Correct = true
                        if(is1Enabled){
                            score+=1
                        }
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
                        if(is2Enabled){
                            score+=1
                        }
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
                        if(is3Enabled){
                            score+=1
                        }
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
                        correctAnswer1Text= "Correct Answer is: "
                        correctAnswer1Country = countriesList.toList()[(list.indexOf(flag1))]
                        correctAnswer2Text= "Correct Answer is: "
                        correctAnswer2Country = countriesList.toList()[(list.indexOf(flag2))]
                        correctAnswer3Text= "Correct Answer is: "
                        correctAnswer3Country = countriesList.toList()[(list.indexOf(flag3))]
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
    }
}
