package com.example.bdm_room

import androidx.room.*

@Dao
interface MhsDao {
    @Query("select * from mhsEntity")
    fun getAll(): List<MhsEntity>

    @Query("select * from mhsEntity where nama like :nama")
    fun findByName(nama: String): MhsEntity

    @Insert
    fun insertAll(vararg mhsData: MhsEntity)

    @Update
    fun updateData(vararg mhsData: MhsEntity)

    @Delete
    fun delete(mhs: MhsEntity)
}