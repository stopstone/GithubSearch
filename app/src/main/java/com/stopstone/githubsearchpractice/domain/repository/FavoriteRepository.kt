package com.stopstone.githubsearchpractice.domain.repository

import com.stopstone.githubsearchpractice.domain.model.GithubUser
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun addFavorite(user: GithubUser)
    suspend fun removeFavorite(user: GithubUser)
    suspend fun isFavorite(userId: Long): Boolean
    fun getFavorites(): Flow<List<GithubUser>>
}