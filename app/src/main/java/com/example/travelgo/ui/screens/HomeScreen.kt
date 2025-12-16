package com.example.travelgo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.travelgo.ui.navegation.Screen

// 👉 IMPORT IMPORTANTE
import androidx.compose.material3.ExperimentalMaterial3Api

data class FakePackage(
    val id: Int,
    val name: String,
    val description: String,
    val price: Int
)

@OptIn(ExperimentalMaterial3Api::class)   // ⬅️ ESTA ES LA CLAVE
@Composable
fun HomeScreen(navController: NavController) {
    val paquetes = remember {
        listOf(
            FakePackage(1, "Atacama sustentable", "Tour 3 días por el desierto", 250000),
            FakePackage(2, "Patagonia ecológica", "Trekking y glaciares", 420000),
            FakePackage(3, "Valparaíso urbano", "City tour y rutas culturales", 120000)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("TravelGo") },
                actions = {
                    TextButton(onClick = { navController.navigate(Screen.Profile.route) }) {
                        Text("Perfil")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            items(paquetes) { p ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(p.name, style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(4.dp))
                        Text(p.description)
                        Spacer(Modifier.height(8.dp))
                        Text("Precio: $${p.price}")
                    }
                }
            }
        }
    }
}
