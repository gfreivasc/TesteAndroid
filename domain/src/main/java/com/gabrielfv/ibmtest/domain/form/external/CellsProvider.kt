package com.gabrielfv.ibmtest.domain.form.external

import com.gabrielfv.ibmtest.domain.form.model.Cell
import io.reactivex.Single

interface CellsProvider {
    fun fetchCells(): Single<List<Cell>>
}