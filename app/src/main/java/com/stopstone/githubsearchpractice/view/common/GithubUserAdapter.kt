package com.stopstone.githubsearchpractice.view.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stopstone.githubsearchpractice.databinding.ItemListBinding
import com.stopstone.githubsearchpractice.domain.model.GithubUser
import com.stopstone.githubsearchpractice.util.loadImage
import com.stopstone.githubsearchpractice.view.common.listener.OnFavoriteClickListener


class GithubUserAdapter(
    private val listener: OnFavoriteClickListener
) : ListAdapter<GithubUser, GithubUserAdapter.GithubUserViewHolder>(GithubUserDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubUserViewHolder {
        return GithubUserViewHolder(
            ItemListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ) { listener.onFavoriteClick(getItem(it)) }
    }

    override fun onBindViewHolder(holder: GithubUserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class GithubUserViewHolder(
        private val binding: ItemListBinding,
        private val onFavoriteClickListener: (index: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnFavorite.setOnClickListener {
                onFavoriteClickListener(adapterPosition)
            }
        }

        fun bind(githubUser: GithubUser) {
            with(binding) {
                ivGithubProfileImage.loadImage(githubUser.avatarUrl)
                tvGithubProfileName.text = githubUser.login
                btnFavorite.isSelected = githubUser.isFavorite
            }
        }
    }
}

class GithubUserDiffUtilCallback : DiffUtil.ItemCallback<GithubUser>() {
    override fun areItemsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
        return oldItem == newItem
    }
}