package com.example.tasktraker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tasktraker.navigation.NavigationRoot
import com.example.tasktraker.ui.theme.LocalIsDarkTheme
import com.example.tasktraker.ui.theme.LocalToggleTheme
import com.example.tasktraker.ui.theme.TaskTrackerTheme
import com.example.tasktraker.viewModels.ThemeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeViewModel: ThemeViewModel = viewModel()
            val isDark by themeViewModel.isDarkTheme
            TaskTrackerTheme(darkTheme = isDark) {
                CompositionLocalProvider(
                    LocalIsDarkTheme provides isDark,
                    LocalToggleTheme provides { themeViewModel.toggleTheme() }
                ) {
                    Scaffold { innerPadding ->
                        NavigationRoot(
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}
