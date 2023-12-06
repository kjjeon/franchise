package com.jjanjjan.franchise.shared.di

import androidx.compose.runtime.Composable
import com.jjanjjan.franchise.shared.data.DataModule
import com.jjanjjan.franchise.shared.domain.DomainModule
import org.koin.compose.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.ksp.generated.module

private fun allModules(): List<Module> {
    return listOf(
        AppModule().module,
        DataModule().module,
        DomainModule().module,
    )
}

/**
 * Initialize all of our Koin DI modules.
 */
fun initKoin(
    modules: List<Module> = emptyList(),
) {
    startKoin {
        modules(modules)
        modules(allModules())
    }
}

@Composable
fun initKoin(
    modules: List<Module> = emptyList(),
    content: @Composable () -> Unit
) {
    KoinApplication(application = {
        // Koin configuration here
        modules(modules)
        modules(allModules())
    }) {
        content()
    }
}