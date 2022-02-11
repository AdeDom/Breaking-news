package com.adedom.breakingnews.presentation.technology

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

class TechnologyFragment : BaseFragment() {

    private lateinit var binding: FragmentMainBinding

    private val viewModel by viewModel<TechnologyViewModel>()
    private val mAdapter by lazy { ArticleAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.callCategoryTechnology()
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
                    viewModel.callCategoryTechnologyNextPage(itemPosition)
                }
            })
            adapter = mAdapter
        }
    }

    override fun setupViewModel() {
        viewModel.uiState.observe { state ->
            binding.animationView.isVisible = state.isLoading
        }

        viewModel.getTechnology.observe(viewLifecycleOwner) { TechnologyEntity ->
            if (TechnologyEntity == null) return@observe

            binding.animationView.isVisible = false
            mAdapter.submitList(TechnologyEntity.articles)
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
            val navDirections = TechnologyFragmentDirections
                .actionTechnologyFragmentToDetailFragment(model)
            findNavController().navigate(navDirections)
        }

        binding.etSearch.addTextChangedListener { viewModel.setStateSearch(it.toString()) }

        binding.ibSearch.setOnClickListener {
            activity?.hideSoftKeyboard()
            viewModel.callCategoryTechnologySearch()
        }

        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                activity?.hideSoftKeyboard()
                viewModel.callCategoryTechnologySearch()
                return@setOnEditorActionListener true
            }
            false
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.callCategoryTechnology()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

}
