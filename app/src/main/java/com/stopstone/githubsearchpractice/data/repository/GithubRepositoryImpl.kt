package com.stopstone.githubsearchpractice.data.repository

import android.util.Log
import com.stopstone.githubsearchpractice.data.remote.api.GithubApiService
import com.stopstone.githubsearchpractice.domain.model.GithubUser
import com.stopstone.githubsearchpractice.domain.repository.GithubRepository
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val githubApiService: GithubApiService
) : GithubRepository {
    override suspend fun searchGithubUsers(username: String): List<GithubUser> {
        val response = try {
            githubApiService.searchUsers(username)
        } catch (e: Exception) {
            Log.e("GithubRepositoryImpl", "Error searching users", e)
            throw e
        }

        return response.items.map { item ->
            GithubUser(
                id = item.id,
                login = item.login,
                avatarUrl = item.avatarUrl,
                score = item.score,
                isFavorite = false
            )
        }
    }
}