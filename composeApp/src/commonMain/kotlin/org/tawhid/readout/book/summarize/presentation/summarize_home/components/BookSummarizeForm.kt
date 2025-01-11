package org.tawhid.readout.book.summarize.presentation.summarize_home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import readout.composeapp.generated.resources.Res
import readout.composeapp.generated.resources.ic_auto_awesome_filled

@Composable
fun BookSummarizeForm(
    onSummarizeClick: (String, String, String) -> Unit,
) {
    var title by remember { mutableStateOf(TextFieldValue("")) }
    var authors by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .widthIn(max = 1000.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            maxLines = 1,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = authors,
            onValueChange = { authors = it },
            label = { Text("Authors") },
            maxLines = 1,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("More Details About The Book") },
            modifier = Modifier.fillMaxWidth().height(200.dp),
        )

        Text(
            text = "*Fill in the Details and Let AI Summarize Your Book.",
            style = MaterialTheme.typography.labelLarge
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    onSummarizeClick(
                        title.text,
                        authors.text,
                        description.text
                    )
                },
                modifier = Modifier.width(160.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_auto_awesome_filled),
                    contentDescription = "Summarize"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Summarize")
            }
        }
    }
}