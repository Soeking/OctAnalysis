package net.soeki.octanalysis.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val icon: ImageVector) {
    object Data : Screen("Data", Icons.Default.Home)
    object Login : Screen("login", Icons.Default.Info)
}

val bottomItems = listOf(
    Screen.Data,
    Screen.Login
)
