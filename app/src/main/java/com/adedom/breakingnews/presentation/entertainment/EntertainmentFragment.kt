package com.adedom.breakingnews.presentation.entertainment

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

class EntertainmentFragment : BaseFragment() {

    private lateinit var binding: FragmentMainBinding

    private val viewModel by viewModel<EntertainmentViewModel>()
    private val mAdapter by lazy { ArticleAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.callCategoryEntertainment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView()
        observeViewModel()
        viewEvent()
    }

    private fun setUpView() {
        binding.recyclerView.apply {
            val llm = LinearLayoutManager(context)
            layoutManager = llm
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val itemPosition = llm.findLastCompletelyVisibleItemPosition()
                    viewModel.callCategoryEntertainmentNextPage(itemPosition)
                }
            })
            adapter = mAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe { state ->
            binding.animationView.isVisible = state.isLoading
        }

        viewModel.getEntertainment.observe(viewLifecycleOwner) { EntertainmentEntity ->
            if (EntertainmentEntity == null) return@observe

            binding.animationView.isVisible = false
            mAdapter.submitList(EntertainmentEntity.articles)
        }

        viewModel.error.observeError()
    }

    private fun viewEvent() {
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
            val navDirections = EntertainmentFragmentDirections
                .actionEntertainmentFragmentToDetailFragment(model)
            findNavController().navigate(navDirections)
        }

        binding.etSearch.addTextChangedListener { viewModel.setStateSearch(it.toString()) }

        binding.ibSearch.setOnClickListener {
            activity?.hideSoftKeyboard()
            viewModel.callCategoryEntertainmentSearch()
        }

        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                activity?.hideSoftKeyboard()
                viewModel.callCategoryEntertainmentSearch()
                return@setOnEditorActionListener true
            }
            false
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.callCategoryEntertainment()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

}
