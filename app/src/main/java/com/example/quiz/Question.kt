package com.example.quiz

// simple class to store all the questions in
class Question(
    Question: String,
    Answer1: String,
    Answer2: String,
    Answer3: String,
    CorrectAnswer: Int
) {
    val answers: Array<String> = arrayOf(Answer1, Answer2, Answer3)
    val question = Question
    val correctAnswer = CorrectAnswer
}