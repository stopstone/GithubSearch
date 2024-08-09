package com.stopstone.githubsearchpractice.data.remote.api

import com.stopstone.githubsearchpractice.data.model.GithubSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiService {
    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String
    ): GithubSearchResponse
}