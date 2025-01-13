package org.tawhid.readout.core.data.database

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.tawhid.readout.book.audiobook.domain.entity.AudioBookTrack

object ReadOutTypeConverters {

    @TypeConverter
    fun fromString(value: String): List<String> {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return Json.encodeToString(list)
    }

    @TypeConverter
    fun fromAudioBookTrackList(value: String): List<AudioBookTrack> {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromAudioBookTrackListToString(list: List<AudioBookTrack>): String {
        return Json.encodeToString(list)
    }
}