package com.gabrielfv.ibmtest.features.form.data

import com.gabrielfv.ibmtest.domain.form.external.CellsProvider
import com.gabrielfv.ibmtest.domain.form.model.Cell
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class CellsApi : CellsProvider {

    override fun fetchCells(): Single<List<Cell>> = Single.just(listOf(
        Cell(0, Cell.InputType.FIELD, "Nome", Cell.DataType.TEXT, false, 35.0, null, true),
        Cell(1, Cell.InputType.FIELD, "Email", Cell.DataType.EMAIL, false, 35.0, null, true),
        Cell(2, Cell.InputType.FIELD, "Telefone", Cell.DataType.PHONE, false, 35.0, null, true),
        Cell(3, Cell.InputType.SEND, "Enviar", null, false, 80.0, null, true)
    )).subscribeOn(Schedulers.io())
}