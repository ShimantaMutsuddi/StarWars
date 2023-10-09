package com.mutsuddi_s.starwars.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mutsuddi_s.starwars.data.model.people.Character
import com.mutsuddi_s.starwars.data.model.people.CharacterRemoteKeys
import com.mutsuddi_s.starwars.data.model.planets.PlanetRemoteKeys
import com.mutsuddi_s.starwars.data.model.planets.Planets
import com.mutsuddi_s.starwars.data.model.starships.Starships
import com.mutsuddi_s.starwars.data.model.starships.StarshipsRemoteKeys

@Database(
    entities = [Character::class, CharacterRemoteKeys::class,Planets::class,PlanetRemoteKeys::class,Starships::class,StarshipsRemoteKeys::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun remoteKeysDao(): CharacteRemoteKeysDao

    abstract fun planetDao(): PlanetsDao
    abstract fun planetRemoteKeysDao(): PlanetsRemoteKeysDao

    abstract fun starshipsDao(): StarshipsDao
    abstract fun starshipsRemoteKeysDao(): StarshipsRemoteKeysDao
}