package com.stopstone.githubsearchpractice.view.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.stopstone.githubsearchpractice.databinding.FragmentSearchBinding
import com.stopstone.githubsearchpractice.domain.model.GithubUser
import com.stopstone.githubsearchpractice.util.hideKeyboard
import com.stopstone.githubsearchpractice.view.common.GithubUserAdapter
import com.stopstone.githubsearchpractice.view.common.listener.OnFavoriteClickListener
import com.stopstone.githubsearchpractice.view.search.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(), OnFavoriteClickListener {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()
    private val adapter: GithubUserAdapter by lazy { GithubUserAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        collectViewModel()
        setListeners()
    }

    // RecyclerView 설정
    private fun setupRecyclerView() {
        binding.rvSearchList.adapter = adapter
        binding.rvSearchList.layoutManager = LinearLayoutManager(requireContext())
    }

    // ViewModel의 데이터 수집
    private fun collectViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            launch { collectUser() }
        }
    }

    private suspend fun collectUser() {
        viewModel.users.collect { users ->
            adapter.submitList(users)
        }
    }

    private fun setListeners() {
        binding.btnSearch.setOnClickListener {
            performSearch()
        }

        binding.etSearch.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                (event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)
            ) {
                performSearch()
                true
            } else {
                false
            }
        }
    }

    // 검색 수행
    private fun performSearch() {
        val query = binding.etSearch.text.toString()
        if (query.isNotEmpty()) {
            viewModel.loadUsers(query)
            requireContext().hideKeyboard(binding.etSearch)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onFavoriteClick(user: GithubUser) {
        viewModel.toggleFavorite(user)
    }
}
