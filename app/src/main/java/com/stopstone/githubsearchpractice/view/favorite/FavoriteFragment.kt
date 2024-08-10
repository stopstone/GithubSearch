package com.stopstone.githubsearchpractice.view.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.stopstone.githubsearchpractice.databinding.FragmentFavoriteBinding
import com.stopstone.githubsearchpractice.domain.model.GithubUser
import com.stopstone.githubsearchpractice.view.common.GithubUserAdapter
import com.stopstone.githubsearchpractice.view.common.listener.OnFavoriteClickListener
import com.stopstone.githubsearchpractice.view.favorite.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment(), OnFavoriteClickListener {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoriteViewModel by viewModels()
    private val adapter: GithubUserAdapter by lazy { GithubUserAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        collectViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onFavoriteClick(user: GithubUser) {
        viewModel.removeFavorite(user)
    }

    private fun setupRecyclerView() {
        binding.rvFavoriteList.adapter = adapter
        binding.rvFavoriteList.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun collectViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            launch { collectFavorites() }
        }
    }

    private suspend fun collectFavorites() {
        viewModel.favorites.collectLatest { favorites ->
            adapter.submitList(favorites)
        }
    }

}
