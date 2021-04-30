package com.adedom.breakingnews.presentation.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
import androidx.core.view.isVisible
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
        viewEvent()
    }

    private fun setUpView() {
        tvTitle.text = args.detail.title
        tvDescription.text = args.detail.description
        tvUrl.text = SpannableString(args.detail.url).apply {
            setSpan(UnderlineSpan(), 0, this.length, 0)
        }
        tvAuthor.text = args.detail.author
        tvPublishedAt.text = args.detail.publishedAt

        ivImage.isVisible = args.detail.urlToImage != null
        args.detail.urlToImage?.let { ivImage.load(args.detail.urlToImage) }
    }

    private fun viewEvent() {
        tvUrl.setOnClickListener {
            Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(args.detail.url)
                startActivity(this)
            }
        }
    }

}
