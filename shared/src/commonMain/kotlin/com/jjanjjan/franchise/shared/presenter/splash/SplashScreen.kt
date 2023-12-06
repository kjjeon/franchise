package com.jjanjjan.franchise.shared.presenter.splash

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
object SplashScreen : Screen {
    data class State(
        val image: String = "",
        val eventSink: (Event) -> Unit = {},
    ) : CircuitUiState

    sealed interface Event : CircuitUiEvent {
        data class Launch(val duration: Long) : Event
    }
}

@Factory
@Named("SplashScreenUiFactory")
class SplashScreenUiFactory : Ui.Factory {
    override fun create(screen: Screen, context: CircuitContext): Ui<*>? {
        return when (screen) {
            is SplashScreen -> ui<SplashScreen.State> { state, modifier ->
                SplashUi(
                    state,
                    modifier,
                )
            }
            else -> null
        }
    }
}


@Factory
@Named("SplashPresenterFactory")
class SplashPresenterFactory : Presenter.Factory {
    override fun create(
        screen: Screen,
        navigator: Navigator,
        context: CircuitContext,
    ): Presenter<*>? {
        return when (screen) {
            is SplashScreen -> SplashPresenter(navigator)
            else -> null
        }
    }
}

