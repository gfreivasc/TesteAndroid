package com.gabrielfv.ibmtest.features.form.data

import com.gabrielfv.ibmtest.features.form.data.model.CellsDTO
import io.reactivex.Single
import retrofit2.http.GET

interface CellsApi {

    @GET("cells.json")
    fun fetchCells(): Single<CellsDTO>
}