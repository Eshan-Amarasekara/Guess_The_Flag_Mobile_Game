package com.example.guesstheflag

import android.os.Bundle
import android.text.style.BackgroundColorSpan
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import androidx.compose.foundation.text.BasicTextField

import com.example.guesstheflag.ui.theme.GuessTheFlagTheme

class GuessHints : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GenerateRandomFlagForHints(CountryCodes)
        }
    }
}

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

//val countriesList2 = listOf(
//    "Andorra", "United Arab Emirates", "Afghanistan", "Antigua and Barbuda", "Anguilla", "Albania", "Armenia", "Angola", "Antarctica", "Argentina",
//    "American Samoa", "Austria", "Australia", "Aruba", "Åland Islands", "Azerbaijan", "Bosnia and Herzegovina", "Barbados", "Bangladesh", "Belgium",
//    "Burkina Faso", "Bulgaria", "Bahrain", "Burundi", "Benin", "Saint Barthélemy", "Bermuda", "Brunei Darussalam", "Bolivia, Plurinational State of",
//    "Caribbean Netherlands", "Brazil", "Bahamas", "Bhutan", "Bouvet Island", "Botswana", "Belarus", "Belize", "Canada", "Cocos (Keeling) Islands",
//    "Congo", "Central African Republic", "Republic of the Congo", "Switzerland", "Côte d'Ivoire", "Cook Islands", "Chile", "Cameroon", "China",
//    "Colombia", "Costa Rica", "Cuba", "Cape Verde", "Curaçao", "Christmas Island", "Cyprus", "Czech Republic", "Germany", "Djibouti", "Denmark",
//    "Dominica", "Algeria", "Ecuador", "Estonia", "Egypt", "Western Sahara", "Eritrea", "Spain", "Ethiopia", "Europe", "Finland", "Fiji", "Falkland Islands",
//    "Micronesia", "Faroe Islands", "France", "Gabon", "United Kingdom", "England", "Northern Ireland", "Scotland", "Wales", "Grenada", "Georgia",
//    "French Guiana", "Guernsey", "Ghana", "Gibraltar", "Greenland", "Gambia", "Guinea", "Guadeloupe", "Equatorial Guinea", "Greece",
//    "South Georgia and the South Sandwich Islands", "Guatemala", "Guam", "Guinea-Bissau", "Guyana", "Hong Kong", "Heard Island and McDonald Islands",
//    "Honduras", "Croatia", "Haiti", "Hungary", "Indonesia", "Ireland", "Israel", "Isle of Man", "India", "British Indian Ocean Territory", "Iraq",
//    "Iran, Islamic Republic of", "Iceland", "Italy", "Jersey", "Jamaica", "Jordan", "Japan", "Kenya", "Kyrgyzstan", "Cambodia", "Kiribati",
//    "Comoros", "Saint Kitts and Nevis", "Korea", "Korea, Republic of", "Kuwait", "Cayman Islands", "Kazakhstan", "Laos ", "Lebanon",
//    "Saint Lucia", "Liechtenstein", "Sri Lanka", "Liberia", "Lesotho", "Lithuania", "Luxembourg", "Latvia", "Libya", "Morocco", "Monaco",
//    "Moldova, Republic of", "Montenegro", "Saint Martin", "Madagascar", "Marshall Islands", "North Macedonia", "Mali", "Myanmar", "Mongolia",
//    "Macao", "Northern Mariana Islands", "Martinique", "Mauritania", "Montserrat", "Malta", "Mauritius", "Maldives", "Malawi", "Mexico",
//    "Malaysia", "Mozambique", "Namibia", "New Caledonia", "Niger", "Norfolk Island", "Nigeria", "Nicaragua", "Netherlands", "Norway", "Nepal",
//    "Nauru", "Niue", "New Zealand", "Oman", "Panama", "Peru", "French Polynesia", "Papua New Guinea", "Philippines", "Pakistan", "Poland",
//    "Saint Pierre and Miquelon", "Pitcairn", "Puerto Rico", "Palestine", "Portugal", "Palau", "Paraguay", "Qatar", "Réunion", "Romania", "Serbia",
//    "Russian Federation", "Rwanda", "Saudi Arabia", "Solomon Islands", "Seychelles", "Sudan", "Sweden", "Singapore", "Saint Helena, Ascension and Tristan da Cunha",
//    "Slovenia", "Svalbard and Jan Mayen Islands", "Slovakia", "Sierra Leone", "San Marino", "Senegal", "Somalia", "Suriname", "South Sudan",
//    "Sao Tome and Principe", "El Salvador", "Sint Maarten (Dutch part)", "Syrian Arab Republic", "Swaziland", "Turks and Caicos Islands", "Chad",
//    "French Southern Territories", "Togo", "Thailand", "Tajikistan", "Tokelau", "Timor-Leste", "Turkmenistan", "Tunisia", "Tonga", "Turkey",
//    "Trinidad and Tobago", "Tuvalu", "Taiwan (Republic of China)", "Tanzania, United Republic of", "Ukraine", "Uganda", "US Minor Outlying Islands",
//    "United States", "Uruguay", "Uzbekistan", "Holy See (Vatican City State)", "Saint Vincent and the Grenadines", "Venezuela, Bolivarian Republic of",
//    "Virgin Islands, British", "Virgin Islands, U.S.", "Vietnam", "Vanuatu", "Wallis and Futuna Islands", "Samoa", "Kosovo", "Yemen", "Mayotte",
//    "South Africa", "Zambia", "Zimbabwe"
//)

