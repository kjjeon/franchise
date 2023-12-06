package com.jjanjjan.franchise.shared.presenter.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import com.jjanjjan.franchise.shared.toImageBitmap
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.utils.io.core.use
//import org.jetbrains.compose.resources.ExperimentalResourceApi
//import org.jetbrains.compose.resources.readResourceBytes


//@Composable
//internal fun ResourceImage(
//    resource: String,
//    modifier: Modifier = Modifier,
//    contentDescription: String? = null,
//    contentScale: ContentScale = ContentScale.Fit,
//    placeholder: @Composable (Composable.() -> Unit)? = null
//) {
//    val image by produceState<ImageBitmap?>(null) {
//        value = fromResource(resource)
//    }
//
//    image?.let {
//        Image(
//            bitmap = it,
//            contentDescription = contentDescription,
//            modifier = modifier,
//            contentScale = contentScale
//        )
//    } ?: placeholder
//}
//
//@Composable
//internal fun KtorImage(
//    url: String,
//    modifier: Modifier = Modifier,
//    contentDescription: String? = null,
//    contentScale: ContentScale = ContentScale.Fit,
//    placeholder: @Composable (Composable.() -> Unit)? = null
//) {
//    val image by produceState<ImageBitmap?>(null) {
//        value = loadPicture(url)
//    }
//
//    image?.let {
//        Image(
//            bitmap = it,
//            contentDescription = contentDescription,
//            modifier = modifier,
//            contentScale = contentScale
//        )
//    } ?: placeholder
//}
//
//suspend fun loadPicture(url: String): ImageBitmap {
//    val image = HttpClient().use { client ->
//        client.get(url).body<ByteArray>()
//    }
//    return image.toImageBitmap()
//}

//@OptIn(ExperimentalResourceApi::class)
//suspend fun fromResource(resource: String) = readResourceBytes(resource).toImageBitmap()

