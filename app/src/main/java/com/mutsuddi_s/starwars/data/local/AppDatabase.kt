package com.mutsuddi_s.starwars.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mutsuddi_s.starwars.data.model.people.Character
import com.mutsuddi_s.starwars.data.model.people.CharacterRemoteKeys

@Database(
    entities = [Character::class, CharacterRemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}