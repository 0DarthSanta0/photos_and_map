package com.projects.photos_and_map.domain.use_cases

import com.projects.photos_and_map.domain.repositories.LoginRepository

class SignUpUseCase(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(
        login: String,
        password: String
    ) = loginRepository.signUp(
        login = login,
        password = password
    )
}