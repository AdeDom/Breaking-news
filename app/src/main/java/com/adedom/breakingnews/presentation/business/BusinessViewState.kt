package com.adedom.breakingnews.presentation.business

data class BusinessViewState(
    val search: String = "",
    val isLoading: Boolean = false,
)
