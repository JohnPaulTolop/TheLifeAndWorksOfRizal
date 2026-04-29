package com.jp.thelifeandworksofrizal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.text.font.FontWeight
import com.jp.thelifeandworksofrizal.Chapter
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.LazyColumn


@Composable
fun ChapterDetailScreen(chapter: Chapter) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F6FA))
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = chapter.title,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF1E3A8A)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = chapter.subtitle,
                fontSize = 16.sp,
                color = Color(0xFF64748B)
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        item {
            chapter.content.split("\n\n").forEach { paragraph ->
                Text(
                    text = paragraph,
                    fontSize = 15.sp,
                    color = Color(0xFF111827),
                    lineHeight = 24.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}