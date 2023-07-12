package net.soeki.octanalysis.screen

sealed class Screen(val route: String) {
    object Data : Screen("Data")
    object Login : Screen("login")
}

val bottomItems = listOf(
    Screen.Data,
    Screen.Login
)
