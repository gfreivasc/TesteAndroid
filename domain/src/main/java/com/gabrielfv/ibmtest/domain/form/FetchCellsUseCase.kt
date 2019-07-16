package com.gabrielfv.ibmtest.domain.form

import com.gabrielfv.ibmtest.domain.NetworkRequestException
import com.gabrielfv.ibmtest.domain.form.external.CellsProvider
import com.gabrielfv.ibmtest.domain.form.model.Cell
import io.reactivex.Single
import javax.inject.Inject

class FetchCellsUseCase @Inject constructor(private val cellsProvider: CellsProvider) {

    operator fun invoke(): Single<List<Cell>> {
        return cellsProvider.fetchCells()
            .doOnError { throw NetworkRequestException("Cells request failed") }
    }
}