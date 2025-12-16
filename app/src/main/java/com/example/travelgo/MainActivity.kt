package com.example.travelgo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.travelgo.ui.navegation.AppNavigation
import com.example.travelgo.ui.theme.TravelGoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TravelGoTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigation()   // 👉 usa la navegación “real”
                }
            }
        }
    }
}
