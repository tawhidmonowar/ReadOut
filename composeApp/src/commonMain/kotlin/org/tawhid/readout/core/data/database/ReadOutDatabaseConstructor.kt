package org.tawhid.readout.core.data.database

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object BookDatabaseConstructor: RoomDatabaseConstructor<ReadOutDatabase> {
    override fun initialize(): ReadOutDatabase
}