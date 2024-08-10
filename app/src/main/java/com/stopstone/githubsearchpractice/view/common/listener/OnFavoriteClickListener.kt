package com.stopstone.githubsearchpractice.view.common.listener

import com.stopstone.githubsearchpractice.domain.model.GithubUser

interface OnFavoriteClickListener {
    fun onFavoriteClick(user: GithubUser)
}