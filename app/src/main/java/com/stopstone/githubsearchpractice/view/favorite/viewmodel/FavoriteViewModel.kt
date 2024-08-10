package com.stopstone.githubsearchpractice.view.favorite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stopstone.githubsearchpractice.domain.model.GithubUser
import com.stopstone.githubsearchpractice.domain.usecase.favorite.GetFavoritesUseCase
import com.stopstone.githubsearchpractice.domain.usecase.favorite.RemoveFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
    getFavoritesUseCase: GetFavoritesUseCase,
    ) : ViewModel() {

    val favorites: StateFlow<List<GithubUser>> = getFavoritesUseCase()
        .map { users -> users.map { it.copy(isFavorite = true) } }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun removeFavorite(user: GithubUser) {
        viewModelScope.launch {
            removeFavoriteUseCase(user)
        }
    }
}
