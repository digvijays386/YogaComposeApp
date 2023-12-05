package com.digvijay.yogaadminapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.digvijay.yogaadminapp.data.CourseRepoImpl
import com.digvijay.yogaadminapp.data.local.CourseDatabase
import com.digvijay.yogaadminapp.data.remote.ApiService
import com.digvijay.yogaadminapp.ui.screen.CourseViewModel
import com.digvijay.yogaadminapp.ui.screen.HomeScreen
import com.digvijay.yogaadminapp.ui.theme.YogaAdminAppTheme
import dagger.hilt.android.AndroidEntryPoint
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
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://stuiis.cms.gre.ac.uk/COMP1424CoreWS/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val apiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    private val viewModel by viewModels<CourseViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return CourseViewModel(CourseRepoImpl(db.dao, apiService)) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YogaAdminAppTheme {
//                val viewModel = hiltViewModel<CourseViewModel>()
                val state by viewModel.state.collectAsState()
                HomeScreen(state = state, onEvent = viewModel::onEvent)
            }
        }
    }
}
