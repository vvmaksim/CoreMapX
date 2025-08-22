package view.appInterface.dialogElements.blocks.helpBlocks

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.coremapx.app.localization.LocalizationManager
import org.coremapx.app.localization.objects.LocalizationFormatter
import org.coremapx.app.theme.AppTheme
import org.coremapx.app.userDirectory.config.ConfigRepository
import view.appInterface.preview.PreviewSurface

@Suppress("ktlint:standard:function-naming")
@Composable
fun UserDirectoryBlock() {
    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        Text(
            text = LocalizationManager.states.dialogs.userDirectoryGeneralInformationTitle.value,
            style = MaterialTheme.typography.h5,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.dialogs.userDirectoryGeneralInformationText1.value,
                ),
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.dialogs.userDirectoryAttention3Point1.value,
                ),
            color = ConfigRepository.states.warningColor.value,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.dialogs.userDirectoryAttention3Point2.value,
                ),
            color = ConfigRepository.states.warningColor.value,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.dialogs.userDirectoryAttention3Point3.value,
                ),
            color = ConfigRepository.states.warningColor.value,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = LocalizationManager.states.dialogs.userDirectoryInterfaceLanguageConfigurationTitle.value,
            style = MaterialTheme.typography.h5,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = LocalizationManager.states.dialogs.userDirectoryInterfaceLanguageConfigurationText1.value,
        )
        Spacer(Modifier.height(8.dp))
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewUserDirectoryBlock() {
    AppTheme {
        PreviewSurface(content = { UserDirectoryBlock() })
    }
}
