package com.stopstone.githubsearchpractice.domain.usecase

import com.stopstone.githubsearchpractice.domain.model.GithubUser
import com.stopstone.githubsearchpractice.domain.repository.GithubRepository
import javax.inject.Inject

class SearchGithubUsersUseCase @Inject constructor(private val repository: GithubRepository) {
    suspend operator fun invoke(query: String): List<GithubUser> {
        return repository.searchGithubUsers(query)
    }
}