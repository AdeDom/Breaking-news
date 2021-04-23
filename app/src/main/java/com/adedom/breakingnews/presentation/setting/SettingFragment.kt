package com.adedom.breakingnews.presentation.setting

import android.os.Bundle
import android.view.View
import com.adedom.breakingnews.R
import com.adedom.breakingnews.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_setting.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingFragment : BaseFragment(R.layout.fragment_setting) {

    private val viewModel by viewModel<SettingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView()
    }

    private fun setUpView() {
        swSearchThaiNews.isChecked = viewModel.getIsSearchOnlyThaiNews()

        swSearchThaiNews.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setIsSearchOnlyThaiNews(isChecked)
        }
    }

}
