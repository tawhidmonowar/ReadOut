package org.tawhid.readout.core.ui.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.stringResource
import org.tawhid.readout.core.theme.medium
import org.tawhid.readout.core.theme.small
import org.tawhid.readout.core.ui.components.CustomDropdownMenu
import readout.composeapp.generated.resources.Res
import readout.composeapp.generated.resources.right_arrow
import readout.composeapp.generated.resources.select_subject

@Composable
fun FeedTitle(
    title: String,
    modifier: Modifier = Modifier,
    titleStyle: TextStyle = MaterialTheme.typography.titleMedium.copy(
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
    )
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(horizontal = medium, vertical = small),
            text = title,
            style = titleStyle
        )
    }
}

@Composable
fun FeedTitleWithButton(
    title: String,
    btnText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    titleStyle: TextStyle = MaterialTheme.typography.titleMedium.copy(
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
    )
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(horizontal = medium, vertical = small),
            text = title,
            style = titleStyle
        )
        TextButton(
            onClick = { onClick() }
        ) {
            Text(
                text = btnText,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            Icon(
                imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                contentDescription = stringResource(Res.string.right_arrow),
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}


@Composable
fun FeedTitleWithDropdown(
    title: String,
    dropDownList: List<String>,
    placeHolder: String = stringResource(Res.string.select_subject),
    onItemSelected: (String) -> Unit? = {},
    modifier: Modifier = Modifier,
    titleStyle: TextStyle = MaterialTheme.typography.titleMedium.copy(
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
    )
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(horizontal = medium, vertical = small),
            text = title,
            style = titleStyle
        )

        CustomDropdownMenu(
            items = dropDownList,
            placeHolder = placeHolder,
            onItemSelected = { selectedItem ->
                onItemSelected(selectedItem)
            }
        )
    }
}