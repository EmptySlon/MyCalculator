package com.example.mycalculator.data.dataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface EquationListDao {
    @Query("SELECT * FROM equation_item")
    fun getEquationList(): LiveData<List<EquationDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEquationItems(equationItemDbModel: EquationDbModel)

    @Query("DELETE FROM equation_item WHERE id=:equationId")
    suspend fun deleteEquationFromDb(equationId: Int)

//    @Query("SELECT * FROM equation_item WHERE id=:equation_item LIMIT 1")
//    suspend fun getEquationItem(equationItemId: Int): EquationDbModel

}