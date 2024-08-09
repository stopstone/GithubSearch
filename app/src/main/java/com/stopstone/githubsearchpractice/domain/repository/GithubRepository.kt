package com.stopstone.githubsearchpractice.domain.repository

import com.stopstone.githubsearchpractice.domain.model.GithubUser

interface GithubRepository {
    suspend fun searchGithubUsers(username: String): List<GithubUser>
}