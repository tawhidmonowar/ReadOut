package org.tawhid.readout.book.audiobook.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.tawhid.readout.book.audiobook.domain.entity.AudioBookTrack

@Entity
data class AudioBookEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    val title: String?,
    val description: String?,
    val language: String?,
    val copyrightYear: String?,
    val numSections: String?,
    val textSourceUrl: String?,
    val zipFileUrl: String?,
    val libriVoxUrl: String?,
    val totalTime: String?,
    val imgUrl: String?,
    val authors: List<String>,

    val bookType: String?,
    val isSaved: Boolean?,
    val isViewed: Boolean?,
    val audioBookTracks: List<AudioBookTrack>?,
    val summaryText: String?,
    val summaryBase64: String?,
    val timeStamp: Long?
)