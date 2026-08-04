package com.example.missionarchitect.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.missionarchitect.presentation.detail.DetailScreen
import com.example.missionarchitect.presentation.home.HomeScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute,
        modifier = modifier
    ) {
        // 1. Home Destination
        composable<HomeRoute> {
            HomeScreen(
                onUserClick = { userId, userName ->
                    navController.navigate(DetailRoute(userId = userId, userName = userName))
                }
            )
        }

        // 2. Detail Destination (Type-Safe Argument Extraction)
        composable<DetailRoute> { backStackEntry ->
            val route: DetailRoute = backStackEntry.toRoute()
            DetailScreen(
                userid = route.userId,
                userName = route.userName,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}