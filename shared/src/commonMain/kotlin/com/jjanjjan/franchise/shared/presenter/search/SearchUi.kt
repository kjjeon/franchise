package com.jjanjjan.franchise.shared.presenter.search

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.jjanjjan.franchise.shared.data.FranchiseRepository
import com.jjanjjan.franchise.shared.presenter.search.SearchScreen
import kotlinx.coroutines.launch


@Composable
fun SearchUi(
    state: SearchScreen.State,
    modifier: Modifier = Modifier,
) {
    MaterialTheme {
        val scope = rememberCoroutineScope()


//            val state by splashScreenModel.state.collectAsState()
//
//            LaunchedEffect(splashScreenModel.effect) {
//                splashScreenModel.effect.collect { splashEffect ->
////                    when (val result = splashEffect) {
////                    }
//                }
//            }
        var text by remember { mutableStateOf("Loading") }

            LaunchedEffect(true) {
                scope.launch {
                    text = try {
                        FranchiseRepository().getFranchise()
                    } catch (e: Exception) {
                        e.message ?: "error"
                    }
                }
            }

        Text(text = text)

        //            var greetingText by remember { mutableStateOf("Hello World!") }
        //            var showImage by remember { mutableStateOf(false) }
        //            val scope = rememberCoroutineScope()
        //            var text by remember { mutableStateOf("Loading") }
        //            LaunchedEffect(true) {
        //                scope.launch {
        //                    text = try {
        //                        FranchiseRepository().getFranchise()
        //                    } catch (e: Exception) {
        //                        e.message ?: "error"
        //                    }
        //                }
        //            }
        //            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        //                Button(onClick = {
        //                    greetingText = "Compose: $text"
        //                    showImage = !showImage
        //                }) {
        //                    Text(greetingText)
        //                }
        //                AnimatedVisibility(showImage) {
        //                    Image(
        //                        painterResource("compose-multiplatform.xml"),
        //                        null
        //                    )
        //                }
        //            }
        //        }

    }
}
