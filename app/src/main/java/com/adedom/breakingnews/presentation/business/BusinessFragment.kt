package com.adedom.breakingnews.presentation.business

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adedom.breakingnews.R
import com.adedom.breakingnews.base.BaseFragment
import com.adedom.breakingnews.presentation.ArticleAdapter
import com.adedom.breakingnews.presentation.model.DetailModel
import com.adedom.breakingnews.utils.hideSoftKeyboard
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class BusinessFragment : BaseFragment(R.layout.fragment_main) {

    private val viewModel by viewModel<BusinessViewModel>()
    private val mAdapter by lazy { ArticleAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.callCategoryBusiness()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView()
        observeViewModel()
        viewEvent()
    }

    private fun setUpView() {
        recyclerView.apply {
            val llm = LinearLayoutManager(context)
            layoutManager = llm
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val itemPosition = llm.findLastCompletelyVisibleItemPosition()
                    viewModel.callCategoryBusinessNextPage(itemPosition)
                }
            })
            adapter = mAdapter
        }

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.callCategoryBusiness()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe { state ->
            animationView.isVisible = state.isLoading
        }

        viewModel.getBusiness.observe(viewLifecycleOwner, { BusinessEntity ->
            if (BusinessEntity == null) return@observe

            animationView.isVisible = false
            mAdapter.submitList(BusinessEntity.articles)
        })

        viewModel.error.observeError()
    }

    private fun viewEvent() {
        mAdapter.setOnClickListener {
            activity?.hideSoftKeyboard()

            val model = DetailModel(
                id = it.id,
                author = it.author,
                title = it.title,
                description = it.description,
                url = it.url,
                urlToImage = it.urlToImage,
                publishedAt = it.publishedAt,
            )
            val navDirections = BusinessFragmentDirections
                .actionBusinessFragmentToDetailFragment(model)
            findNavController().navigate(navDirections)
        }

        etSearch.addTextChangedListener { viewModel.setStateSearch(it.toString()) }

        ibSearch.setOnClickListener {
            activity?.hideSoftKeyboard()
            viewModel.callCategoryBusinessSearch()
        }

        etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                activity?.hideSoftKeyboard()
                viewModel.callCategoryBusinessSearch()
                return@setOnEditorActionListener true
            }
            false
        }
    }

}
