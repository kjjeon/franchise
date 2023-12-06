package com.jjanjjan.franchise.shared

import androidx.compose.runtime.Composable
import com.slack.circuit.backstack.SaveableBackStack
import com.slack.circuit.foundation.rememberCircuitNavigator
import com.slack.circuit.runtime.Navigator

@Composable
actual fun provideCircuitNavigator(
    backStack: SaveableBackStack,
    onRootPop: () -> Unit,
): Navigator {
    return rememberCircuitNavigator(
        backstack = backStack,
        onRootPop = onRootPop,
    )
}