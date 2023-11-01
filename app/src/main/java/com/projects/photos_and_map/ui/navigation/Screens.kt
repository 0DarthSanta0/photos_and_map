package com.projects.photos_and_map.ui.navigation

const val AUTHORIZATION_SCREEN = "auth_screen"
const val MAP_SCREEN = "map_screen"
const val PHOTOS_SCREEN = "photos_screen"
const val MAIN_SCREEN = "main_screen"
const val DETAILS_SCREEN = "details_screen"

sealed class Screens(val route: String) {
    object AuthorizationScreen : Screens(AUTHORIZATION_SCREEN)
    object MapScreen : Screens(MAP_SCREEN)
    object PhotosScreen : Screens(PHOTOS_SCREEN)
    object MainScreen : Screens(MAIN_SCREEN)
    object DetailsScreen : Screens(DETAILS_SCREEN)
}
