package view.appInterface.dialogElements.blocks.settingsBlocks

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import org.coremapx.app.localization.LocalizationManager
import org.coremapx.app.localization.objects.LocalizationFormatter
import org.coremapx.app.theme.AppTheme
import org.coremapx.app.userDirectory.config.ConfigKeys.CANVAS_DRAG_RATIO
import org.coremapx.app.userDirectory.config.ConfigKeys.CANVAS_LIMIT
import org.coremapx.app.userDirectory.config.ConfigKeys.EDGE_ARROW_SIZE
import org.coremapx.app.userDirectory.config.ConfigKeys.EDGE_LABEL_SIZE
import org.coremapx.app.userDirectory.config.ConfigKeys.EDGE_WIDTH
import org.coremapx.app.userDirectory.config.ConfigKeys.GRAPH_LAYOUT_HEIGHT
import org.coremapx.app.userDirectory.config.ConfigKeys.GRAPH_LAYOUT_WIDTH
import org.coremapx.app.userDirectory.config.ConfigKeys.VERTEX_LABEL_SIZE
import org.coremapx.app.userDirectory.config.ConfigKeys.VERTEX_RADIUS
import org.coremapx.app.userDirectory.config.ConfigRepository
import view.appInterface.dialogElements.lines.NumberTextFieldLine
import view.appInterface.preview.PreviewSurface

@Suppress("ktlint:standard:function-naming")
@Composable
fun WorkAreaBlock(isExpandedSettings: Boolean = ConfigRepository.states.isExpandedSettings.value) {
    val graphLayoutHeight by remember { ConfigRepository.states.graphLayoutHeight }
    val graphLayoutWidth by remember { ConfigRepository.states.graphLayoutWidth }
    val vertexRadius by remember { ConfigRepository.states.vertexRadius }
    val vertexLabelSize by remember { ConfigRepository.states.vertexLabelSize }
    val edgeLabelSize by remember { ConfigRepository.states.edgeLabelSize }
    val edgeArrowSize by remember { ConfigRepository.states.edgeArrowSize }
    val edgeWidth by remember { ConfigRepository.states.edgeWidth }
    val canvasDragRatio by remember { ConfigRepository.states.canvasDragRatio }
    val canvasLimit by remember { ConfigRepository.states.canvasLimit }

    Column {
        NumberTextFieldLine(
            title = LocalizationManager.states.dialogs.workAreaGraphLayoutHeight.value,
            valueType = Int::class,
            value = TextFieldValue("$graphLayoutHeight"),
            onValueChange = { ConfigRepository.setValue(GRAPH_LAYOUT_HEIGHT, it.text) },
            description =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.descriptions.descriptionGraphLayoutHeight.value,
                ),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        NumberTextFieldLine(
            title = LocalizationManager.states.dialogs.workAreaGraphLayoutWidth.value,
            valueType = Int::class,
            value = TextFieldValue("$graphLayoutWidth"),
            onValueChange = { ConfigRepository.setValue(GRAPH_LAYOUT_WIDTH, it.text) },
            description =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.descriptions.descriptionGraphLayoutWidth.value,
                ),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        NumberTextFieldLine(
            title = LocalizationManager.states.dialogs.workAreaVertexRadius.value,
            valueType = Int::class,
            value = TextFieldValue("$vertexRadius"),
            onValueChange = { ConfigRepository.setValue(VERTEX_RADIUS, it.text) },
            description =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.descriptions.descriptionVertexRadius.value,
                ),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        NumberTextFieldLine(
            title = LocalizationManager.states.dialogs.workAreaVertexLabelSize.value,
            valueType = Int::class,
            value = TextFieldValue("$vertexLabelSize"),
            onValueChange = { ConfigRepository.setValue(VERTEX_LABEL_SIZE, it.text) },
            description =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.descriptions.descriptionVertexLabelSize.value,
                ),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        NumberTextFieldLine(
            title = LocalizationManager.states.dialogs.workAreaEdgeLabelSize.value,
            valueType = Int::class,
            value = TextFieldValue("$edgeLabelSize"),
            onValueChange = { ConfigRepository.setValue(EDGE_LABEL_SIZE, it.text) },
            description =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.descriptions.descriptionEdgeLabelSize.value,
                ),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        NumberTextFieldLine(
            title = LocalizationManager.states.dialogs.workAreaEdgeArrowSize.value,
            valueType = Float::class,
            value = TextFieldValue("$edgeArrowSize"),
            onValueChange = { ConfigRepository.setValue(EDGE_ARROW_SIZE, it.text) },
            description =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.descriptions.descriptionEdgeArrowSize.value,
                ),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        NumberTextFieldLine(
            title = LocalizationManager.states.dialogs.workAreaEdgeWidth.value,
            valueType = Float::class,
            value = TextFieldValue("$edgeWidth"),
            onValueChange = { ConfigRepository.setValue(EDGE_WIDTH, it.text) },
            description =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.descriptions.descriptionEdgeWidth.value,
                ),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        NumberTextFieldLine(
            title = LocalizationManager.states.dialogs.workAreaCanvasDragRatio.value,
            valueType = Float::class,
            value = TextFieldValue("$canvasDragRatio"),
            onValueChange = { ConfigRepository.setValue(CANVAS_DRAG_RATIO, it.text) },
            description =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.descriptions.descriptionCanvasDragRatio.value,
                ),
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        NumberTextFieldLine(
            title = LocalizationManager.states.dialogs.workAreaCanvasLimit.value,
            valueType = Int::class,
            value = TextFieldValue("$canvasLimit"),
            onValueChange = { ConfigRepository.setValue(CANVAS_LIMIT, it.text) },
            description =
                LocalizationFormatter.getStringWithLineBreak(
                    startString = LocalizationManager.states.descriptions.descriptionCanvasLimit.value,
                ),
            isExpanded = isExpandedSettings,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewWorkAreaBlock() {
    AppTheme {
        PreviewSurface(content = { WorkAreaBlock() })
    }
}
