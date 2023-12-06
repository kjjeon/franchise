package com.jjanjjan.franchise.shared.presenter.welcome

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
object WelcomeScreen : Screen {
    data class State(
        val welcomeModel: WelcomeModel,
        val eventSink: (Event) -> Unit = {},
    ) : CircuitUiState {
        val message
            get() = welcomeModel.messageList.firstOrNull()
    }

    sealed interface Event : CircuitUiEvent {
        data object NextWelcomeMessage : Event
        data object Launch : Event
    }
}

@Factory
@Named("WelcomeScreenUiFactory")
class WelcomeScreenUiFactory : Ui.Factory {
    override fun create(screen: Screen, context: CircuitContext): Ui<*>? {
        return when (screen) {
            is WelcomeScreen -> ui<WelcomeScreen.State> { state, modifier ->
                WelcomeUi(
                    state,
                    modifier,
                )
            }

            else -> null
        }
    }
}

@Factory
@Named("WelcomePresenterFactory")
class WelcomePresenterFactory : Presenter.Factory {

    override fun create(
        screen: Screen,
        navigator: Navigator,
        context: CircuitContext,
    ): Presenter<*>? {
        return when (screen) {
            is WelcomeScreen -> WelcomePresenter(navigator)
            else -> null
        }
    }
}

