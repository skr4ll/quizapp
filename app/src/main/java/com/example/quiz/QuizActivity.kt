package com.example.quiz

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp // Ensure this import is present
import com.example.quiz.ui.theme.QuizTheme

// more about activities: https://developer.android.com/guide/components/activities/intro-activities
class QuizActivity : ComponentActivity() {
    // a list to save all the questions and answers
    // could be something ele, but ehhhh I am way to lazy
    // to *remove* an already asked question, we delete it from the list
    // ref about mutable lists: https://stackoverflow.com/questions/33278869/how-do-i-initialize-kotlins-mutablelist-to-empty-mutablelist
    var questions = arrayListOf<Question>(
        // https://www.youtube.com/watch?v=E58q1dTZa68
        Question(
            "Welches ist die Höchstgeschwindigkeit einer unbeladenen Schwalbe?",
            "Was sind Schwalben?",
            "Recht schnell für Vögel glaube ich",
            "Eine europäische oder eine afrikanische Schwalbe?",
            3
        ),

        Question(
            "Welches ist deine Lieblingsfarbe?",
            "Blau",
            "Gelb",
            "Blau ... nein, Gelb!",
            3
        ),

        Question(
            "Was ist die Hauptstadt von Australien?",
            "Sydney",
            "Melbourne",
            "Canberra",
            3
        ),
        Question("Wie viele Hauptstädte hat Südafrika?", "3", "2", "1", 1),

        Question(
            "Welche Option ist die Beste?",
            "Kotlin",
            "React",
            "Bier",
            3
        ),

        Question(
            "Ja juut woran hat et jelegen?",
            "Das fragt man sich nachher natürlich immer woran et jelegen hat",
            "Das ist natürlich immer so die Frage",
            "Woran hat et jelegen? Ähh",
            1    
        ),

        Question(
            "Ey Mark, wer ist der coolste hier?",
            "Du",
            "Du Nich",
            "Ich",
            2
        ),

        Question(
            "Gibt es hier eigentlich auch Bier?",
            "Geh selber kaufen, Büdchen ist ums Eck",
            "Na Freilich: feinstes Ötti, handwarm",
            "Nur falls du alle Fragen richtig beantwortet hast",
            2
        ),

        Question(
            "Döner wieder 3€, wann?",
            "Nur mit Zeitmaschine",
            "Inflation Brudi Inflation, dies das",
            "Dann nur noch homemade oder so",
            3
        ),

        Question(
            "Wie heißt die Hauptstadt von Assyrien?",
            "Assur",
            "Weiß ich nicht, da war ich noch nie",
            "Assyr",
            2
        )
        // TODO: Finish the needed 8+ more Questions
    )

    var gameRound = true
    var currentScore = 0

    // rounds ...
    // TODO: Set it up to 10 later
    val rounds = 10

