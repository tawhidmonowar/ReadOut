package org.tawhid.readout.core.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseFactory(
    private val context: Context
) {
    actual fun create(): RoomDatabase.Builder<ReadOutDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(ReadOutDatabase.READ_OUT_DB_NAME)

        return Room.databaseBuilder(
            context = appContext,
            name = dbFile.absolutePath
        )
    }
}