package view.appInterface.settingsElements.blocks

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
import org.coremapx.app.config
import org.coremapx.app.localization.LocalizationManager
import org.coremapx.app.theme.AppTheme
import org.coremapx.app.userDirectory.config.ConfigDescriptions
import org.coremapx.app.userDirectory.config.ConfigKeys.CANVAS_DRAG_RATIO
import org.coremapx.app.userDirectory.config.ConfigKeys.CANVAS_LIMIT
import org.coremapx.app.userDirectory.config.ConfigKeys.EDGE_ARROW_SIZE
import org.coremapx.app.userDirectory.config.ConfigKeys.EDGE_LABEL_SIZE
import org.coremapx.app.userDirectory.config.ConfigKeys.EDGE_WIDTH
import org.coremapx.app.userDirectory.config.ConfigKeys.GRAPH_LAYOUT_HEIGHT
import org.coremapx.app.userDirectory.config.ConfigKeys.GRAPH_LAYOUT_WIDTH
import org.coremapx.app.userDirectory.config.ConfigKeys.VERTEX_LABEL_SIZE
import org.coremapx.app.userDirectory.config.ConfigKeys.VERTEX_RADIUS
import view.appInterface.preview.PreviewSurface
import view.appInterface.settingsElements.lines.NumberTextFieldLine

@Suppress("ktlint:standard:function-naming")
@Composable
fun WorkAreaBlock(isExpandedSettings: Boolean = config.states.isExpandedSettings.value) {
    val graphLayoutHeight by remember { config.states.graphLayoutHeight }
    val graphLayoutWidth by remember { config.states.graphLayoutWidth }
    val vertexRadius by remember { config.states.vertexRadius }
    val vertexLabelSize by remember { config.states.vertexLabelSize }
    val edgeLabelSize by remember { config.states.edgeLabelSize }
    val edgeArrowSize by remember { config.states.edgeArrowSize }
    val edgeWidth by remember { config.states.edgeWidth }
    val canvasDragRatio by remember { config.states.canvasDragRatio }
    val canvasLimit by remember { config.states.canvasLimit }

    Column {
        NumberTextFieldLine(
            title = LocalizationManager.states.dialogs.workAreaGraphLayoutHeight.value,
            valueType = Int::class,
            value = TextFieldValue("$graphLayoutHeight"),
            onValueChange = { config.setValue(GRAPH_LAYOUT_HEIGHT, it.text) },
            description = ConfigDescriptions.GRAPH_LAYOUT_HEIGHT,
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        NumberTextFieldLine(
            title = LocalizationManager.states.dialogs.workAreaGraphLayoutWidth.value,
            valueType = Int::class,
            value = TextFieldValue("$graphLayoutWidth"),
            onValueChange = { config.setValue(GRAPH_LAYOUT_WIDTH, it.text) },
            description = ConfigDescriptions.GRAPH_LAYOUT_WIDTH,
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        NumberTextFieldLine(
            title = LocalizationManager.states.dialogs.workAreaVertexRadius.value,
            valueType = Int::class,
            value = TextFieldValue("$vertexRadius"),
            onValueChange = { config.setValue(VERTEX_RADIUS, it.text) },
            description = ConfigDescriptions.VERTEX_RADIUS,
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        NumberTextFieldLine(
            title = LocalizationManager.states.dialogs.workAreaVertexLabelSize.value,
            valueType = Int::class,
            value = TextFieldValue("$vertexLabelSize"),
            onValueChange = { config.setValue(VERTEX_LABEL_SIZE, it.text) },
            description = ConfigDescriptions.VERTEX_LABEL_SIZE,
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        NumberTextFieldLine(
            title = LocalizationManager.states.dialogs.workAreaEdgeLabelSize.value,
            valueType = Int::class,
            value = TextFieldValue("$edgeLabelSize"),
            onValueChange = { config.setValue(EDGE_LABEL_SIZE, it.text) },
            description = ConfigDescriptions.EDGE_LABEL_SIZE,
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        NumberTextFieldLine(
            title = LocalizationManager.states.dialogs.workAreaEdgeArrowSize.value,
            valueType = Float::class,
            value = TextFieldValue("$edgeArrowSize"),
            onValueChange = { config.setValue(EDGE_ARROW_SIZE, it.text) },
            description = ConfigDescriptions.EDGE_ARROW_SIZE,
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        NumberTextFieldLine(
            title = LocalizationManager.states.dialogs.workAreaEdgeWidth.value,
            valueType = Float::class,
            value = TextFieldValue("$edgeWidth"),
            onValueChange = { config.setValue(EDGE_WIDTH, it.text) },
            description = ConfigDescriptions.EDGE_WIDTH,
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        NumberTextFieldLine(
            title = LocalizationManager.states.dialogs.workAreaCanvasDragRatio.value,
            valueType = Float::class,
            value = TextFieldValue("$canvasDragRatio"),
            onValueChange = { config.setValue(CANVAS_DRAG_RATIO, it.text) },
            description = ConfigDescriptions.CANVAS_DRAG_RATIO,
            isExpanded = isExpandedSettings,
        )
        Spacer(Modifier.height(8.dp))
        NumberTextFieldLine(
            title = LocalizationManager.states.dialogs.workAreaCanvasLimit.value,
            valueType = Int::class,
            value = TextFieldValue("$canvasLimit"),
            onValueChange = { config.setValue(CANVAS_LIMIT, it.text) },
            description = ConfigDescriptions.CANVAS_LIMIT,
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
