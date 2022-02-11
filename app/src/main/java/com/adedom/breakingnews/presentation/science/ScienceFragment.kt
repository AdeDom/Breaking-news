package com.adedom.breakingnews.presentation.science

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adedom.breakingnews.base.BaseFragment
import com.adedom.breakingnews.databinding.FragmentMainBinding
import com.adedom.breakingnews.presentation.ArticleAdapter
import com.adedom.breakingnews.presentation.model.DetailModel
import com.adedom.breakingnews.utils.hideSoftKeyboard
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScienceFragment : BaseFragment() {

    private lateinit var binding: FragmentMainBinding

    private val viewModel by viewModel<ScienceViewModel>()
    private val mAdapter by lazy { ArticleAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.callCategoryScience()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupAdapter() {
        binding.recyclerView.apply {
            val llm = LinearLayoutManager(context)
            layoutManager = llm
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val itemPosition = llm.findLastCompletelyVisibleItemPosition()
                    viewModel.callCategoryScienceNextPage(itemPosition)
                }
            })
            adapter = mAdapter
        }
    }

    override fun setupViewModel() {
        viewModel.uiState.observe { state ->
            binding.animationView.isVisible = state.isLoading
        }

        viewModel.getScience.observe(viewLifecycleOwner) { ScienceEntity ->
            if (ScienceEntity == null) return@observe

            binding.animationView.isVisible = false
            mAdapter.submitList(ScienceEntity.articles)
        }

        viewModel.error.observeError()
    }

    override fun setupEvent() {
        mAdapter.setOnClickListener {
            activity?.hideSoftKeyboard()
            binding.swipeRefreshLayout.isRefreshing = false

            val model = DetailModel(
                id = it.id,
                author = it.author,
                title = it.title,
                description = it.description,
                url = it.url,
                urlToImage = it.urlToImage,
                publishedAt = it.publishedAt,
            )
            val navDirections = ScienceFragmentDirections
                .actionScienceFragmentToDetailFragment(model)
            findNavController().navigate(navDirections)
        }

        binding.etSearch.addTextChangedListener { viewModel.setStateSearch(it.toString()) }

        binding.ibSearch.setOnClickListener {
            activity?.hideSoftKeyboard()
            viewModel.callCategoryScienceSearch()
        }

        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                activity?.hideSoftKeyboard()
                viewModel.callCategoryScienceSearch()
                return@setOnEditorActionListener true
            }
            false
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.callCategoryScience()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

}
