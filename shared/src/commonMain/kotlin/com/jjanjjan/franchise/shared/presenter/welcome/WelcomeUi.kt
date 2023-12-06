package com.jjanjjan.franchise.shared.presenter.welcome

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer


@Composable
fun WelcomeUi(
    state: WelcomeScreen.State,
    modifier: Modifier = Modifier,
) {

    MaterialTheme {

//        val state by welcomeScreenModel.state.collectAsState()
//
//        LaunchedEffect(welcomeScreenModel.effect) {
//            welcomeScreenModel.effect.collect { welcomeEffect ->
//                when (welcomeEffect) {
//                    is WelcomeEffect.LaunchSearchScreen -> navigator.replace(SearchScreen())
//                }
//            }
//        }

        println("joel state = ${state.welcomeModel.messageList}")

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            state.message?.let { message ->
                WelcomeText(
                    message
                ) {
                    state.eventSink(WelcomeScreen.Event.NextWelcomeMessage)
                }
            } ?: run {
                state.eventSink(WelcomeScreen.Event.Launch)
            }
        }

    }
}

@Composable
private fun WelcomeText(
    message: String,
    onFinished: () -> Unit,
) {
    var visible by remember { mutableStateOf(false) }
    val alphaAnimation by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 1000, delayMillis = 0),
    ) {
        println("it = $it")
        if (it == 1f) visible = false
        else if (it == 0f) onFinished()
    }
    LaunchedEffect(message) {
        visible = true
    }
    Text(
        message,
        modifier = Modifier.graphicsLayer {
            alpha = alphaAnimation
        },
        style = MaterialTheme.typography.h6
    )
}


//@Factory
//@Named("WelcomeScreenFactory")
//fun provideWelcomeScreenFactory(): Ui.Factory = WelcomeScreenUiFactory()
