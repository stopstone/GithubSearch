package com.stopstone.githubsearchpractice.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.stopstone.githubsearchpractice.domain.model.GithubUser
import com.stopstone.githubsearchpractice.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val gson: Gson
) : FavoriteRepository {

    override suspend fun addFavorite(user: GithubUser) {
        dataStore.edit { preferences ->
            val currentFavorites = getFavoritesFromPreferences(preferences)
            if (user !in currentFavorites) {
                val updatedFavorites = currentFavorites + user
                preferences[FAVORITES_KEY] = gson.toJson(updatedFavorites)
            }
        }
    }

    override suspend fun removeFavorite(user: GithubUser) {
        dataStore.edit { preferences ->
            val currentFavorites = getFavoritesFromPreferences(preferences)
            val updatedFavorites = currentFavorites.filter { it.id != user.id }
            preferences[FAVORITES_KEY] = gson.toJson(updatedFavorites)
        }
    }

    override suspend fun isFavorite(userId: Long): Boolean {
        return dataStore.data
            .map { preferences ->
                getFavoritesFromPreferences(preferences).any { it.id == userId }
            }
            .first()
    }

    override fun getFavorites(): Flow<List<GithubUser>> {
        return dataStore.data.map { preferences ->
            getFavoritesFromPreferences(preferences)
        }
    }

    private fun getFavoritesFromPreferences(preferences: Preferences): List<GithubUser> {
        val favoritesJson = preferences[FAVORITES_KEY] ?: return emptyList()
        return gson.fromJson(favoritesJson, object : TypeToken<List<GithubUser>>() {}.type)
    }

    companion object {
        private val FAVORITES_KEY = stringPreferencesKey("favorites")
    }
}