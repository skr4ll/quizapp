package com.example.quiz

import kotlin.random.Random
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composer.Companion.Empty
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.quiz.ui.theme.QuizTheme

// more about activities: https://developer.android.com/guide/components/activities/intro-activities
class QuizActivity : ComponentActivity() {
    // a list to save all the questions and answers
    // could be something ele, but ehhhh I am way to lazy
    // to *remove* an already asked question, we delete it from the list
    // ref about mutable lists: https://stackoverflow.com/questions/33278869/how-do-i-initialize-kotlins-mutablelist-to-empty-mutablelist
    var questions = arrayListOf<Question>(
        Question(
            "Was ist die Hauptstadt von Australien?",
            "Sydney",
            "Melbourne",
            "Canberra",
            3
        ),
        Question("Wie viele Hauptstädte hat Südafrika?", "3", "2", "1", 1)
    )

    var gameRound = true
    var currentScore = 0

    // rounds ...
    var round = 1
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
        var button1Colour = remember { mutableStateOf(Color.Unspecified) }
        var button2Colour = remember { mutableStateOf(Color.Unspecified) }
        var button3Colour = remember { mutableStateOf(Color.Unspecified) }
        // refs for getting random list item:
        // https://stackoverflow.com/questions/47850156/get-a-random-item-from-list-using-kotlin-streams
        // https://developermemos.com/posts/random-list-item-kotlin
        // https://www.techiedelight.com/randomly-select-an-item-from-a-list-in-kotlin/
        // https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-mutable-list/
        // https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-mutable-list/remove-at.html
        var currentQuestion =
            remember { mutableStateOf(questions.removeAt((0 until questions.size).random())) }
        var answerGiven = remember { mutableIntStateOf(0) }

        // ref for color of buttons: https://stackoverflow.com/questions/64376333/background-color-on-button-in-jetpack-compose
        Column(
            modifier = Modifier
                .padding(5.dp, 0.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(fontSize = 20.em, lineHeight = 1.em, text = "Quitz")
            Column {
                Text(
                    text = currentQuestion.value.question,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp),
                    fontSize = 10.em,
                    lineHeight = 1.em,
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
                                button2Colour.value = Color.Green
                            }
                            round++
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = button1Colour.value),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = currentQuestion.value.answers[0]
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
                                button2Colour.value = Color.Green
                            }
                            round++
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = button2Colour.value),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = currentQuestion.value.answers[1]
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
                            round++
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = button3Colour.value),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = currentQuestion.value.answers[2]
                    )
                }
            }
            Text(text = "Played $round of $rounds rounds and won $currentScore")
            Spacer(Modifier.size(0.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = {
                    if (round <= rounds) {
                        button1Colour.value = Color.Unspecified
                        button2Colour.value = Color.Unspecified
                        button3Colour.value = Color.Unspecified
                        gameRound = true
                        currentQuestion.value =
                            questions.removeAt((0 until questions.size).random())
                    }
                }) { Text(text = "Next Question") }
            }
        }
    }
}
