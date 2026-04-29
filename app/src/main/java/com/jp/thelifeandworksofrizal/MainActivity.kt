package com.jp.thelifeandworksofrizal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.google.firebase.FirebaseApp
import com.example.yourapp.GetStartedScreen
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.ShowChart

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            @OptIn(ExperimentalMaterial3Api::class)
            RizalApp()
        }
    }
}

// Your existing Screen sealed class
sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object Story : Screen("story")
    object ChapterDetail : Screen("chapterDetail/{chapterIndex}") {
        const val ARG = "chapterIndex"
        fun createRoute(chapterIndex: Int) = "chapterDetail/$chapterIndex"
    }
    object QuizChapters : Screen("quiz_chapters")
    object Quiz : Screen("quiz/{chapterIndex}") {
        const val ARG = "chapterIndex"
        fun createRoute(chapterIndex: Int) = "quiz/$chapterIndex"
    }
    object WrittenReport : Screen("written_report")
    object Members : Screen("members")
    object AiAssistant : Screen("ai_assistant")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RizalBottomNavigationBar(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = selectedTab == 0,
            onClick = { onTabSelected(0) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Book, contentDescription = "Chapters") },
            label = { Text("Chapters") },
            selected = selectedTab == 1,
            onClick = { onTabSelected(1) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.AutoMirrored.Filled.ShowChart, contentDescription = "Progress") },
            label = { Text("Progress") },
            selected = selectedTab == 2,
            onClick = { onTabSelected(2) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
            label = { Text("Profile") },
            selected = selectedTab == 3,
            onClick = { onTabSelected(3) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RizalApp() {
    val navController = rememberNavController()
    var selectedTab by remember { mutableIntStateOf(0) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            if (currentRoute != Screen.Dashboard.route && currentRoute != "get_started") {
                CenterAlignedTopAppBar(
                    title = { Text(getScreenTitle(currentRoute)) },
                    navigationIcon = {
                        TextButton(onClick = { navController.popBackStack() }) {
                            Text("Back")
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (currentRoute != "get_started") {
                RizalBottomNavigationBar(selectedTab = selectedTab, onTabSelected = { tab: Int ->
                    selectedTab = tab
                    when(tab) {
                        0 -> navController.navigate(Screen.Dashboard.route)
                        1 -> navController.navigate(Screen.Story.route)
                        2 -> navController.navigate(Screen.QuizChapters.route)
                        3 -> navController.navigate(Screen.Members.route)
                    }
                })
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "get_started",
            modifier = Modifier.padding(innerPadding)
        ) {
            // Get Started Screen first
            composable("get_started") { GetStartedScreen(navController) }

            // Dashboard
            composable(Screen.Dashboard.route) {
                DashboardScreen(
                    onStoryClick = { navController.navigate(Screen.Story.route) },
                    onQuizClick = { navController.navigate(Screen.QuizChapters.route) },
                    onReportClick = { navController.navigate(Screen.WrittenReport.route) },
                    onMembersClick = { navController.navigate(Screen.Members.route) },
                    onAiAssistantClick = { navController.navigate(Screen.AiAssistant.route) }
                )
            }

            // Story
            composable(Screen.Story.route) {
                StoryScreen(book = RizalData.book) { chapterIndex ->
                    navController.navigate(Screen.ChapterDetail.createRoute(chapterIndex))
                }
            }

            // Chapter Detail
            composable(
                route = Screen.ChapterDetail.route,
                arguments = listOf(navArgument(Screen.ChapterDetail.ARG) { type = NavType.IntType })
            ) { backStackEntry ->
                val chapterIndex = backStackEntry.arguments?.getInt(Screen.ChapterDetail.ARG) ?: 0
                ChapterDetailScreen(chapter = RizalData.book.chapters[chapterIndex])
            }

            // Quiz Chapters
            composable(Screen.QuizChapters.route) {
                QuizChapterSelectionScreen(
                    chapterQuizzes = RizalData.chapterQuizzes,
                    onChapterSelected = { index -> navController.navigate(Screen.Quiz.createRoute(index)) }
                )
            }

            // Quiz
            composable(
                route = Screen.Quiz.route,
                arguments = listOf(navArgument(Screen.Quiz.ARG) { type = NavType.IntType })
            ) { backStackEntry ->
                val chapterIndex = backStackEntry.arguments?.getInt(Screen.Quiz.ARG) ?: 0
                val selectedQuiz = RizalData.chapterQuizzes.getOrElse(chapterIndex) { RizalData.chapterQuizzes.first() }
                QuizScreen(quiz = selectedQuiz, onBackToChapters = { navController.popBackStack() })
            }

            // Written Report
            composable(Screen.WrittenReport.route) {
                WrittenReportScreen(sections = RizalData.reportSections)
            }

            // Members
            composable(Screen.Members.route) {
                MembersScreen(members = RizalData.members)
            }

            // AI Assistant
            composable(Screen.AiAssistant.route) {
                AiAssistantScreen()
            }
        }
    }
}

private fun getScreenTitle(route: String?): String {
    return when (route) {
        Screen.Story.route -> "Story of Rizal"
        Screen.ChapterDetail.route -> "Chapter Detail"
        Screen.QuizChapters.route -> "Quiz Chapters"
        Screen.Quiz.route -> "Quiz"
        Screen.WrittenReport.route -> "Written Report"
        Screen.Members.route -> "Members"
        Screen.AiAssistant.route -> "AI Assistant"
        else -> "The Life and Works of Rizal"
    }
}