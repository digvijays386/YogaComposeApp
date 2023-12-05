package com.digvijay.yogaadminapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.digvijay.yogaadminapp.data.CourseRepoImpl
import com.digvijay.yogaadminapp.data.local.CourseDatabase
import com.digvijay.yogaadminapp.data.remote.ApiService
import com.digvijay.yogaadminapp.ui.BottomBarScreen
import com.digvijay.yogaadminapp.ui.screen.HomeScreen
import com.digvijay.yogaadminapp.ui.screen.ScheduleScreen
import com.digvijay.yogaadminapp.ui.screen.TeacherScreen
import com.digvijay.yogaadminapp.ui.theme.YogaAdminAppTheme
import com.digvijay.yogaadminapp.ui.viewmodels.CourseViewModel
import com.digvijay.yogaadminapp.ui.viewmodels.ScheduleViewModel
import com.digvijay.yogaadminapp.ui.viewmodels.TeacherViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            CourseDatabase::class.java,
            CourseDatabase.name
        ).build()
    }

    private val okHttpClient = getOkHttpClient()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://stuiis.cms.gre.ac.uk/COMP1424CoreWS/comp1424cw/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    private val apiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    private val viewModel by viewModels<CourseViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return CourseViewModel(CourseRepoImpl(db.dao, apiService)) as T
                }
            }
        }
    )
    private val classViewModel by viewModels<ScheduleViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ScheduleViewModel(db.classInstanceDao) as T
                }
            }
        }
    )

    private val teacherViewModel by viewModels<TeacherViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return TeacherViewModel(db.teacherDao) as T
                }
            }
        }
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YogaAdminAppTheme {
                MainScreen()
            }
        }
    }

    @Composable
    fun MainScreen() {
        val navController = rememberNavController()

        Scaffold(
            bottomBar = { BottomNavigationComponent(navController) },
            floatingActionButton = { /* FAB composable */ },
            isFloatingActionButtonDocked = true,
        ) { padding ->
            NavHost(navController, startDestination = BottomBarScreen.Courses.route) {

                composable(BottomBarScreen.Courses.route) {
                    val state by viewModel.state.collectAsState()
                    HomeScreen(
                        state = state,
                        onEvent = viewModel::onEvent
                    )
                }
                composable(BottomBarScreen.Schedule.route) {
                    val state by classViewModel.state.collectAsState()
                    val courses = viewModel.state.value.courses
                    val teachers = teacherViewModel.state.value.teachers
                    ScheduleScreen(state, courses,teachers, classViewModel::onEvent)
                }
                composable(BottomBarScreen.Teacher.route) {
                    val state by teacherViewModel.state.collectAsState()
                    TeacherScreen(
                        state = state,
                        onEvent = teacherViewModel::onEvent
                    )
                }
            }
        }
    }

    @Composable
    fun BottomNavigationComponent(navController: NavHostController) {
        val items = listOf(
            BottomBarScreen.Courses,
            BottomBarScreen.Schedule,
            BottomBarScreen.Teacher
        )

        BottomAppBar(backgroundColor = Color.White) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            items.forEach { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painterResource(id = item.icon),
                            contentDescription = item.title
                        )
                    },
                    label = {
                        Text(
                            text = item.title,
                            fontSize = 12.sp
                        )
                    },
                    selectedContentColor = Color.Blue,
                    unselectedContentColor = Color.Black.copy(0.4f),
                    alwaysShowLabel = true,
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let { screen_route ->
                                popUpTo(screen_route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }

    private fun getOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }
}
