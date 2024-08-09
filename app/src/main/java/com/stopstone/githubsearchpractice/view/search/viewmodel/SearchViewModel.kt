package com.stopstone.githubsearchpractice.view.search.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stopstone.githubsearchpractice.domain.model.GithubUser
import com.stopstone.githubsearchpractice.domain.usecase.SearchGithubUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchGithubUsersUseCase: SearchGithubUsersUseCase
): ViewModel() {
    private val _userName = MutableStateFlow<List<GithubUser>>(emptyList())
    val userName = _userName.asStateFlow()

    fun loadUsers(query: String) = viewModelScope.launch {
        val users = searchGithubUsersUseCase(query)
        _userName.emit(users)
        Log.d("SearchViewModel", "Users: $_userName")
    }
}