package com.projects.photos_and_map

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.projects.photos_and_map.ui.navigation.AppNavigation
import com.projects.photos_and_map.ui.theme.PhotosAndMapTheme


class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels { MainActivityViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val screen by viewModel.route.collectAsStateWithLifecycle()
            PhotosAndMapTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (screen != null) {
                        AppNavigation(screen)
                    }
                }
            }
        }
    }
}
