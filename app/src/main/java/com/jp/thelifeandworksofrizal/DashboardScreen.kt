package com.jp.thelifeandworksofrizal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.LazyColumn


@Composable
fun DashboardScreen(
    onStoryClick: () -> Unit,
    onQuizClick: () -> Unit,
    onReportClick: () -> Unit,
    onMembersClick: () -> Unit,
    onAiAssistantClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F6FA))
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Rizal Learning",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF1E293B)
                )
                Text(
                    text = "Explore stories, quizzes, and reports",
                    fontSize = 14.sp,
                    color = Color(0xFF64748B)
                )
            }

            // Profile avatar placeholder
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )
        }

        // Feature grid
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            DashboardFeatureCard(
                title = "Story",
                subtitle = "Read life stories and works of Rizal",
                icon = Icons.Rounded.MenuBook,
                accent = Color(0xFF60A5FA),
                onClick = onStoryClick,
                modifier = Modifier.weight(1f)
            )
            DashboardFeatureCard(
                title = "Quiz",
                subtitle = "Test your knowledge with fun quizzes",
                icon = Icons.Rounded.Create,
                accent = Color(0xFF22C55E),
                onClick = onQuizClick,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            DashboardFeatureCard(
                title = "Reports",
                subtitle = "View your activity and learning reports",
                icon = Icons.Rounded.Article,
                accent = Color(0xFFF59E0B),
                onClick = onReportClick,
                modifier = Modifier.weight(1f)
            )
            DashboardFeatureCard(
                title = "Members",
                subtitle = "See classmates and class activities",
                icon = Icons.Rounded.Groups,
                accent = Color(0xFFF472B6),
                onClick = onMembersClick,
                modifier = Modifier.weight(1f)
            )
        }

        DashboardFeatureCard(
            title = "AI Assistant",
            subtitle = "Ask questions and get help about Rizal anytime",
            icon = Icons.Rounded.AutoAwesome,
            accent = Color(0xFFA78BFA),
            onClick = onAiAssistantClick,
            modifier = Modifier.fillMaxWidth()
        )

        // Continue Learning card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Continue Learning",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF1E293B)
                )
                Text(
                    text = "Pick up where you left off",
                    fontSize = 12.sp,
                    color = Color(0xFF64748B)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF60A5FA)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.MenuBook,
                            contentDescription = "Book",
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Noli Me Tángere",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color(0xFF1E293B)
                        )
                        Text(
                            text = "Chapter 8: The Conspiracy",
                            fontSize = 12.sp,
                            color = Color(0xFF64748B)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        LinearProgressIndicator(
                            progress = 0.62f,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp),
                            color = Color(0xFF60A5FA),
                            trackColor = Color(0xFFE0E7FF)
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        onClick = { /* Continue action */ },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Continue")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Bottom navigation bar with floating action button would be implemented in MainActivity
    }
}

@Composable
private fun DashboardFeatureCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    accent: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.height(152.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(accent.copy(alpha = 0.15f), accent.copy(alpha = 0.05f))
                    )
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(accent.copy(alpha = 0.25f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = accent
                )
            }
            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B),
                    fontSize = 16.sp
                )
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = Color(0xFF64748B),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}