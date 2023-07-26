package net.soeki.octanalysis

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import net.soeki.octanalysis.logic.Auth
import net.soeki.octanalysis.screen.LoginScreen
import net.soeki.octanalysis.screen.Screen
import net.soeki.octanalysis.screen.bottomItems
import net.soeki.octanalysis.ui.theme.OctAnalysisTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OctAnalysisTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScreenManage(context = applicationContext)
                }
            }
        }
    }
}

@Composable
fun ScreenManage(context: Context) {
    val navController = rememberNavController()
    val auth = Auth(context)
    val isSuccessLogin = auth.tryAutoLogin()

    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                bottomItems.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(screen.icon, null) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(screen.route) {
                                    saveState = true
                                    inclusive = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = if (isSuccessLogin) Screen.Data.route else Screen.Login.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screen.Data.route) {}
            composable(Screen.Login.route) { LoginScreen(context = context) }
        }
    }
}
