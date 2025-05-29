package view.interfaceElements.buttons

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.internal.composableLambda
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.coremapx.app.config

@Composable
fun DropdownSelectButton(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    additionalColor: Color = config.getColor("mainMenuButtonTextColor"),
    borderColor: Color = config.getColor("dialogBorderColor"),
    borderWidth: Dp = 1.dp,
    backgroundColor: Color = config.getColor("dialogBackgroundColor"),
    textColor: Color = config.getColor("dialogTextColor"),
    fontSize: TextUnit = 16.sp,
    height: Dp = 56.dp,
    iconSize: Dp = 24.dp,
    roundedCornerShapeSize: Dp = 8.dp,
) {
    var expanded by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(if (expanded) 180f else 0f)

    Box(
        modifier =
            modifier
                .wrapContentSize()
                .clip(shape = RoundedCornerShape(roundedCornerShapeSize))
                .border(
                    border = BorderStroke(borderWidth, borderColor),
                    shape = RoundedCornerShape(roundedCornerShapeSize),
                ).background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(roundedCornerShapeSize),
                ).clickable { expanded = !expanded },
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(height)
                    .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = selectedItem,
                fontSize = fontSize,
                fontWeight = FontWeight.Medium,
                color = textColor,
            )
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "Dropdown Arrow",
                modifier =
                    Modifier
                        .size(iconSize)
                        .rotate(rotation),
                tint = additionalColor,
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(backgroundColor)
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    },
                ) {
                    Text(
                        text = item,
                        fontSize = fontSize,
                        color = if (item == selectedItem) additionalColor else textColor,
                    )
                }
            }
        }
    }
}