    // a normal score that is increased when a question was answered correctly
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuizTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        QuizQuestion()
                    }
                }
            }
        }
    }

    // below is one of the worst and most ugly code I have ever written
    // with the exception of the one time I wrote a 6502 vm
    // i am terrible sorry for everyone who needs to read this code
    // i am sorry
    @Composable
    @Preview
    fun QuizQuestion() {
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp
        val screenHeight = configuration.screenHeightDp

        // Define viewport-relative sizes
        val fontSizeQuestion = (screenWidth * 0.05).sp // 5% of screen width in sp
        val fontSizeAnswer = (screenWidth * 0.04).sp // 4% of screen width in sp
        val buttonHeight = (screenHeight * 0.08).dp // 8% of screen height in dp
        val spacerHeight = (screenHeight * 0.1).dp // 10% of screen height as spacer

        var button1Colour = remember { mutableStateOf(Color.Gray) }
        var button2Colour = remember { mutableStateOf(Color.Gray) }
        var button3Colour = remember { mutableStateOf(Color.Gray) }
        var currentQuestion =
            remember { mutableStateOf(questions.removeAt((0 until questions.size).random())) }
        var answerGiven = remember { mutableIntStateOf(0) }
        var round = remember { mutableIntStateOf(0) }
        var buttonText = remember { mutableStateOf("Nächste Frage") }

        Column(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Add a spacer to move the content down
            Spacer(modifier = Modifier.height(spacerHeight))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = currentQuestion.value.question,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    fontSize = fontSizeQuestion, // Use calculated sp value
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = {
                        if (gameRound) {
                            gameRound = false
                            answerGiven.intValue = 1
                            if (answerGiven.intValue != currentQuestion.value.correctAnswer) {
                                button1Colour.value = Color.Red
                            } else {
                                button1Colour.value = Color.Green
                                currentScore++
                            }
                            if (currentQuestion.value.correctAnswer == 2) {
                                button2Colour.value = Color.Green
                            }
                            if (currentQuestion.value.correctAnswer == 3) {
                                button3Colour.value = Color.Green
                            }
                            round.intValue++

                            if (round.intValue == rounds) {
                                buttonText.value = "Hauptmenü"
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = button1Colour.value),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                        .height(buttonHeight)
                ) {
                    Text(
                        text = currentQuestion.value.answers[0],
                        fontSize = fontSizeAnswer
                    )
                }
                Button(
                    onClick = {
                        if (gameRound) {
                            gameRound = false
                            answerGiven.intValue = 2
                            if (answerGiven.intValue != currentQuestion.value.correctAnswer) {
                                button2Colour.value = Color.Red
                            } else {
                                button2Colour.value = Color.Green
                                currentScore++
                            }
                            if (currentQuestion.value.correctAnswer == 1) {
                                button1Colour.value = Color.Green
                            }
                            if (currentQuestion.value.correctAnswer == 3) {
                                button3Colour.value = Color.Green
                            }
                            round.intValue++

                            if (round.intValue == rounds) {
                                buttonText.value = "Hauptmenü"
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = button2Colour.value),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                        .height(buttonHeight)
                ) {
                    Text(
                        text = currentQuestion.value.answers[1],
                        fontSize = fontSizeAnswer
                    )
                }
                Button(
                    onClick = {
                        if (gameRound) {
                            gameRound = false
                            answerGiven.intValue = 3
                            if (answerGiven.intValue != currentQuestion.value.correctAnswer) {
                                button3Colour.value = Color.Red
                            } else {
                                button3Colour.value = Color.Green
                                currentScore++
                            }
                            if (currentQuestion.value.correctAnswer == 1) {
                                button1Colour.value = Color.Green
                            }
                            if (currentQuestion.value.correctAnswer == 2) {
                                button2Colour.value = Color.Green
                            }
                            round.intValue++

                            if (round.intValue == rounds) {
                                buttonText.value = "Hauptmenü"
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = button3Colour.value),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                        .height(buttonHeight)
                ) {
                    Text(
                        text = currentQuestion.value.answers[2],
                        fontSize = fontSizeAnswer
                    )
                }
            }
            Text(
                text = "Du hast $currentScore von $rounds Fragen richtig beantwortet.",
                textAlign = TextAlign.Center,
                fontSize = fontSizeAnswer,
                modifier = Modifier.padding(5.dp)
            )
            Spacer(Modifier.size(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        if (round.intValue < rounds) {
                            button1Colour.value = Color.Gray
                            button2Colour.value = Color.Gray
                            button3Colour.value = Color.Gray

                            gameRound = true
                            currentQuestion.value =
                                questions.removeAt((0 until questions.size).random())
                        } else {
                            val intent = Intent(this@QuizActivity, MainActivity::class.java)
                            startActivity(intent)
                        }
                    },
                    modifier = Modifier.height(buttonHeight) // Use viewport-relative button height
                ) {
                    Text(text = buttonText.value, fontSize = fontSizeAnswer)
                }
            }
        }
    }
}
