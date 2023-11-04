package com.digvijay.yogaadminapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.digvijay.yogaadminapp.data.local.CourseDatabase
import com.digvijay.yogaadminapp.ui.screen.CourseViewModel
import com.digvijay.yogaadminapp.ui.screen.HomeScreen
import com.digvijay.yogaadminapp.ui.theme.YogaAdminAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            CourseDatabase::class.java,
            CourseDatabase.name
        ).build()
    }
    private val viewModel by viewModels<CourseViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return CourseViewModel(db.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YogaAdminAppTheme {
                val state by viewModel.state.collectAsState()
                HomeScreen(state = state, onEvent = viewModel::onEvent)
            }
        }
    }
}
