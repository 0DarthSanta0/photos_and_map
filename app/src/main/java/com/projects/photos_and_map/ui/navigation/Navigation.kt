package com.projects.photos_and_map.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.projects.photos_and_map.ui.screens.authorization.AuthorizationScreen
import com.projects.photos_and_map.ui.screens.main.MainScreen


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
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.PhotosScreen.route
    ) {
//        composable(route = Screens.AuthorizationScreen.route) {
//            AuthorizationScreen(onLoginSuccess = {
//                navController.navigate(Screens.PlaylistsScreen.route)
//            }, onLoginError = {
//                navController.navigate(Screens.AuthorizationScreen.route)
//            })
//        }
    }
}

