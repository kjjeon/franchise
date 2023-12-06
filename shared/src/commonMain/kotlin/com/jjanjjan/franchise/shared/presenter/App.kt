import androidx.compose.runtime.Composable
import com.jjanjjan.franchise.shared.di.initKoin
import com.jjanjjan.franchise.shared.presenter.PresenterModule
import com.jjanjjan.franchise.shared.presenter.di.CircuitModule
import com.jjanjjan.franchise.shared.presenter.splash.SplashScreen
import com.jjanjjan.franchise.shared.provideCircuitNavigator
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import kotlinx.collections.immutable.persistentListOf
import org.koin.compose.koinInject
import org.koin.ksp.generated.module

@Composable
fun App() {
    initKoin(
        listOf(
            PresenterModule().module,
            CircuitModule().module
        )

    ) {
        circuit()
    }

}

@Composable
private fun circuit(circuit: Circuit = koinInject()) {

    val initialBackstack = persistentListOf(SplashScreen)

    val backstack = rememberSaveableBackStack {
        initialBackstack.forEach { screen -> push(screen) }
    }

    val navigator = provideCircuitNavigator(backStack = backstack) {

    }
    CircuitCompositionLocals(circuit) {
        NavigableCircuitContent(navigator, backstack)
    }
}