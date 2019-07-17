package com.gabrielfv.ibmtest.features.form.data

import com.gabrielfv.ibmtest.domain.form.model.Cell
import io.reactivex.Single
import retrofit2.http.GET

interface CellsApi {

    @GET("cells.json")
    fun fetchCells(): Single<List<Cell>>
}