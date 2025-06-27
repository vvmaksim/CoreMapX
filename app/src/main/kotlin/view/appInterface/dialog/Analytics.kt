package view.appInterface.dialog

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.coremapx.app.theme.AppTheme
import view.appInterface.button.DropdownSelectButton
import viewmodel.visualizationStrategy.CircularStrategy
import viewmodel.visualizationStrategy.RandomStrategy
import viewmodel.visualizationStrategy.VisualizationStrategy


@Suppress("ktlint:standard:function-naming")
@Composable
fun Analytics(
    onDismiss: () -> Unit,
    onStrategyUpdate: (VisualizationStrategy) -> Unit,
    selectedLayoutStrategy: String,
    dialogWidth: Dp = 650.dp,
) {
    Dialog(onDismissRequest = onDismiss) {
        AnalyticsContent(
            onDismiss = onDismiss,
            onStrategyUpdate = onStrategyUpdate,
            selectedLayoutStrategy = selectedLayoutStrategy,
            dialogWidth = dialogWidth,
        )
    }
}

@Composable
fun AnalyticsContent(
    onDismiss: () -> Unit,
    onStrategyUpdate: (VisualizationStrategy) -> Unit,
    selectedLayoutStrategy: String,
    dialogWidth: Dp = 650.dp,
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
                    .padding(24.dp)
                    .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            DialogHeader(
                title = "Analytics",
                onButtonClick = onDismiss,
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(
                    text = "Layout strategy:",
                    style = MaterialTheme.typography.body2,
                )
                DropdownSelectButton(
                    items = listOf("Random", "Circular"),
                    selectedItem = selectedLayoutStrategy,
                    onItemSelected = { newStrategy: String ->
                        when (newStrategy) {
                            "Random" -> { onStrategyUpdate(RandomStrategy()) }
                            "Circular" -> { onStrategyUpdate(CircularStrategy()) }
                        }
                    },
                )
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewAnalytics() {
    AppTheme {
        Column {
            AnalyticsContent(
                onDismiss = {},
                onStrategyUpdate = {},
                selectedLayoutStrategy = "Random",
            )
            Spacer(Modifier.height(8.dp))
            AnalyticsContent(
                onDismiss = {},
                onStrategyUpdate = {},
                selectedLayoutStrategy = "Circular",
            )
        }
    }
}
