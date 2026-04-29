package com.jp.thelifeandworksofrizal

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
@Composable
fun QuizScreen(
    quiz: ChapterQuiz,
    onBackToChapters: () -> Unit
) {
    var currentIndex by remember { mutableIntStateOf(0) }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var score by remember { mutableIntStateOf(0) }
    var finished by remember { mutableStateOf(false) }

    if (finished) {
        QuizResultScreen(
            chapterTitle = quiz.chapterTitle,
            score = score,
            total = quiz.questions.size,
            onRetry = {
                currentIndex = 0
                selectedAnswer = null
                score = 0
                finished = false
            },
            onBackToChapters = onBackToChapters
        )
        return
    }

    val currentQuestion = quiz.questions[currentIndex]

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FB))
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = quiz.chapterTitle,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF111827),
                        lineHeight = 28.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Question ${currentIndex + 1} of ${quiz.questions.size}",
                        fontSize = 14.sp,
                        color = Color(0xFF6B7280)
                    )
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = currentQuestion.question,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E293B),
                        lineHeight = 28.sp
                    )
                }
            }
        }

        items(currentQuestion.options.size) { optionIndex ->
            val option = currentQuestion.options[optionIndex]
            QuizOptionCard(
                text = option,
                selected = selectedAnswer == option,
                onClick = { selectedAnswer = option }
            )
        }

        item {
            Spacer(modifier = Modifier.height(4.dp))

            Button(
                onClick = {
                    if (selectedAnswer == currentQuestion.correctAnswer) {
                        score++
                    }

                    if (currentIndex == quiz.questions.lastIndex) {
                        finished = true
                    } else {
                        currentIndex++
                        selectedAnswer = null
                    }
                },
                enabled = selectedAnswer != null,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (currentIndex == quiz.questions.lastIndex) "Finish Quiz" else "Next Question",
                    modifier = Modifier.padding(vertical = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                onClick = onBackToChapters,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back to Chapters")
            }
        }
    }
}

@Composable
private fun QuizOptionCard(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (selected) Color(0xFFDBEAFE) else Color.White
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if (selected) Color(0xFF2563EB) else Color(0xFFE5E7EB)
        )
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(18.dp),
            fontSize = 15.sp,
            color = Color(0xFF111827),
            lineHeight = 22.sp
        )
    }
}

@Composable
private fun QuizResultScreen(
    chapterTitle: String,
    score: Int,
    total: Int,
    onRetry: () -> Unit,
    onBackToChapters: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FB))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Quiz Finished",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF111827)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = chapterTitle,
                    fontSize = 16.sp,
                    color = Color(0xFF4B5563),
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "Your Score: $score / $total",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1D4ED8)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = onRetry,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Try Again")
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedButton(
                    onClick = onBackToChapters,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Back to Chapters")
                }
            }
        }
    }
}