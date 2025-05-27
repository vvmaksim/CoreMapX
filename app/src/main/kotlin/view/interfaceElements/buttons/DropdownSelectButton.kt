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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.coremapx.app.config

@Composable
fun DropdownSelectButton(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(if (expanded) 180f else 0f)
    val additionalColor = config.getColor("mainMenuButtonTextColor")
    val borderColor = Color(0xFFE0E0E0)

    Box(
        modifier = modifier
            .wrapContentSize()
            .border(
                border = BorderStroke(1.dp, borderColor),
                shape = RoundedCornerShape(8.dp)
            )
            .background(
                Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { expanded = !expanded }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = selectedItem,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "Dropdown Arrow",
                modifier = Modifier
                    .size(24.dp)
                    .rotate(rotation),
                tint = additionalColor
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .border(
                    border = BorderStroke(1.dp, borderColor),
                    shape = RoundedCornerShape(8.dp)
                )
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
                        fontSize = 16.sp,
                        color = if (item == selectedItem) additionalColor else Color.Black
                    )
                }
            }
        }
    }
}
