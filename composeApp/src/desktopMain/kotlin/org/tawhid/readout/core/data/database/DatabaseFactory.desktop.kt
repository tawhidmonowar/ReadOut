package org.tawhid.readout.core.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

actual class DatabaseFactory {
    actual fun create(): RoomDatabase.Builder<ReadOutDatabase> {
        val os = System.getProperty("os.name").lowercase()
        val userHome = System.getProperty("user.home")
        val appDataDir = when {
            os.contains("win") -> File(System.getenv("APPDATA"), "ReadOut")
            os.contains("mac") -> File(userHome, "Library/Application Support/ReadOut")
            else -> File(userHome, ".local/share/ReadOut")
        }

        if (!appDataDir.exists()) {
            appDataDir.mkdirs()
        }

        val dbFile = File(appDataDir, ReadOutDatabase.READ_OUT_DB_NAME)
        return Room.databaseBuilder(dbFile.absolutePath)
    }
}