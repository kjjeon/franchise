package com.jjanjjan.franchise.shared.presenter.search

import androidx.compose.runtime.Stable
import com.jjanjjan.franchise.shared.presenter.core.Async

data class SearchModel(private val ver: String) {
    companion object {
        val placeholder = SearchModel(
            ver = "1",
        )
    }
}


@Stable
data class SearchSideEffectModel(
    val searchAsync: Async<String>,
) {
    companion object {
        val placeholder = SearchSideEffectModel(
            searchAsync = Async.Uninitialized,
        )
    }
}