package com.projects.photos_and_map.domain.use_cases

import com.projects.photos_and_map.domain.repositories.LoginRepository

class IsAuthorizedCheckUseCase(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke() = loginRepository.isAuthorized()
}