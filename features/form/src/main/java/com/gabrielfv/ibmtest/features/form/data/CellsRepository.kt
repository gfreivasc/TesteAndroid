package com.gabrielfv.ibmtest.features.form.data

import com.gabrielfv.ibmtest.domain.form.external.CellsProvider
import com.gabrielfv.ibmtest.domain.form.model.Cell
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CellsRepository(
    private val cellsApi: CellsApi
) : CellsProvider {

    override fun fetchCells(): Single<List<Cell>> = cellsApi.fetchCells()
        .subscribeOn(Schedulers.io())
}