package com.gabrielfv.ibmtest.features.funds.data

import com.gabrielfv.ibmtest.features.funds.data.model.Screen
import io.reactivex.Single
import retrofit2.http.GET

interface FundApi {

    @GET("fund.json")
    fun getFund(): Single<Screen>
}