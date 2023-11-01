package com.projects.photos_and_map.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.projects.photos_and_map.ui.screens.authorization.AuthorizationScreen
import com.projects.photos_and_map.ui.screens.details.DetailsScreen
import com.projects.photos_and_map.ui.screens.main.MainScreen
import com.projects.photos_and_map.ui.screens.map.MapScreen
import com.projects.photos_and_map.ui.screens.photos.PhotosScreen


private const val ID = "id"

@Composable
fun AppNavigation(startScreen: Screens?) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startScreen?.route ?: Screens.AuthorizationScreen.route
    ) {
        composable(route = Screens.AuthorizationScreen.route) {
            AuthorizationScreen(onAuthorization = {
                navController.navigate(Screens.MainScreen.route)
            })
        }
        composable(route = Screens.MainScreen.route) {
            MainScreen()
        }
    }
}

@Composable
fun MainNavigation(startScreen: Screens) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startScreen.route
    ) {
        composable(route = Screens.PhotosScreen.route) {
            PhotosScreen(onItemSelect = fun(id: Int) {
                navController.navigate(Screens.DetailsScreen.route + "/$id")
            })
        }
        composable(route = Screens.MapScreen.route) {
            MapScreen()
        }
        composable(
            route = Screens.DetailsScreen.route + "/{$ID}",
            arguments = listOf(
                navArgument(ID) {
                    type = NavType.IntType
                }
            )
        ) { entry ->
            DetailsScreen(
                id = entry.arguments?.getInt(ID),
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
