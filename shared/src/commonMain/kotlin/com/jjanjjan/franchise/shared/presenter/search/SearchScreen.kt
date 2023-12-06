package com.jjanjjan.franchise.shared.presenter.search


import com.jjanjjan.franchise.shared.CommonParcelize
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named

@CommonParcelize
object SearchScreen : Screen {
    data class State(
        val searchModel: SearchModel,
        val searchSideEffectModel: SearchSideEffectModel,
        val eventSink: (Event) -> Unit = {},
    ) : CircuitUiState

    sealed interface Event : CircuitUiEvent {
        data class Launch(val duration: Long) : Event
    }
}

class SearchScreenUiFactory : Ui.Factory {
    override fun create(screen: Screen, context: CircuitContext): Ui<*>? {
        return when (screen) {
            is SearchScreen -> ui<SearchScreen.State> { state, modifier ->
                SearchUi(
                    state,
                    modifier,
                )
            }
            else -> null
        }
    }
}
@Factory
@Named("SearchScreenUiFactory")
fun provideSearchScreenUiFactory(): Ui.Factory = SearchScreenUiFactory()

class SearchPresenterFactory : Presenter.Factory {

    override fun create(
        screen: Screen,
        navigator: Navigator,
        context: CircuitContext,
    ): Presenter<*>? {
        return when (screen) {
            is SearchScreen -> SearchPresenter(navigator)
            else -> null
        }
    }
}
@Factory
@Named("SearchPresenterFactory")
fun provideSearchPresenterFactory(): Presenter.Factory = SearchPresenterFactory()