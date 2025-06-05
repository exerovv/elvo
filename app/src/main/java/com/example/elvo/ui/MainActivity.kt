package com.example.elvo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.elvo.ui.navigation.AppNavigation
import com.example.elvo.ui.theme.ElvoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ElvoTheme {
                AppNavigation()
            }
        }
    }
}