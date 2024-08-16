package com.ccink.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ccink.model.dao.StoreDao
import com.ccink.model.entity.AccountEntity
import com.ccink.model.entity.AnimationEntity
import com.ccink.model.entity.AnimationFileEntity
import com.ccink.model.entity.ClothesEntity
import com.ccink.model.entity.Converters
import com.ccink.model.entity.PetEntity
import com.ccink.model.entity.ImageFileEntity
import com.ccink.model.entity.PetLinkEntity

@Database(
    entities = [
        AccountEntity::class,
        PetLinkEntity::class,
        AnimationEntity::class,
        AnimationFileEntity::class,
        ImageFileEntity::class,
        PetEntity::class,
        ClothesEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDb : RoomDatabase() {
    abstract fun storeDao(): StoreDao
}