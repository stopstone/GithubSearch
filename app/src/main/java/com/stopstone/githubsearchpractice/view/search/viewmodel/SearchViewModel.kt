package com.stopstone.githubsearchpractice.view.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stopstone.githubsearchpractice.domain.model.GithubUser
import com.stopstone.githubsearchpractice.domain.repository.FavoriteRepository
import com.stopstone.githubsearchpractice.domain.usecase.search.SearchGithubUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: FavoriteRepository,
    private val searchGithubUsersUseCase: SearchGithubUsersUseCase,
) : ViewModel() {
    private val _users = MutableStateFlow<List<GithubUser>>(emptyList())
    val users: StateFlow<List<GithubUser>> = _users.asStateFlow()

    init {
        // 좋아요 리스트에 포함된 아이템은 isFavorite를 true로 설정
        viewModelScope.launch {
            repository.getFavorites().collect { favorites ->
                val updatedUsers = _users.value.map { user ->
                    user.copy(isFavorite = favorites.any { it.id == user.id }) // 좋아요된 아이템이 있다면 true로 반영
                }
                _users.value = updatedUsers
            }
        }
    }

    fun loadUsers(query: String) = viewModelScope.launch {
        // 사용자를 불러올 때 좋아요 상태 확인 후 업데이트
        val updatedUsers = searchGithubUsersUseCase(query).map { user ->
            user.copy(isFavorite = repository.isFavorite(user.id))
        }
        _users.value = updatedUsers
    }

    fun toggleFavorite(user: GithubUser) = viewModelScope.launch {
        // 좋아요 상태 변경 후 업데이트
        val updatedUser = user.copy(isFavorite = !user.isFavorite)
        when (updatedUser.isFavorite) {
            true -> repository.addFavorite(updatedUser)
            false -> repository.removeFavorite(updatedUser)
        }
    }
}

