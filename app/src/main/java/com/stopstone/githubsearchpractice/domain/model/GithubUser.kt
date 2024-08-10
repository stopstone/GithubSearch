package com.stopstone.githubsearchpractice.domain.model

data class GithubUser(
    val id: Long,
    val login: String,
    val avatarUrl: String,
    val isFavorite: Boolean
)