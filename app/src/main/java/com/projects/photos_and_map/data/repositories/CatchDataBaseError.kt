package com.projects.photos_and_map.data.repositories

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.projects.photos_and_map.AppErrors
import java.io.IOException


inline infix fun <T, V> T.catchDataBaseErrors(block: T.() -> V): Result<V, AppErrors> {
    return try {
        Ok(block())
    } catch (e: IOException) {
        Err(AppErrors.DataBaseError)
    }
}
