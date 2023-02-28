package com.finance_tracker.finance_tracker.core.common

import androidx.compose.runtime.Composable
import com.seiko.imageloader.AsyncImagePainter
import com.seiko.imageloader.rememberAsyncImagePainter
import dev.icerock.moko.resources.FileResource

@Composable
actual fun rememberAsyncImagePainter(fileResource: FileResource): AsyncImagePainter {
    return rememberAsyncImagePainter(fileResource.rawResId)
}