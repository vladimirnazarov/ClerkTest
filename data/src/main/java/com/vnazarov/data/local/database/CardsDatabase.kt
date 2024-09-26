package com.vnazarov.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vnazarov.data.local.dao.CardsDao
import com.vnazarov.data.local.entity.CardEntity

@Database(
    entities = [CardEntity::class],
    version = 4,
    exportSchema = false
)
abstract class CardsDatabase : RoomDatabase() {

    abstract fun cardsDao(): CardsDao
}