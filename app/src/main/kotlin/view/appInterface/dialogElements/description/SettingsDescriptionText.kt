package view.appInterface.dialogElements.description

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import org.coremapx.app.config.PrivateConfig
import org.coremapx.app.theme.AppTheme
import view.appInterface.preview.PreviewSurface

@Suppress("ktlint:standard:function-naming")
@Composable
fun SettingsDescriptionText(
    description: String,
    textStyle: TextStyle = MaterialTheme.typography.body2,
    textColor: Color = MaterialTheme.colors.onSurface.copy(alpha = PrivateConfig.View.DESCRIPTION_ALPHA),
    modifier: Modifier = Modifier.padding(horizontal = 16.dp),
) {
    Text(
        text = description,
        style = textStyle,
        color = textColor,
        modifier = modifier,
    )
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewSettingsDescriptionText() {
    AppTheme {
        PreviewSurface(
            content = {
                Column(
                    modifier = Modifier.padding(8.dp),
                ) {
                    SettingsDescriptionText(
                        description = "Some description",
                    )
                    Spacer(Modifier.height(8.dp))
                    SettingsDescriptionText(
                        description =
                            """
                            Some long description
                            Some long description
                            Some long description
                            Some long description
                            Some long description
                            Some long description
                            Some long description
                            Some long description
                            """.trimIndent(),
                    )
                }
            },
        )
    }
}
