package com.example.mezbaan.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember

@Composable
fun ProvideAppUtils(
    appDimens: Dimens,
    content: @Composable () -> Unit,
) {
    val appDimenss = remember { appDimens }
    CompositionLocalProvider(LocalAppDimens provides appDimenss) {
        content()
    }
}

val LocalAppDimens = compositionLocalOf {
    CompactDimens
}
