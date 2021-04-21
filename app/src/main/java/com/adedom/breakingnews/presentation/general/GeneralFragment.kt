package com.adedom.breakingnews.presentation.general

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.adedom.breakingnews.R
import com.adedom.breakingnews.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_general.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class GeneralFragment : BaseFragment(R.layout.fragment_general) {

    private val viewModel by viewModel<GeneralViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.attachFirstTime.observe {
            viewModel.callGeneral()
        }

        viewModel.state.observe { state ->
            animationView.isVisible = state.isLoading
        }

        viewModel.getGeneral.observe(viewLifecycleOwner, { generalEntity ->
            if (generalEntity == null) return@observe

            Toast.makeText(context, "${generalEntity}", Toast.LENGTH_SHORT).show()
        })

        viewModel.error.observeError()
    }

}
