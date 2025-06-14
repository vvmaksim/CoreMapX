package view.appInterface.dialogs

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import extensions.warning
import org.coremapx.app.theme.AppTheme

@Suppress("ktlint:standard:function-naming")
@Composable
fun OpenGraphErrors(
    onDismiss: () -> Unit,
    warnings: List<String>,
    dialogWidth: Dp = 550.dp,
) {
    Dialog(onDismissRequest = onDismiss) {
        OpenGraphErrorsContent(
            onDismiss = onDismiss,
            warnings = warnings,
            dialogWidth = dialogWidth,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun OpenGraphErrorsContent(
    onDismiss: () -> Unit,
    warnings: List<String>,
    dialogWidth: Dp = 550.dp,
) {
    Surface(
        modifier =
            Modifier
                .width(dialogWidth)
                .padding(16.dp),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colors.background,
    ) {
        Column(
            modifier =
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Warning",
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colors.warning,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Errors and warnings when loading the graph",
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .heightIn(max = 300.dp),
            ) {
                Column(
                    modifier =
                        Modifier
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                ) {
                    warnings.forEachIndexed { index, error ->
                        Row(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                imageVector = Icons.Default.Error,
                                contentDescription = "Error",
                                modifier = Modifier.size(20.dp),
                                tint = MaterialTheme.colors.error,
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = error,
                                style = MaterialTheme.typography.body1,
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onDismiss,
                colors =
                    ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = MaterialTheme.colors.background,
                    ),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "OK",
                    style = MaterialTheme.typography.button,
                )
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
fun PreviewOpenGraphErrorsWithTwoErrors() {
    AppTheme {
        OpenGraphErrorsContent(
            onDismiss = {},
            warnings =
                listOf(
                    "Some error",
                    "Second error",
                ),
            dialogWidth = 550.dp,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
fun PreviewOpenGraphErrorsWithMoreErrors() {
    AppTheme {
        OpenGraphErrorsContent(
            onDismiss = {},
            warnings =
                listOf(
                    "Some error 1",
                    "Some error 2",
                    "Some error 3",
                    "Some error 4",
                    "Some error 5",
                    "Some error 6",
                    "Some error 7",
                    "Some error 8",
                ),
            dialogWidth = 550.dp,
        )
    }
}
