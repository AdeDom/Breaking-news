package com.adedom.breakingnews.presentation.general

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adedom.breakingnews.R
import com.adedom.breakingnews.base.BaseFragment
import com.adedom.breakingnews.presentation.model.DetailModel
import kotlinx.android.synthetic.main.fragment_general.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class GeneralFragment : BaseFragment(R.layout.fragment_general) {

    private val viewModel by viewModel<GeneralViewModel>()
    private val mAdapter by lazy { GeneralAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.callCategoryGeneral()
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
                    viewModel.callCategoryGeneralNextPage(itemPosition)
                }
            })
            adapter = mAdapter
        }

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.callCategoryGeneral()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe { state ->
            animationView.isVisible = state.isLoading
        }

        viewModel.getGeneral.observe(viewLifecycleOwner, { generalEntity ->
            if (generalEntity == null) return@observe

            animationView.isVisible = false
            mAdapter.submitList(generalEntity.articles)
        })

        viewModel.error.observeError()
    }

    private fun viewEvent() {
        mAdapter.setOnClickListener {
            val model = DetailModel(
                id = it.id,
                author = it.author,
                title = it.title,
                description = it.description,
                urlToImage = it.urlToImage,
                publishedAt = it.publishedAt,
            )
            val navDirections = GeneralFragmentDirections
                .actionGeneralFragmentToDetailFragment(model)
            findNavController().navigate(navDirections)
        }
    }

}
