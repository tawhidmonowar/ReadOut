package org.tawhid.readout.core.gemini

import org.tawhid.readout.book.openbook.domain.Book

object GeminiApiPrompts {
    fun geminiBookSummaryPrompt(book: Book): String {
        return """
            Provide a concise summary of the book:
            - Title: ${book.title}
            - Author(s): ${book.authors.joinToString()}
            - First Published Year: ${book.firstPublishYear ?: "Unknown"}
            - Description: ${book.description ?: "No description available"}
            The summary should be approximately 1500-2000 words long and include a comprehensive overview of the plot, character development, themes, and literary devices used. Please ensure the summary is well-structured, coherent, and engaging.
        """.trimIndent()
    }
}