package com.example.guesstheflag

//Optimized Imports
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

class GuessTheCountry : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            //getting the data passed from the MainActivity
            var timer = intent.getBooleanExtra("timer", false)
            GenerateRandomFlag(CountryCodes,timer)

        }
    }
}

//Function to Generate Random Flag from list

@Composable
fun GenerateRandomFlag(list : List<Int>, timer:Boolean) {
    //https://stackoverflow.com/questions/68164883/how-do-i-create-a-jetpack-compose-column-where-a-middle-child-is-scrollable-but
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        var selectedCountry by rememberSaveable { mutableStateOf<String?>(null) }             //Variable to hold user selected country
        var correction by rememberSaveable { mutableStateOf<String>("") }                     //Variable used to check the correction of the answer
        var buttonText by rememberSaveable { mutableStateOf<String>("Submit") }               //Text for button, initiated with "Submit"
        var flag by rememberSaveable { mutableIntStateOf(list.random()) }                           //Generating random flag from list
        var flagIndex by rememberSaveable { mutableIntStateOf(list.indexOf(flag)) }                 //Getting index of flag
        var correctAnswer by rememberSaveable { mutableStateOf<String>("") }                  //Variable to hold correct answer
        var correctAnswerText by rememberSaveable { mutableStateOf<String>("") }              //Variable to hold the text before the answer
        var valueFromList by rememberSaveable { mutableStateOf<String>("") }                  //Variable used to hold name of correct country
        var time by rememberSaveable { mutableStateOf(10)}                                    //time variable with 10 initial to start the timer at 10
        var pause by rememberSaveable { mutableStateOf(false) }                               //Boolean variable to check if the timer should pause
        var isEnabled by rememberSaveable { mutableStateOf(true) }                            //Boolean variable to make list items disabled or enabled

        //Getting String name of correct flag
        valueFromList = countriesList.toList()[flagIndex]


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
                pause=!pause
                time = 10
                if (valueFromList == selectedCountry) {
                    correction = "Correct!"
                } else {
                    correction = "Wrong!"
                }
                buttonText = "Next"
                correctAnswer = valueFromList
                correctAnswerText += "Correct Answer is:"
                isEnabled = false
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
            //Image generation
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {

                //Log to get flag index
                Log.d(flagIndex.toString(), "GenerateRandomFlag: ")

                //Adding customised properties for image separately and assigning to a variable
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

            //Lazy column used to display the list of countries to choose
            Row {
                LazyColumn(
                    modifier = Modifier
                        .height(300.dp)
//                    .width(600.dp)
                        .padding(10.dp)
                ) {
                    //Generating answers by mapping values into clickable boxes
                    items(countriesList) { countryName ->
                        val isSelected = countryName == selectedCountry
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    if (isSelected) Color(235, 127, 0) else Color.White,
                                    shape = RoundedCornerShape(18.dp) // Setting border radius
                                )
                                //making boxes clickable
                                .clickable(
                                    enabled = isEnabled,
                                    onClick = { selectedCountry = countryName })
                                .border(
                                    BorderStroke(4.dp, Color(235, 127, 0)),
                                    shape = RoundedCornerShape(18.dp)
                                ),

                            ) {
                            Text(
                                text = " $countryName",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Left,
                                color = if (isSelected) Color.White else Color.Black, // Changing Colors if Selected
                                modifier = Modifier
                                    .fillMaxWidth() // Ensures that text does not overflow horizontally
                                    .padding(horizontal = 16.dp) // Adding padding to prevent overflow
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp)) // Add space between boxes
                    }
                }


            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {

                Button(
                    onClick = {
                        //Resetting all variables when "Next" is clicked
                        if (buttonText == "Next") {
                            selectedCountry = null
                            correction = ""
                            buttonText = "Submit"
                            correctAnswer = ""
                            correctAnswerText = ""
                            valueFromList = ""
                            val flag2 = list.random()
                            flag = flag2
                            flagIndex = list.indexOf(flag)
                            time=10
                            pause=false
                            isEnabled = true
                        } else {
                            //checking answer when "Submit" is clicked
                            pause=!pause
                            if (valueFromList == selectedCountry) {
                                correction = "Correct!"
                            } else {
                                correction = "Wrong!"
                            }
                            buttonText = "Next"
                            correctAnswer = valueFromList
                            correctAnswerText += "Correct Answer is:"
                            isEnabled = false

                        }
                    },
                    colors = ButtonDefaults.buttonColors(Color(235, 127, 0))
                ) {
                    Text(text = buttonText)
                }

            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                //checking what correction value is and displaying accordingly
                if (correction == "Correct!") {
                    Text(
                        text = correction,
                        style = TextStyle(color = Color.Green, fontStyle = FontStyle.Italic),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                    )
                } else if (correction == "Wrong!") {
                    Text(
                        text = correction,
                        style = TextStyle(color = Color.Red, fontStyle = FontStyle.Italic),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }

            Row {
                //Text added separately to add blue color only to the correct country part
                Text(text = correctAnswerText)
                Text(text = correctAnswer, style = TextStyle(color = Color.Blue))
            }
        }
    }




//@Preview

//Country image name list with rendering code snippet

    val CountryCodes = listOf(
        R.drawable.ad, R.drawable.ae, R.drawable.af, R.drawable.ag, R.drawable.ai, R.drawable.al, R.drawable.am, R.drawable.ao, R.drawable.aq, R.drawable.ar,
        R.drawable.`as`, R.drawable.at, R.drawable.au, R.drawable.aw, R.drawable.ax, R.drawable.az, R.drawable.ba, R.drawable.bb, R.drawable.bd, R.drawable.be,
        R.drawable.bf, R.drawable.bg, R.drawable.bh, R.drawable.bi, R.drawable.bj, R.drawable.bl, R.drawable.bm, R.drawable.bn, R.drawable.bo, R.drawable.bq,
        R.drawable.br, R.drawable.bs, R.drawable.bt, R.drawable.bv, R.drawable.bw, R.drawable.by, R.drawable.bz, R.drawable.ca, R.drawable.cc, R.drawable.cd,
        R.drawable.cf, R.drawable.cg, R.drawable.ch, R.drawable.ci, R.drawable.ck, R.drawable.cl, R.drawable.cm, R.drawable.cn, R.drawable.co, R.drawable.cr,
        R.drawable.cu, R.drawable.cv, R.drawable.cw, R.drawable.cx, R.drawable.cy, R.drawable.cz, R.drawable.de, R.drawable.dj, R.drawable.dk, R.drawable.dm,
        R.drawable.dz, R.drawable.ec, R.drawable.ee, R.drawable.eg, R.drawable.eh, R.drawable.er, R.drawable.es, R.drawable.et, R.drawable.eu, R.drawable.fi,
        R.drawable.fj, R.drawable.fk, R.drawable.fm, R.drawable.fo, R.drawable.fr, R.drawable.ga, R.drawable.gb, R.drawable.gbeng, R.drawable.gbnir,
        R.drawable.gbsct, R.drawable.gbwls, R.drawable.gd, R.drawable.ge, R.drawable.gf, R.drawable.gg, R.drawable.gh, R.drawable.gi, R.drawable.gl,
        R.drawable.gm, R.drawable.gn, R.drawable.gp, R.drawable.gq, R.drawable.gr, R.drawable.gs, R.drawable.gt, R.drawable.gu, R.drawable.gw, R.drawable.gy,
        R.drawable.hk, R.drawable.hm, R.drawable.hn, R.drawable.hr, R.drawable.ht, R.drawable.hu, R.drawable.id, R.drawable.ie, R.drawable.il, R.drawable.im,
        R.drawable.`in`, R.drawable.io, R.drawable.iq, R.drawable.ir, R.drawable.`is`, R.drawable.it, R.drawable.je, R.drawable.jm, R.drawable.jo, R.drawable.jp,
        R.drawable.ke, R.drawable.kg, R.drawable.kh, R.drawable.ki, R.drawable.km, R.drawable.kn, R.drawable.kp, R.drawable.kr, R.drawable.kw, R.drawable.ky,
        R.drawable.kz, R.drawable.la, R.drawable.lb, R.drawable.lc, R.drawable.li, R.drawable.lk, R.drawable.lr, R.drawable.ls, R.drawable.lt, R.drawable.lu,
        R.drawable.lv, R.drawable.ly, R.drawable.ma, R.drawable.mc, R.drawable.md, R.drawable.me, R.drawable.mf, R.drawable.mg, R.drawable.mh, R.drawable.mk,
        R.drawable.ml, R.drawable.mm, R.drawable.mn, R.drawable.mo, R.drawable.mp, R.drawable.mq, R.drawable.mr, R.drawable.ms, R.drawable.mt, R.drawable.mu,
        R.drawable.mv, R.drawable.mw, R.drawable.mx, R.drawable.my, R.drawable.mz, R.drawable.na, R.drawable.nc, R.drawable.ne, R.drawable.nf, R.drawable.ng,
        R.drawable.ni, R.drawable.nl, R.drawable.no, R.drawable.np, R.drawable.nr, R.drawable.nu, R.drawable.nz, R.drawable.om, R.drawable.pa, R.drawable.pe,
        R.drawable.pf, R.drawable.pg, R.drawable.ph, R.drawable.pk, R.drawable.pl, R.drawable.pm, R.drawable.pn, R.drawable.pr, R.drawable.ps, R.drawable.pt,
        R.drawable.pw, R.drawable.py, R.drawable.qa, R.drawable.re, R.drawable.ro, R.drawable.rs, R.drawable.ru, R.drawable.rw, R.drawable.sa, R.drawable.sb,
        R.drawable.sc, R.drawable.sd, R.drawable.se, R.drawable.sg, R.drawable.sh, R.drawable.si, R.drawable.sj, R.drawable.sk, R.drawable.sl, R.drawable.sm,
        R.drawable.sn, R.drawable.so, R.drawable.sr, R.drawable.ss, R.drawable.st, R.drawable.sv, R.drawable.sx, R.drawable.sy, R.drawable.sz, R.drawable.tc,
        R.drawable.td, R.drawable.tf, R.drawable.tg, R.drawable.th, R.drawable.tj, R.drawable.tk, R.drawable.tl, R.drawable.tm, R.drawable.tn, R.drawable.to,
        R.drawable.tr, R.drawable.tt, R.drawable.tv, R.drawable.tw, R.drawable.tz, R.drawable.ua, R.drawable.ug, R.drawable.um, R.drawable.us, R.drawable.uy,
        R.drawable.uz, R.drawable.va, R.drawable.vc, R.drawable.ve, R.drawable.vg, R.drawable.vi, R.drawable.vn, R.drawable.vu, R.drawable.wf, R.drawable.ws,
        R.drawable.xk, R.drawable.ye, R.drawable.yt, R.drawable.za, R.drawable.zm, R.drawable.zw
    )




val countriesList = listOf(
    "Andorra", "United Arab Emirates", "Afghanistan", "Antigua and Barbuda", "Anguilla", "Albania", "Armenia", "Angola", "Antarctica", "Argentina",
    "American Samoa", "Austria", "Australia", "Aruba", "Åland Islands", "Azerbaijan", "Bosnia and Herzegovina", "Barbados", "Bangladesh", "Belgium",
    "Burkina Faso", "Bulgaria", "Bahrain", "Burundi", "Benin", "Saint Barthélemy", "Bermuda", "Brunei Darussalam", "Bolivia, Plurinational State of",
    "Caribbean Netherlands", "Brazil", "Bahamas", "Bhutan", "Bouvet Island", "Botswana", "Belarus", "Belize", "Canada", "Cocos (Keeling) Islands",
    "Congo", "Central African Republic", "Republic of the Congo", "Switzerland", "Côte d'Ivoire", "Cook Islands", "Chile", "Cameroon", "China",
    "Colombia", "Costa Rica", "Cuba", "Cape Verde", "Curaçao", "Christmas Island", "Cyprus", "Czech Republic", "Germany", "Djibouti", "Denmark",
    "Dominica", "Algeria", "Ecuador", "Estonia", "Egypt", "Western Sahara", "Eritrea", "Spain", "Ethiopia", "Europe", "Finland", "Fiji", "Falkland Islands",
    "Micronesia", "Faroe Islands", "France", "Gabon", "United Kingdom", "England", "Northern Ireland", "Scotland", "Wales", "Grenada", "Georgia",
    "French Guiana", "Guernsey", "Ghana", "Gibraltar", "Greenland", "Gambia", "Guinea", "Guadeloupe", "Equatorial Guinea", "Greece",
    "South Georgia and the South Sandwich Islands", "Guatemala", "Guam", "Guinea-Bissau", "Guyana", "Hong Kong", "Heard Island and McDonald Islands",
    "Honduras", "Croatia", "Haiti", "Hungary", "Indonesia", "Ireland", "Israel", "Isle of Man", "India", "British Indian Ocean Territory", "Iraq",
    "Iran, Islamic Republic of", "Iceland", "Italy", "Jersey", "Jamaica", "Jordan", "Japan", "Kenya", "Kyrgyzstan", "Cambodia", "Kiribati",
    "Comoros", "Saint Kitts and Nevis", "Korea", "Korea, Republic of", "Kuwait", "Cayman Islands", "Kazakhstan", "Laos ", "Lebanon",
    "Saint Lucia", "Liechtenstein", "Sri Lanka", "Liberia", "Lesotho", "Lithuania", "Luxembourg", "Latvia", "Libya", "Morocco", "Monaco",
    "Moldova, Republic of", "Montenegro", "Saint Martin", "Madagascar", "Marshall Islands", "North Macedonia", "Mali", "Myanmar", "Mongolia",
    "Macao", "Northern Mariana Islands", "Martinique", "Mauritania", "Montserrat", "Malta", "Mauritius", "Maldives", "Malawi", "Mexico",
    "Malaysia", "Mozambique", "Namibia", "New Caledonia", "Niger", "Norfolk Island", "Nigeria", "Nicaragua", "Netherlands", "Norway", "Nepal",
    "Nauru", "Niue", "New Zealand", "Oman", "Panama", "Peru", "French Polynesia", "Papua New Guinea", "Philippines", "Pakistan", "Poland",
    "Saint Pierre and Miquelon", "Pitcairn", "Puerto Rico", "Palestine", "Portugal", "Palau", "Paraguay", "Qatar", "Réunion", "Romania", "Serbia",
    "Russian Federation", "Rwanda", "Saudi Arabia", "Solomon Islands", "Seychelles", "Sudan", "Sweden", "Singapore", "Saint Helena, Ascension and Tristan da Cunha",
    "Slovenia", "Svalbard and Jan Mayen Islands", "Slovakia", "Sierra Leone", "San Marino", "Senegal", "Somalia", "Suriname", "South Sudan",
    "Sao Tome and Principe", "El Salvador", "Sint Maarten (Dutch part)", "Syrian Arab Republic", "Swaziland", "Turks and Caicos Islands", "Chad",
    "French Southern Territories", "Togo", "Thailand", "Tajikistan", "Tokelau", "Timor-Leste", "Turkmenistan", "Tunisia", "Tonga", "Turkey",
    "Trinidad and Tobago", "Tuvalu", "Taiwan (Republic of China)", "Tanzania, United Republic of", "Ukraine", "Uganda", "US Minor Outlying Islands",
    "United States", "Uruguay", "Uzbekistan", "Holy See (Vatican City State)", "Saint Vincent and the Grenadines", "Venezuela, Bolivarian Republic of",
    "Virgin Islands, British", "Virgin Islands, U.S.", "Vietnam", "Vanuatu", "Wallis and Futuna Islands", "Samoa", "Kosovo", "Yemen", "Mayotte",
    "South Africa", "Zambia", "Zimbabwe"
)


//@Composable

//fun countryInit(): String?{
//
//    return selectedCountry
//}

//@Composable
//List of Countries for the Answers List
//fun AnswerList(){
//
//
//}

//@Composable
//fun CheckAnswer(countryName: String){
//    MyImageComponent(flag)
//}

