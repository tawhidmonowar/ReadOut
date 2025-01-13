package org.tawhid.readout.book.audiobook.domain.entity

data class AudioBook(
    val id: String,
    val title: String,
    val description: String?,
    val language: String?,
    val copyrightYear: String?,
    val numSections: String?,
    val textSourceUrl: String?,
    val zipFileUrl: String?,
    val libriVoxUrl: String?,
    val totalTime: String?,
    val imgUrl: String?,
    val authors: List<String>
)