package com.jjanjjan.franchise.shared.presenter.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.jjanjjan.franchise.shared.presenter.splash.SplashScreen
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalResourceApi::class)
@Composable
fun SplashUi(
    state: SplashScreen.State,
    modifier: Modifier = Modifier,
) {
    MaterialTheme {

        LaunchedEffect(Unit) {
            state.eventSink(SplashScreen.Event.Launch(2_000))
        }

        Image(
            painterResource(state.image),
            modifier = Modifier.fillMaxSize(),
            contentDescription = "splash",
            contentScale = ContentScale.FillHeight,

            )

        SideEffect {

        }
    }
}

