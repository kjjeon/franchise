package com.jjanjjan.franchise.shared.presenter.di

import com.slack.circuit.foundation.Circuit
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan
class CircuitModule {
    @Single
    fun provideCircuit(
        presenterFactories: List<Presenter.Factory>,
        uiFactories: List<Ui.Factory>
    ): Circuit =
        Circuit.Builder()
            .addPresenterFactories(presenterFactories)
            .addUiFactories(uiFactories)
            .build()

}