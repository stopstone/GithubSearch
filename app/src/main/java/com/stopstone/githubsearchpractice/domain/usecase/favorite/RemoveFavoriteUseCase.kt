package com.stopstone.githubsearchpractice.domain.usecase.favorite

import com.stopstone.githubsearchpractice.domain.model.GithubUser
import com.stopstone.githubsearchpractice.domain.repository.FavoriteRepository
import javax.inject.Inject

class RemoveFavoriteUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(user: GithubUser) {
        repository.removeFavorite(user)
    }
}