package com.projects.photos_and_map


private const val DATA_BASE_ERROR = "Data base error"
private const val RESPONSE_ERROR = "Response error"

sealed class AppErrors(val error: String) {
    object DataBaseError : AppErrors(DATA_BASE_ERROR)
    object ResponseError : AppErrors(RESPONSE_ERROR)
}