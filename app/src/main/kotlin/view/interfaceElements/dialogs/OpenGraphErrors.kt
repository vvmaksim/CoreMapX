package view.interfaceElements.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import org.coremapx.app.config

@Composable
fun OpenGraphErrors(
    onDismiss: () -> Unit,
    warnings: List<String>,
) {
    val backgroundColor = config.getColor("mainMenuColor")
    val textColor = config.getColor("mainMenuButtonTextColor")
    val buttonColor = config.getColor("mainMenuButtonColor")

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier =
                Modifier
                    .width(600.dp)
                    .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            color = backgroundColor,
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
                    tint = Color(0xFFFFA000),
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Errors and warnings when loading the graph",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
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
                                        .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Error,
                                    contentDescription = "Error",
                                    modifier = Modifier.size(16.dp),
                                    tint = Color(0xFFE53935),
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = error,
                                    color = textColor,
                                    fontSize = 14.sp,
                                )
                            }
                            if (index < warnings.size - 1) {
                                Divider(
                                    modifier = Modifier.padding(vertical = 8.dp),
                                    color = Color.White.copy(alpha = 0.1f),
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Incorrect lines were replaced with default values or were not taken into account at all",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onDismiss,
                    colors =
                        ButtonDefaults.buttonColors(
                            backgroundColor = buttonColor,
                            contentColor = textColor,
                        ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.width(200.dp),
                ) {
                    Text(
                        text = "OK",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}
