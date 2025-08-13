package view.appInterface.dialogElements.lines

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.coremapx.app.theme.AppTheme
import view.appInterface.preview.PreviewSurface
import view.appInterface.dialogElements.description.SettingsDescriptionIconButton
import view.appInterface.dialogElements.description.SettingsDescriptionText

@Suppress("ktlint:standard:function-naming")
@Composable
fun SwitchLine(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    isExpanded: Boolean = false,
) {
    var expanded by remember { mutableStateOf(isExpanded) }

    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.Companion.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.weight(1f),
            )
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(checkedThumbColor = MaterialTheme.colors.primary),
            )
            SettingsDescriptionIconButton(
                onClick = { expanded = !expanded },
                isExpanded = expanded,
            )
        }
        if (expanded) {
            Spacer(modifier = Modifier.height(8.dp))
            SettingsDescriptionText(description = description)
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewSwitchLine() {
    AppTheme {
        PreviewSurface(
            content = {
                Column(
                    modifier = Modifier.padding(8.dp),
                ) {
                    SwitchLine(
                        title = "Setting 1",
                        description = "Some description",
                        checked = true,
                        onCheckedChange = {},
                        modifier = Modifier.padding(16.dp),
                        isExpanded = true,
                    )
                    Spacer(Modifier.height(8.dp))
                    SwitchLine(
                        title = "Setting 2",
                        description = "Some description",
                        checked = false,
                        onCheckedChange = {},
                        modifier = Modifier.padding(16.dp),
                        isExpanded = false,
                    )
                }
            },
        )
    }
}
