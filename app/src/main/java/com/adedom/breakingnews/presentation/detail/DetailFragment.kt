package com.adedom.breakingnews.presentation.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.adedom.breakingnews.base.BaseFragment
import com.adedom.breakingnews.databinding.FragmentDetailBinding
import com.adedom.breakingnews.utils.load

class DetailFragment : BaseFragment() {

    private lateinit var binding: FragmentDetailBinding

    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupView() {
        binding.tvTitle.text = args.detail.title
        binding.tvDescription.text = args.detail.description
        binding.tvUrl.text = SpannableString(args.detail.url).apply {
            setSpan(UnderlineSpan(), 0, this.length, 0)
        }
        binding.tvAuthor.text = args.detail.author
        binding.tvPublishedAt.text = args.detail.publishedAt

        binding.tvDescription.isVisible = !args.detail.description.isNullOrBlank()

        binding.ivImage.isVisible = !args.detail.urlToImage.isNullOrBlank()
        args.detail.urlToImage?.let { binding.ivImage.load(args.detail.urlToImage) }
    }

    override fun setupEvent() {
        binding.tvUrl.setOnClickListener {
            Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(args.detail.url)
                startActivity(this)
            }
        }
    }

}
