package view.appInterface.dialogElements.blocks.helpBlocks

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.coremapx.app.config
import org.coremapx.app.theme.AppTheme
import view.appInterface.preview.PreviewSurface
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

@Suppress("ktlint:standard:function-naming")
@Composable
fun CodeBlock(
    text: String,
    backgroundColor: Color = config.states.borderColor.value,
) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .clip(shape = MaterialTheme.shapes.medium)
                .background(backgroundColor),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.body2,
            modifier =
                Modifier
                    .padding(16.dp)
                    .padding(end = 16.dp),
        )
        Row(
            modifier =
                Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp),
        ) {
            Icon(
                imageVector = Icons.Filled.ContentCopy,
                contentDescription = "Copy Button",
                tint = MaterialTheme.colors.onBackground,
                modifier =
                    Modifier
                        .size(24.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .clickable {
                            val clipboard = Toolkit.getDefaultToolkit().systemClipboard
                            clipboard.setContents(StringSelection(text), null)
                        }.padding(4.dp),
            )
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewCodeBlock() {
    AppTheme {
        PreviewSurface(
            content = {
                CodeBlock(
                    text = "Some test text\n\nSome text on next line",
                )
            },
        )
    }
}