@Composable
fun GenerateRandomFlagForHints(list : List<Int>) {
    Column() {
        var correction by remember { mutableStateOf<String>("") }
        var buttonText by remember { mutableStateOf<String>("Submit") }
        var flag by remember { mutableIntStateOf(list.random()) }
        var flagIndex by remember { mutableIntStateOf(list.indexOf(flag)) }
        var correctAnswer by remember { mutableStateOf<String>("") }
        var correctAnswerText by remember { mutableStateOf<String>("") }
        var valueFromMap by remember { mutableStateOf<String>("") }
        var dashList by remember{ mutableStateOf( mutableListOf<String>()) }
        var letterList by remember { mutableStateOf( mutableListOf<Char>()) }
        var userGuess by remember { mutableStateOf("") }
        var wrongCount by remember { mutableStateOf<Int>(3) }




        Row(modifier= Modifier.align(Alignment.CenterHorizontally)) {
//            flagIndex = remember { list.indexOf(flag) }
            valueFromMap = countriesList.toList()[flagIndex]
            letterList = valueFromMap.lowercase().toMutableList()
            if(letterList.size != dashList.size) {
                for (letter in letterList) {
                    if (letter == ' ') {
                        dashList.add(' '.toString())
                    } else {
                        dashList.add('-'.toString())
                    }
                }
            }
            Log.d(flagIndex.toString(), "GenerateRandomFlag: ")
            Log.d(dashList.toString(),"Dash list")
            Log.d(dashList.size.toString(),"Dash list size")
            Log.d(letterList.size.toString(),"Letter list size")
            Log.d(letterList.toString(), "letter List")
            Log.d(valueFromMap,"Country from list")



            val imageModifier = Modifier
                .padding(20.dp)
                .border(BorderStroke(6.dp, Color.Black))
                .sizeIn(maxWidth = 300.dp, maxHeight = 300.dp)



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
            userGuess = userGuesses()

        }


        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ){

            Button(
                onClick = {
                    if (buttonText == "Next") {
                        correction = ""
                        buttonText = "Submit"
                        correctAnswer = ""
                        correctAnswerText = ""
                        valueFromMap=""
                        val flag2 = list.random()
                        flag = flag2
                        flagIndex = list.indexOf(flag)
                        dashList.clear()
                        letterList.clear()
                        wrongCount=3

                    }else{
                        for (i in 0..<(letterList.size)){
                            if(userGuess == letterList[i].toString()){
                                dashList[i] = userGuess
                                Log.d(dashList.toString(),"updated list")

                            }
                        }

                        if(userGuess !in letterList.toString()){
                            wrongCount-=1
                        }


                        if ((wrongCount == 0) || "-" !in dashList){
                            buttonText = "Next"
                            correctAnswer = valueFromMap
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
            Text(text = "You have $wrongCount chances left to answer wrong")
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
            //Text added separately to add blue color only to the correct country part
            Text(text= correctAnswerText)
            Text(text = correctAnswer,style = TextStyle(color = Color.Blue),
                fontWeight = FontWeight.Bold)
        }
    }
}

//https://developer.android.com/develop/ui/compose/text/user-input
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun userGuesses(): String {
    var guessInput by remember { mutableStateOf("") }

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
