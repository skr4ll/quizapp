package com.example.quiz

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em

import com.example.quiz.ui.theme.QuizTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuizTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // ref for layouts:
                    // https://developer.android.com/develop/ui/compose/layouts/basics
                    Box(modifier = Modifier.padding(innerPadding)) {
                        Column(
                            modifier = Modifier.padding(5.dp, 0.dp),
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(fontSize = 20.em, lineHeight = 1.em, text = "Quitz")
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    // ref: https://www.tutorialspoint.com/how-to-switch-between-different-activities-in-android-using-kotlin
                                    val intent = Intent(this@MainActivity, QuizActivity::class.java)
                                    startActivity(intent)
                                }) {
                                Text("Quiz starten")
                            }
                            Spacer(Modifier.size(1.dp))
                        }
                    }
                }
            }
        }
    }
}
