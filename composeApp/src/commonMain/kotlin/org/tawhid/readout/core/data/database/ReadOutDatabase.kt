package org.tawhid.readout.core.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.tawhid.readout.book.audiobook.data.database.AudioBookDao
import org.tawhid.readout.book.audiobook.data.database.AudioBookEntity
import org.tawhid.readout.book.openbook.data.database.OpenBookDao
import org.tawhid.readout.book.openbook.data.database.OpenBookEntity
import org.tawhid.readout.book.summarize.data.database.SummarizeDao
import org.tawhid.readout.book.summarize.data.database.SummarizeEntity

@Database(
    entities = [
        OpenBookEntity::class,
        AudioBookEntity::class,
        SummarizeEntity::class
    ],
    version = 1
)

@TypeConverters(ReadOutTypeConverters::class)

@ConstructedBy(BookDatabaseConstructor::class)
abstract class ReadOutDatabase : RoomDatabase() {

    abstract val openBookDao: OpenBookDao
    abstract val audioBookDao: AudioBookDao
    abstract val summarizeDao: SummarizeDao

    companion object {
        const val READ_OUT_DB_NAME = "ReadOut.db"
    }
}