package com.stopstone.githubsearchpractice.data.repository

import com.stopstone.githubsearchpractice.data.remote.api.GithubApiService
import com.stopstone.githubsearchpractice.domain.repository.FavoriteRepository
import com.stopstone.githubsearchpractice.domain.repository.GithubRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideGithubRepository(apiService: GithubApiService): GithubRepository {
        return GithubRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideFavoriteRepository(favoriteRepository: FavoriteRepositoryImpl): FavoriteRepository {
        return favoriteRepository
    }
}
