package com.example.mycalculator.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.mycalculator.data.dataBase.AppDataBase
import com.example.mycalculator.data.dataBase.EquationListMapper
import com.example.mycalculator.domain.Equation
import com.example.mycalculator.domain.EquationListRepository

class EquationListRepositoryImpl(application: Application) : EquationListRepository {

    private val equationListDao = AppDataBase.getInstance(application).EquationListDao()
    private val mapper = EquationListMapper()


    override fun getEquationList(): LiveData<List<Equation>> =
        Transformations.map(equationListDao.getEquationList()) {
            mapper.mapListDbModelToListEntity(it)
        }

    override suspend fun addEquation(equation: Equation) {
        equationListDao.addEquationItems(mapper.mapEntityToDbModel(equation.apply { id = 0 }))
    }

    override suspend fun deleteEquationFromDb(equationId: Int) {
        equationListDao.deleteEquationFromDb(equationId)
    }


}