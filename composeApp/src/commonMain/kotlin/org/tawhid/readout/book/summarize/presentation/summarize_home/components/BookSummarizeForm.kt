package org.tawhid.readout.book.summarize.presentation.summarize_home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.tawhid.readout.core.theme.extraSmall
import readout.composeapp.generated.resources.Res
import readout.composeapp.generated.resources.authors
import readout.composeapp.generated.resources.clear
import readout.composeapp.generated.resources.ic_auto_awesome_filled
import readout.composeapp.generated.resources.more_details_about_the_book
import readout.composeapp.generated.resources.summarize
import readout.composeapp.generated.resources.summarize_book_description
import readout.composeapp.generated.resources.title
import readout.composeapp.generated.resources.title_cannot_empty

@Composable
fun BookSummarizeForm(
    onSummarizeClick: (String, String, String) -> Unit,
) {
    var title by remember { mutableStateOf(TextFieldValue("")) }
    var authors by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var titleError by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .widthIn(max = 1000.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = {
                title = it
                titleError = it.text.isEmpty()
            },
            label = { Text(stringResource(Res.string.title)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    if (title.text.isNotEmpty()) {
                        focusManager.moveFocus(FocusDirection.Down)
                    } else {
                        titleError = true
                    }
                }
            )
        )

        if (titleError) {
            Text(
                modifier = Modifier.padding(start = extraSmall),
                text = stringResource(Res.string.title_cannot_empty),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        OutlinedTextField(
            value = authors,
            onValueChange = { authors = it },
            label = { Text(stringResource(Res.string.authors)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text(stringResource(Res.string.more_details_about_the_book)) },
            modifier = Modifier.fillMaxWidth().height(200.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    onSummarizeClick(
                        title.text,
                        authors.text,
                        description.text
                    )
                }
            )
        )

        Text(
            text = stringResource(Res.string.summarize_book_description),
            style = MaterialTheme.typography.labelLarge
        )

        val isAnyFieldNotEmpty = title.text.isNotEmpty() || authors.text.isNotEmpty() || description.text.isNotEmpty()

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (isAnyFieldNotEmpty) {
                Button(
                    onClick = {
                        focusManager.clearFocus()
                        title = TextFieldValue("")
                        authors = TextFieldValue("")
                        description = TextFieldValue("")
                        titleError = false
                    },
                    modifier = Modifier.width(120.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = stringResource(Res.string.clear)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(Res.string.clear))
                }

                Spacer(modifier = Modifier.width(8.dp))
            }

            Button(
                onClick = {
                    if (title.text.isNotEmpty()) {
                        onSummarizeClick(
                            title.text,
                            authors.text,
                            description.text
                        )
                    } else {
                        titleError = true
                    }
                },
                modifier = Modifier.width(160.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_auto_awesome_filled),
                    contentDescription = stringResource(Res.string.summarize)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(Res.string.summarize))
            }
        }
    }
}