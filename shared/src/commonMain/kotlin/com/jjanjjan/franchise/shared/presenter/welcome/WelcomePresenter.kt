package com.jjanjjan.franchise.shared.presenter.welcome

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.jjanjjan.franchise.shared.presenter.search.SearchScreen

class WelcomePresenter(
    private val navigator: Navigator
) : Presenter<WelcomeScreen.State> {
    @Composable
    override fun present(): WelcomeScreen.State {

        val scope = rememberCoroutineScope()
        var welcomeModel by rememberRetained {
            mutableStateOf(
                WelcomeModel.placeholder.copy(
                    messageList = listOf("가맹점을 찾고 있나요?", "모든 가맹점 정보를 한번에", "이제 묻지마 창업은 그만")
                )
            )
        }

        println("joel messageList = ${welcomeModel.messageList}")

        return WelcomeScreen.State(welcomeModel) { event ->
            when (event) {
                WelcomeScreen.Event.Launch -> navigator.resetRoot(SearchScreen)
                WelcomeScreen.Event.NextWelcomeMessage -> {
                    welcomeModel = welcomeModel.copy(messageList = welcomeModel.messageList.drop(1))
                }
            }
        }
    }

}
//
//@Factory
//@Named("WelcomePresenterFactory")
//fun provideWelcomePresenterFactory(): Presenter.Factory = WelcomePresenterFactory()