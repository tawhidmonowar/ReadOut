package org.tawhid.readout.book.summarize.domain

data class Summarize(
    val title: String,
    val authors: String,
    val image: String? = null,
    val summary: String,
    val description: String? = null,
)
