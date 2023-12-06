package com.jjanjjan.franchise.shared.presenter.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.jjanjjan.franchise.shared.presenter.welcome.WelcomeScreen

class SplashPresenter(
    private val navigator: Navigator
) : Presenter<SplashScreen.State> {
    @Composable
    override fun present(): SplashScreen.State {

        val image by rememberRetained { mutableStateOf("splash.jpg") }
        val scope = rememberCoroutineScope()

        return SplashScreen.State(image) { event ->
            when (event) {
                is SplashScreen.Event.Launch -> {
                    scope.launch(Dispatchers.Default) {
                        delay(event.duration)
                        navigator.resetRoot(WelcomeScreen)
                    }
                }
            }
        }
    }

}
