package com.finance_tracker.finance_tracker.core.common

import androidx.compose.runtime.Composable
import com.seiko.imageloader.AsyncImagePainter
import dev.icerock.moko.resources.FileResource

@Composable
expect fun rememberAsyncImagePainter(fileResource: FileResource): AsyncImagePainter