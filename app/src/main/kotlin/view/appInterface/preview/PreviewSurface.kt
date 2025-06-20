package view.appInterface.preview

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.coremapx.app.theme.AppTheme

@Suppress("ktlint:standard:function-naming")
@Composable
fun PreviewSurface(
    content: @Composable () -> Unit,
    surfaceShape: CornerBasedShape = MaterialTheme.shapes.large,
    surfaceModifier: Modifier = Modifier.padding(8.dp),
    surfaceColor: Color = MaterialTheme.colors.background,
) {
    Surface(
        shape = surfaceShape,
        modifier = surfaceModifier,
        color = surfaceColor,
    ) {
        content()
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewPreviewSurface() {
    AppTheme {
        PreviewSurface(
            content = {
                Column(
                    modifier = Modifier.padding(8.dp),
                ) {
                    Text("Some Text On Surface")
                    Spacer(Modifier.height(8.dp))
                    Text("Some Text On Surface")
                }
            },
        )
    }
}
