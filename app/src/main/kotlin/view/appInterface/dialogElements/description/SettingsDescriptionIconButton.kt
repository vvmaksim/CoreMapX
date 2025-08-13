package view.appInterface.dialogElements.description

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.coremapx.app.localization.LocalizationManager
import org.coremapx.app.theme.AppTheme
import view.appInterface.preview.PreviewSurface

@Suppress("ktlint:standard:function-naming")
@Composable
fun SettingsDescriptionIconButton(
    onClick: () -> Unit,
    isExpanded: Boolean,
    iconTintColor: Color = MaterialTheme.colors.primary,
    modifier: Modifier = Modifier,
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = if (isExpanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
            contentDescription =
                if (isExpanded) {
                    LocalizationManager.states.anyIconDescriptionsStates.settingsHideDescription.value
                } else {
                    LocalizationManager.states.anyIconDescriptionsStates.settingsShowDescription.value
                },
            tint = iconTintColor,
            modifier = modifier,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewSettingsDescriptionIconButton() {
    AppTheme {
        PreviewSurface(
            content = {
                Column(
                    modifier = Modifier.padding(8.dp),
                ) {
                    SettingsDescriptionIconButton(
                        onClick = {},
                        isExpanded = true,
                    )
                    Spacer(Modifier.height(8.dp))
                    SettingsDescriptionIconButton(
                        onClick = {},
                        isExpanded = false,
                    )
                }
            },
        )
    }
}
