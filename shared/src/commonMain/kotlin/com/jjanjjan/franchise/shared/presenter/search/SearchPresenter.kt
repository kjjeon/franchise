package com.jjanjjan.franchise.shared.presenter.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.jjanjjan.franchise.shared.presenter.core.producerAsync

class SearchPresenter(
    private val navigator: Navigator
) : Presenter<SearchScreen.State> {
    @Composable
    override fun present(): SearchScreen.State {

        var searchModel by rememberRetained { mutableStateOf(SearchModel.placeholder) }
        var searchSideEffectModel by rememberRetained { mutableStateOf(SearchSideEffectModel.placeholder) }

        searchSideEffectModel = searchSideEffectModel.copy(
            searchAsync = producerAsync(
                async = searchSideEffectModel.searchAsync,
            ) {
                "a"
            }
        )

        return SearchScreen.State(
            searchModel = searchModel,
            searchSideEffectModel = searchSideEffectModel,
        ) { event ->
            when (event) {
                is SearchScreen.Event.Launch -> {
                }
            }
        }
    }

}
