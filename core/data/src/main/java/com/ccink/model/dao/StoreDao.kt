package com.ccink.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.ccink.model.entity.AccountEntity
import com.ccink.model.entity.AnimationEntity
import com.ccink.model.entity.AnimationFileEntity
import com.ccink.model.entity.ClothesEntity
import com.ccink.model.entity.PetEntity
import com.ccink.model.entity.ImageFileEntity
import com.ccink.model.entity.PetLinkEntity

@Dao
interface StoreDao {
    @Insert(onConflict = REPLACE)
    suspend fun saveAccount(account: AccountEntity)

    @Insert(onConflict = REPLACE)
    suspend fun savePetLinks(petLinks: List<PetLinkEntity>)

    @Delete
    suspend fun deletePetLinks(petLinks: List<PetLinkEntity>)

    @Insert(onConflict = REPLACE)
    suspend fun saveAnimations(animations: List<AnimationEntity>)

    @Insert(onConflict = REPLACE)
    suspend fun saveAnimationsFiles(animationsFiles: List<AnimationFileEntity>)

    @Insert(onConflict = REPLACE)
    suspend fun saveImageFile(imageFile: ImageFileEntity)

    @Insert(onConflict = REPLACE)
    suspend fun savePets(pets: List<PetEntity>)

    @Insert(onConflict = REPLACE)
    suspend fun saveClothes(clothes: List<ClothesEntity>)

    @Query("SELECT * FROM AccountEntity WHERE id = :id")
    suspend fun getAccount(id: String): AccountEntity

    @Query("SELECT * FROM PetLinkEntity WHERE accountId = :accountId")
    suspend fun getPetLinks(accountId: String): List<PetLinkEntity>

    @Query("SELECT * FROM PetEntity")
    suspend fun getAllPets(): List<PetEntity>

    @Query("SELECT * FROM PetEntity WHERE id = :petId")
    suspend fun getSinglePet(petId: Int): PetEntity?

    @Query("SELECT * FROM ClothesEntity")
    suspend fun getAllClothes(): List<ClothesEntity>

    @Query("SELECT * FROM ClothesEntity WHERE id = :clothesId")
    suspend fun getSingleClothes(clothesId: Int): ClothesEntity?

    @Query("SELECT * FROM AnimationEntity")
    suspend fun getAllAnimations(): List<AnimationEntity>

    @Query("SELECT * FROM AnimationEntity WHERE clothesId = :clothesId")
    suspend fun getSingleAnimation(clothesId: Int): AnimationEntity

    @Query("SELECT * FROM AnimationFileEntity WHERE fileName = :fileName")
    suspend fun getAnimationFile(fileName: String): AnimationFileEntity

    @Query("SELECT fileName FROM AnimationFileEntity")
    suspend fun getAllAnimationFilenames(): List<String>

    @Query("SELECT * FROM ImageFileEntity")
    suspend fun getImageFiles(): List<ImageFileEntity>
}