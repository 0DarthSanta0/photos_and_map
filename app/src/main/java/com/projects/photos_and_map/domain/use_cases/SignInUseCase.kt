package com.projects.photos_and_map.domain.use_cases

import com.projects.photos_and_map.domain.repositories.LoginRepository

class SignInUseCase(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(
        login: String,
        password: String
    ) = loginRepository.signIn(
        login = login,
        password = password
    )
}