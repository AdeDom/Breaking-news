package com.adedom.breakingnews.presentation.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adedom.breakingnews.base.BaseFragment
import com.adedom.breakingnews.databinding.FragmentSettingBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingFragment : BaseFragment() {

    private lateinit var binding: FragmentSettingBinding

    private val viewModel by viewModel<SettingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupView() {
        binding.swSearchThaiNews.isChecked = viewModel.getIsSearchOnlyThaiNews()

        binding.swSearchThaiNews.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setIsSearchOnlyThaiNews(isChecked)
        }
    }

}
