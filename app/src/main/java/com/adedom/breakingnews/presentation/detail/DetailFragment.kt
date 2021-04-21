package com.adedom.breakingnews.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.adedom.breakingnews.R
import com.adedom.breakingnews.base.BaseFragment
import com.adedom.breakingnews.utils.load
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : BaseFragment(R.layout.fragment_detail) {

    private val args by navArgs<DetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView()
    }

    private fun setUpView() {
        tvTitle.text = args.detail.title
        tvDescription.text = args.detail.description
        tvAuthor.text = args.detail.author
        tvPublishedAt.text = args.detail.publishedAt
        ivImage.load(args.detail.urlToImage)
    }

}
