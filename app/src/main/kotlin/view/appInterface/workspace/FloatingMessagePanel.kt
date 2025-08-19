package view.appInterface.workspace

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import extensions.border
import org.coremapx.app.config
import org.coremapx.app.localization.LocalizationManager
import org.coremapx.app.theme.AppTheme
import view.appInterface.preview.PreviewSurface

@Suppress("ktlint:standard:function-naming")
@Composable
fun FloatingMessagePanel(
    outputMessages: List<String>,
    modifier: Modifier = Modifier,
    initialPosition: Offset = Offset(100f, 100f),
    backgroundColor: Color = MaterialTheme.colors.background,
    borderColor: Color = MaterialTheme.colors.border,
) {
    var position by remember { mutableStateOf(initialPosition) }

    val scrollState = rememberScrollState()

    LaunchedEffect(outputMessages.size) {
        if (outputMessages.isNotEmpty()) {
            scrollState.animateScrollTo(scrollState.maxValue)
        }
    }

    Box(
        modifier = modifier,
    ) {
        Popup(
            popupPositionProvider =
                object : PopupPositionProvider {
                    override fun calculatePosition(
                        anchorBounds: IntRect,
                        windowSize: IntSize,
                        layoutDirection: LayoutDirection,
                        popupContentSize: IntSize,
                    ): IntOffset = IntOffset(position.x.toInt(), position.y.toInt())
                },
        ) {
            FloatingMessagePanelContent(
                outputMessages = outputMessages,
                onDrag = { dragAmount: Offset ->
                    position += dragAmount
                },
                scrollState = scrollState,
                backgroundColor = backgroundColor,
                borderColor = borderColor,
            )
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun FloatingMessagePanelContent(
    outputMessages: List<String>,
    onDrag: (dragAmount: Offset) -> Unit,
    scrollState: ScrollState,
    backgroundColor: Color = MaterialTheme.colors.background,
    borderColor: Color = MaterialTheme.colors.border,
) {
    var isExpanded by remember { mutableStateOf(true) }

    val maxPanelWidth = config.states.commandFieldWidth.value.dp
    val maxPanelHeight = 300.dp
    val minPanelWidth = 200.dp
    val collapsedHeight = 30.dp
    val panelShape = MaterialTheme.shapes.medium

    Box(
        modifier =
            Modifier
                .width(if (isExpanded) maxPanelWidth else minPanelWidth)
                .height(if (isExpanded) maxPanelHeight else collapsedHeight)
                .clip(panelShape)
                .background(
                    color = backgroundColor,
                    shape = panelShape,
                ).border(
                    width = 1.dp,
                    color = borderColor,
                    shape = panelShape,
                ),
    ) {
        if (isExpanded) {
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                            .background(MaterialTheme.colors.primary)
                            .pointerInput(Unit) {
                                detectDragGestures(
                                    onDrag = { change, dragAmount ->
                                        onDrag(dragAmount)
                                    },
                                )
                            },
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "Messages",
                            color = MaterialTheme.colors.onPrimary,
                            modifier = Modifier.weight(1f).padding(start = 8.dp),
                        )

                        IconButton(
                            onClick = { isExpanded = false },
                            modifier = Modifier.size(24.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "Collapse",
                                tint = MaterialTheme.colors.onPrimary,
                            )
                        }
                    }
                }

                Box(
                    modifier =
                        Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .verticalScroll(scrollState),
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        // It just takes the first word of the template with an error.
                        val errorWord =
                            LocalizationManager.states.ui.errorBasicString.value
                                .split(" ")[0]
                        outputMessages.forEach { message ->
                            val parts = message.split(errorWord, limit = 2)
                            val annotatedText =
                                buildAnnotatedString {
                                    if (parts.size == 2) {
                                        withStyle(style = SpanStyle(color = MaterialTheme.colors.error)) {
                                            append(errorWord)
                                        }
                                        withStyle(style = SpanStyle(color = MaterialTheme.colors.onSurface)) {
                                            append(parts[1])
                                        }
                                    } else {
                                        withStyle(style = SpanStyle(color = MaterialTheme.colors.onSurface)) {
                                            append(message)
                                        }
                                    }
                                }
                            Text(
                                text = annotatedText,
                                modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                            )
                        }
                    }
                }
            }
        } else {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.primary)
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDrag = { change, dragAmount ->
                                    onDrag(dragAmount)
                                },
                            )
                        }.pointerInput(Unit) {
                            detectTapGestures(
                                onTap = {
                                    isExpanded = true
                                },
                            )
                        },
                contentAlignment = Alignment.CenterStart,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 8.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "Expand",
                        tint = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.size(16.dp),
                    )
                    Text(
                        text = "Messages",
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.padding(start = 4.dp),
                    )
                }
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview
@Composable
private fun PreviewFloatingMessagePanel() {
    AppTheme {
        PreviewSurface(
            content = {
                FloatingMessagePanelContent(
                    outputMessages = listOf("ERROR_MESSAGE", "Next message", "..."),
                    onDrag = { },
                    scrollState = rememberScrollState(),
                )
            },
        )
    }
}
