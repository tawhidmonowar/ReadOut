package org.tawhid.readout.book.summarize.presentation.summarize_home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import org.jetbrains.compose.resources.stringResource
import org.tawhid.readout.book.summarize.presentation.summarize_home.SummarizeState
import org.tawhid.readout.core.theme.large
import org.tawhid.readout.core.theme.medium
import readout.composeapp.generated.resources.Res
import readout.composeapp.generated.resources.book_summary
import readout.composeapp.generated.resources.summary_generated_with_ai

@Composable
fun BookSummary(
    state: SummarizeState
) {
    if (state.isSummaryRequest) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(Res.string.book_summary),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .align(Alignment.Start)
                    .fillMaxWidth()
                    .padding(top = large)
            )
            Text(
                modifier = Modifier
                    .align(Alignment.Start)
                    .fillMaxWidth()
                    .padding(bottom = medium),
                text = stringResource(Res.string.summary_generated_with_ai),
                style = MaterialTheme.typography.bodyMedium
            )

            if (state.isSummaryLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(medium),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                state.summarize?.summary?.let { summary ->
                    Text(
                        text = summary,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Justify,
                    )
                }
            }
        }
    }
}