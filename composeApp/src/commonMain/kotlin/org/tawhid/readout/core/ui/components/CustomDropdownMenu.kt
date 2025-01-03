package org.tawhid.readout.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.stringResource
import org.tawhid.readout.core.theme.medium
import org.tawhid.readout.core.theme.small
import readout.composeapp.generated.resources.Res
import readout.composeapp.generated.resources.right_arrow
import java.util.Locale

@Composable
fun CustomDropdownMenu(
    items: List<String>,
    onItemSelected: (String) -> Unit
) {
    var isDropDownExpanded by remember { mutableStateOf(false) }
    var selectedItem by rememberSaveable { mutableStateOf("Select Subject") }

    Box(contentAlignment = Alignment.CenterEnd) {
        TextButton(
            onClick = { isDropDownExpanded = true }
        ) {
            Text(
                text = selectedItem,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = stringResource(Res.string.right_arrow),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
        Box(modifier = Modifier.padding(medium)) {
            DropdownMenu(
                expanded = isDropDownExpanded,
                onDismissRequest = { isDropDownExpanded = false },
                modifier = Modifier.padding(horizontal = small)
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        onClick = {
                            isDropDownExpanded = false
                            selectedItem = item
                            onItemSelected(item)
                        },
                        text = { Text(text = item) }
                    )
                }
            }
        }
    }
}