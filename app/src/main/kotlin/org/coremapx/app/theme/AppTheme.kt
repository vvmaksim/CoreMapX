package org.coremapx.app.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.runtime.Composable

@Suppress("ktlint:standard:function-naming")
@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = Theme.colors,
        typography = Typography,
        shapes =
            Shapes(
                small = AppShapes.small,
                medium = AppShapes.medium,
                large = AppShapes.large,
            ),
        content = content,
    )
}
