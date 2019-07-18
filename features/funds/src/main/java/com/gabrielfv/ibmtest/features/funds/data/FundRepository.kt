package com.gabrielfv.ibmtest.features.funds.data

import com.gabrielfv.ibmtest.domain.funds.external.FundProvider
import com.gabrielfv.ibmtest.domain.funds.model.Fund
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class FundRepository(private val api: FundApi): FundProvider {

    override fun getFund(): Single<Fund> = api.getFund()
        .subscribeOn(Schedulers.io())
        .map { it.screen }
}